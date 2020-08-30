(ns bluecheese.article-generator
  (:require [clojure.string :as str]
            [markdown.core :as md]
            [clojure.java.io :as io]
            [clostache.parser :as clo]
            [clojure.walk :as walk]
            [me.raynes.fs :as fs]
            [java-time :as time])
  (:use [bluecheese.config :only [config]])
  (:import (java.util Date)))


(defn parse-variables [variables]
  "parse `key = value` style text"
  (->>
    (str/split variables #"(?m)\n")
    (mapcat #(str/split % #"="))
    (map str/trim)
    (map #(str/replace % #"[\^\"\"$\^\'\'$]" ""))  ; unquote
    (apply hash-map))) ;; (into {}) ??


(defn only-date [datetime]
  (first (str/split datetime #"T")))


(defn url-path [vm]
  "generate url path of an article including date and slug
   e.g., 2019/08/26/blog-ab-test/

  * date format: 2020-07-01 or 2017-09-08T04:53:40+02:00"
  (let [date (only-date (vm "date"))
        slug (vm "slug")]
    (str (str/replace date "-" "/")
         "/"
         (str/replace slug " " "-"))))


; TODO - exception handling for empty date or slug / mandatory variables
;   add filepath for exception msg - is there any better way? catch late
(defn md->map [md-file-content]
  (->>
    (str/split md-file-content #"(?m)^\+\+\+")
    (map str/trim)
    ((fn [[_, variables, md]]
       (let [vm (parse-variables variables)]
         (merge vm
                {"body" (md/md-to-html-string md)}
                {"url-path" (url-path vm)}))))))


(defn formatted-date [long-datetime]
  "long datetime format 2019-02-04T20:17:00+01:00"
  (->>
    long-datetime
    only-date
    (time/local-date "yyyy-MM-dd")
    (time/format "MMM d, yyyy")))


(defn java-date [offset-date]
  (Date.
    (.toEpochMilli
      (.toInstant
        (time/offset-date-time offset-date)))))


(defn read-md-files [dir]
  "return a vector of article metadata and html from markdown files"
  (->>
    dir
    io/file
    file-seq
    ((fn [xs]
       (println (count xs) " md files.")
       xs))
    (filter (fn [^java.io.File f] (. f (isFile))))
    (map slurp)
    (map md->map)
    (map walk/keywordize-keys)))


(defn read-posts [dir]
  "return a vector of articles with dates and being sorted"
  (->>
    (read-md-files dir)
    (map #(merge % {:datetime (java-date (:date %))
                    :dateStr (formatted-date (:date %))}))
    (sort-by :datetime)
    (reverse)
    (filter #(not= "fixed" (:type %)))))


(defn write-pages [dist-path pages]
  (when-let [dist (io/resource dist-path)]
    (fs/delete-dir dist))
  (doseq [article pages]
    (let [filepath (str dist-path "/" (:url-path article) "/index.html")]
      (println "Writing a file to " filepath article)
      (fs/mkdirs (fs/parent filepath))
      (spit filepath (:html article)))))


(defn generate-article-pages [env-config]
  (let [md-path (:kr-md-path env-config)
        template (:basic-template-path env-config)
        blog-info (:blog-info env-config)
        common-head (:common-head env-config)
        dist (:kr-blog-path env-config)]
    (->>
      (read-md-files (io/resource md-path))
      ;; Adding one property by one in a map doesn't look very understandable.
      ;; * title, description, body: for templating
      ;; * date, slug, url-path: for path
      ;; * html: for writing to a file
      (map #(merge blog-info % {:common-head (slurp (io/file (io/resource common-head)))}))
      (map #(merge % {:html (clo/render-resource template %)}))
      (write-pages dist))))

