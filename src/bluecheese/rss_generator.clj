(ns bluecheese.rss-generator
  (:require [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [clj-rss.core :as rss])
  (:use [bluecheese.article-generator :only [read-posts]]))


;* take meta data
;* set base rss
;* read md files
;* order by date
;* filter by blog
;* take top 30
;* write the file
(defn generate-rss [env-config]
  (let [blog-info (:blog-info env-config)
        blog-title (:kr-blog-title blog-info)
        description (:description blog-info)
        rss-path (:kr-rss-path env-config)
        md-path (:kr-md-path env-config)]
    (->>
      (read-posts (io/resource md-path)))))


