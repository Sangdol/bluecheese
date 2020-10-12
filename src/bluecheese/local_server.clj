(ns bluecheese.local-server
  (:require [clojure.string :as str]
            [cljstache.core :as clo]
            [clojure.java.io :as io]
            [markdown.core :as md]
            [me.raynes.fs :as fs]
            [ring.util.response :as response]
            [clojure.walk :as walk])
  (:use [ring.middleware.resource :only [wrap-resource]]
        [bluecheese.article-generator :only [articles md->map common-htmls]]
        [bluecheese.config :only [config]]))


(def env-config (config "local"))

;(defn articles->pages [articles]
;  (->> articles
;       (map (fn [article] [(str "/blog/" (article :url-path) "/index.html")
;                           (article :html)]))
;       (into {})))


;(defn get-pages []
;  (let [md-path (:kr-md-path env-config)
;        blog-info (:blog-info env-config)
;        base-url (:base-url env-config)]
;    (->>
;      ;; TODO index.html / fixed
;      (articles md-path base-url blog-info env-config fixed-template article-template)
;      (articles->pages)))


;(def pages (get-pages))

(def blog-info (:blog-info env-config))
(def article-template (:article-template-path env-config))
(def fixed-template (:fixed-template-path env-config))
(def md-path (:kr-md-path env-config))

;* translate the url
; /blog/2020/10/10/custom-static-site-generator -> md/kr/blog/{filename}
;; TODO fixed path
(defn uri->path [uri]
  (str "resources/" md-path "/" (last (str/split uri #"/")) ".md"))

;* take path: :uri
;* take markdown file
;* turn it to html
(defn handler [request]
  (println request)
  (println (uri->path (:uri request)))
  (->>
    (uri->path (:uri request))
    slurp
    md->map
    walk/keywordize-keys
    (merge blog-info (common-htmls env-config))
    (clo/render-resource article-template)
    ;(fn [article] ;; why doesn't this work?
    ;  (if (= (:type article) "fixed")
    ;    (clo/render-resource fixed-template article)
    ;    (clo/render-resource article-template article))
    ;:html))
    ;(fn [html] ;; why doesn't this print anything?
    ;  (println "html" html)
    ;  html)
    response/response))

(def app
  (->
    handler
    (wrap-resource "web")))


