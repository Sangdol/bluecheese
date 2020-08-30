(ns bluecheese.list-generator
  (:require [clostache.parser :as clo]
            [clojure.java.io :as io])
  (:use [bluecheese.article-generator :only [read-posts]]))


; /blog/index.html
(defn write-list [dist-path page]
  (let [filepath (str dist-path "/index.html")]
    (println "Writing a file to " filepath page)
    (spit filepath page)))


(defn generate-list-page [env-config]
  (let [md-path (:kr-md-path env-config)
        template (:list-template-path env-config)
        blog-info (:blog-info env-config)
        common-head (:common-head env-config)
        dist (:kr-blog-path env-config)]
    (->>
      (read-posts (io/resource md-path))
      (map #(merge % {:common-head (slurp (io/file (io/resource common-head)))}))
      ((fn [articles]
         (merge blog-info {:articles articles})))
      (clo/render-resource template)
      (write-list dist))))


