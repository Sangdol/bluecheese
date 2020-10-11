(ns bluecheese.rss-generator
  (:require [clojure.java.io :as io]
            [clj-rss.core :as rss])
  (:use [bluecheese.article-generator :only [read-posts]]))


(def max-item-count 30)


(defn items [md-path blog-url base-url]
  (->>
    (read-posts (io/resource md-path) base-url)
    (map (fn [article] {:title       (:title article)
                        :link        (str blog-url "/" (:url-path article))
                        :pubDate     (:datetime article)
                        :description (:body article)}))))


(defn generate-rss [env-config]
  (let [blog-info (:blog-info env-config)
        blog-title (:kr-blog-title blog-info)
        description (:description blog-info)
        md-path (:kr-md-path env-config)
        base-url (:base-url env-config)
        blog-url (:kr-blog-url env-config)
        rss-path (:kr-rss-path env-config)
        items (items md-path blog-url base-url)]
    (->>
      (rss/channel-xml {:title       blog-title
                        :link        base-url
                        :description description}
                       (take max-item-count items))
      (spit rss-path))))




