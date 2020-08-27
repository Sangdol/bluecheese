(ns bluecheese.article-generator
  (:require [clojure.string :as str]
            [markdown.core :as md]
            [clojure.java.io :as io]
            [clostache.parser :as clo]
            [clojure.walk :as walk]
            [me.raynes.fs :as fs])
  (:use [bluecheese.config :only [config]]))


(defn parse-variables [variables]
  "parse `key = value` style text"
  (->>
    (str/split variables #"(?m)\n")
    (mapcat #(str/split % #"="))
    (map str/trim)
    (map #(str/replace % #"[\^\"\"$\^\'\'$]" ""))  ; unquote
    (apply hash-map))) ;; (into {}) ??


(defn url-path [vm]
  "generate url path of an article including date and slug
   e.g., 2019/08/26/blog-ab-test/

  * date format: 2020-07-01 or 2017-09-08T04:53:40+02:00"
  (let [date (first (str/split (vm "date") #"T"))
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
    (map md->map)))


(defn write-article [dist-path articles]
  (when-let [dist (io/resource dist-path)]
    (fs/delete-dir dist))
  (doseq [article articles]
    (let [filepath (str dist-path "/" (:url-path article) "/index.html")]
      (println "Writing a file to " filepath article)
      (fs/mkdirs (fs/parent filepath))
      (spit filepath (:html article)))))


(defn generate-article-pages [env-config]
  (let [md-path (:kr-md-path env-config)
        article (:article-template-path env-config)
        common-head (:common-head env-config)
        dist (:kr-blog-path env-config)]
    (->>
      (read-md-files (io/resource md-path))
      (map walk/keywordize-keys)
      (map #(merge % {:common-head (slurp (io/file (io/resource common-head)))}))
      (map #(merge % {:html (clo/render-resource article %)}))
      ((partial write-article dist)))))


