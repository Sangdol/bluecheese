(ns bluecheese.local-server
  (:require [clojure.string :as str]
            [cljstache.core :as clo]
            [clojure.walk :as walk]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]])
  (:use [bluecheese.article-generator :only [md->map common-htmls]]
        [bluecheese.list-generator :only [list-html]]
        [bluecheese.config :only [config]]))


(def env-config (config "local"))
(def blog-info (:blog-info env-config))
(def article-template (:article-template-path env-config))
(def fixed-template (:fixed-template-path env-config))
(def md-path (:md-path env-config))
(def base-url (:base-url env-config))
(def list-template (:list-template-path env-config))

;;
;; Translate the url to a file path
;;
;; /blog/2020/10/10/custom-static-site-generator ->
;;   md/kr/blog/custom-static-site-generator.md
;;
;; /about -> md/kr/blog/fixed-about.md
;;
(defn uri->path [uri]
  (if (str/starts-with? uri "/blog") ;;
    (str "resources/" md-path "/" (last (str/split uri #"/")) ".md")
    (str "resources/" md-path "/fixed-" (last (str/split uri #"/")) ".md")))


;; This finds md files based on the slug in the uri
;; which doesn't always work since some articles have a different
;; slug from its filename.
;; I could make a filename to slug map for a search but this approach
;; is simple works well enough.
(defn read-md-as-html [path]
  (->>
    path
    slurp
    md->map
    walk/keywordize-keys
    (merge blog-info)
    (#(merge (common-htmls env-config %) %))
    ((fn [article]
       (if (= (:type article) "fixed")
         (clo/render-resource fixed-template article)
         (clo/render-resource article-template article))))))


(defn handler [request]
  (->>
    (:uri request)
    ((fn [uri]
       (if (= uri "/")
          (list-html md-path base-url blog-info env-config list-template)
          (read-md-as-html (uri->path uri)))))
    ((fn [html]
       {:status 200
        :headers {"content-type" "text/html; charset=UTF-8"}
        :body html}))))


;; This is used by Ring.
(def app
  (->
    handler
    (wrap-resource "web")
    (wrap-content-type)))


