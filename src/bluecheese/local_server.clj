(ns bluecheese.local-server
  (:require [clojure.string :as str]
            [cljstache.core :as clo]
            [clojure.walk :as walk]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]])
  (:use [bluecheese.home-generator :only [home-html]]
        [bluecheese.article-generator :only [md->map common-htmls]]
        [bluecheese.list-generator :only [list-html]]
        [bluecheese.config :only [config]]))


(def env-configs (config "local"))


(defn md-file-path
  ([uri md-path] (md-file-path uri md-path ""))
  ([uri md-path prefix] (str "resources/"
                             md-path
                             "/"
                             prefix
                             (last (str/split uri #"/"))
                             ".md")))

;;
;; Translate the url to a file path
;;
;; /blog/2020/10/10/custom-static-site-generator ->
;;   md/en/blog/custom-static-site-generator.md
;;
;; /blog/2020/10/10/custom-static-site-generator ->
;;   md/kr/blog/custom-static-site-generator.md
;;
;; /about -> md/kr/blog/fixed-about.md
;;
(defn uri->path [uri env-config]
  (if (or (str/starts-with? uri "/en") (str/starts-with? uri "/blog"))
    (md-file-path uri (:md-path env-config))
    (md-file-path uri (:md-path env-config) "fixed-")))


;; This finds md files based on the slug in the uri
;; which doesn't always work since some articles have a different
;; slug from its filename.
;; I could make a filename to slug map for a search but this approach
;; is simple works well enough.
(defn read-md-as-html [path env-config blog-info fixed-template article-template]
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


(defn env-config-for [uri]
  (if (str/starts-with? uri "/blog")
    (first env-configs)
    (second env-configs)))


(defn handler [request]
  (->>
    (:uri request)
    ((fn [uri]
       (let [env-config (env-config-for uri)
             md-path (:md-path env-config)
             base-url (:base-url env-config)
             blog-info (:blog-info env-config)
             list-template (:list-template-path env-config)
             fixed-template (:fixed-template-path env-config)
             article-template (:article-template-path env-config)]
         (cond
           (= uri "/")
           (home-html env-config)

           (or (= uri "/blog") (= uri "/en"))
           (list-html md-path base-url blog-info env-config list-template)

           :else
           (read-md-as-html (uri->path uri env-config)
                            env-config blog-info fixed-template article-template)))))
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
