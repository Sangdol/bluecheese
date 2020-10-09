(ns bluecheese.article-generator
  (:require [clojure.string :as str]
            [markdown.core :as md]
            [clojure.java.io :as io]
            [cljstache.core :as clo]
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
    (map #(str/replace % #"[\^\"\"$\^\'\'$]" ""))           ; unquote
    (apply hash-map)))                                      ;; (into {}) ??


(defn only-date [datetime]
  (first (str/split datetime #"T")))


(defn url-path [vm]
  "generate url path of an article including date and slug
   e.g., 2019/08/26/blog-ab-test/

  * date format: 2020-07-01 or 2017-09-08T04:53:40+02:00"
  (let [date (only-date (vm "date"))
        slug (vm "slug")]
    (if (= (vm "type") "fixed")
        (str/replace slug " " "-")
        (str (str/replace date "-" "/")
             "/"
             (str/replace slug " " "-")))))


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


(defn replace-relative-to-absolute-img-url [base-url md-file-content]
  (str/replace md-file-content
               #"<img src=\"/"
               (str "<img src=\"" base-url "/")))


(defn read-md-files [dir base-url]
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
    (map #(replace-relative-to-absolute-img-url base-url %))
    (map md->map)
    (map walk/keywordize-keys)))


(defn read-posts [dir base-url]
  "return a vector of articles with dates and being sorted"
  (->>
    (read-md-files dir base-url)
    (map #(merge % {:datetime (java-date (:date %))
                    :dateStr  (formatted-date (:date %))}))
    (sort-by :datetime)
    (reverse)
    (filter #(not= "fixed" (:type %)))))


(defn write-page [dist article]
  (let [filepath (str dist "/" (:url-path article) "/index.html")]
    (println "Writing a file to " filepath)
    (fs/mkdirs (fs/parent filepath))
    (spit filepath (:html article))))


(defn write-pages [dist blog-dist pages]
  (doseq [article pages]
    (if (= (:type article) "fixed")
      (write-page dist article)
      (write-page blog-dist article))))


(defn common-htmls [env-config]
  (->>
    (for [key [:common-head :common-header :common-footer]]
      [key (slurp (io/file (io/resource (key env-config))))])
    (into {})))


(defn generate-article-pages [env-config]
  (let [md-path (:kr-md-path env-config)
        article-template (:article-template-path env-config)
        fixed-template (:fixed-template-path env-config)
        blog-info (:blog-info env-config)
        dist (:dist env-config)
        blog-dist (:kr-blog-path env-config)
        base-url (:base-url env-config)]
    (->>
      (read-md-files (io/resource md-path) base-url)
      ;; Adding one property by one in a map doesn't look very understandable.
      ;; * title, description, body: for templating
      ;; * date, slug, url-path: for path
      ;; * html: for writing to a file
      (map #(merge blog-info % (common-htmls env-config)))
      (map #(merge % {:html (if (= (:type %) "fixed")
                              (clo/render-resource fixed-template %)
                              (clo/render-resource article-template %))}))
      (write-pages dist blog-dist))))