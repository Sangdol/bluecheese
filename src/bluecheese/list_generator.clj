(ns bluecheese.list-generator
  (:require [clostache.parser :as clo]
            [clojure.java.io :as io]
            [java-time :as time])
  (:use [bluecheese.config :only [config]]
        [bluecheese.article-generator :only [only-date read-md-files]]))


(defn formatted-date [long-datetime]
  "long datetime format 2019-02-04T20:17:00+01:00"
  (->>
    long-datetime
    only-date
    (time/local-date "yyyy-MM-dd")
    (time/format "MMM d, yyyy")))


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
      (read-md-files (io/resource md-path))
      (map #(merge % {:dateStr (formatted-date (:date %))}))
      (map #(merge % {:common-head (slurp (io/file (io/resource common-head)))}))
      (sort-by :date)
      (reverse)
      ((fn [articles]
         (merge blog-info {:articles articles})))
      (clo/render-resource template)
      (write-list dist))))


