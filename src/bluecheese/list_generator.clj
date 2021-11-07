(ns bluecheese.list-generator
  (:require [cljstache.core :as clo]
            [clojure.java.io :as io])
  (:use [bluecheese.article-generator :only [read-posts common-htmls]]))


; /blog/index.html
(defn write-list
  [dist-path page]
  (let [filepath (str dist-path "/index.html")]
    (println "Writing a file to " filepath)
    (spit filepath page)))


(defn list-html
  [md-path base-url blog-info env-config template]
  (->>
    (read-posts (io/resource md-path) base-url)
    ((fn [articles]
       (merge blog-info (common-htmls env-config blog-info) {:articles articles})))
    (clo/render-resource template)))


(defn generate-list-page [env-config]
  (let [md-path (:md-path env-config)
        template (:list-template-path env-config)
        blog-info (:blog-info env-config)
        dist (:dist env-config)
        blog-dist (:blog-path env-config)
        base-url (:base-url env-config)]
    (->>
      (list-html md-path base-url blog-info env-config template)
      (write-list dist)  ; (main page) writing here until I have an English blog.
      (write-list blog-dist))))


