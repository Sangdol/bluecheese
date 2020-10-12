(ns bluecheese.local-server
  (:require [clojure.string :as str]
            [cljstache.core :as clo]
            [clojure.java.io :as io]
            [markdown.core :as md]
            [me.raynes.fs :as fs]
            [ring.util.response :as response]
            [clojure.walk :as walk])
  (:use [ring.middleware.resource :only [wrap-resource]]
        [bluecheese.article-generator :only [md->map common-htmls]]
        [bluecheese.config :only [config]]))


(def env-config (config "local"))
(def blog-info (:blog-info env-config))
(def article-template (:article-template-path env-config))
(def fixed-template (:fixed-template-path env-config))
(def md-path (:kr-md-path env-config))

;* translate the url
; /blog/2020/10/10/custom-static-site-generator -> md/kr/blog/{filename}
;; TODO fixed path
(defn uri->path [uri]
  (str "resources/" md-path "/" (last (str/split uri #"/")) ".md"))


(defn read-md-as-html [path]
  (->>
    path
    slurp
    md->map
    walk/keywordize-keys
    (merge blog-info (common-htmls env-config))
    ((fn [article]
       (if (= (:type article) "fixed")
         (clo/render-resource fixed-template article)
         (clo/render-resource article-template article))))))


;* take path: :uri
;* take markdown file
;* turn it to html
(defn handler [request]
  (->>
    (uri->path (:uri request))
    read-md-as-html
    response/response))


;; This is used by Ring.
(def app
  (->
    handler
    (wrap-resource "web")))


