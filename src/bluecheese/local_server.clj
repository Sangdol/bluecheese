(ns bluecheese.local-server
  (:require [clojure.string :as str]
            [cljstache.core :as clo]
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
    (merge blog-info (common-htmls env-config))
    ((fn [article]
       (if (= (:type article) "fixed")
         (clo/render-resource fixed-template article)
         (clo/render-resource article-template article))))))


;; TODO list (main page)
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


