(ns bluecheese.article-generator
  (:require [clojure.string :as str]
            [markdown.core :as md]
            [clojure.java.io :as io]
            [clostache.parser :as clo]
            [stasis.core :as stasis]
            [clojure.walk :as walk])
  (:use [bluecheese.config :only [config]]))


(defn parse-variables [variables]
  "parse `key = value` style text"
  (->>
    (str/split variables #"(?m)\n")
    (mapcat #(str/split % #"="))
    (map str/trim)
    (map #(str/replace % #"[\^\"\"$]" ""))
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
    (map (partial slurp))
    (map md->map)))


(defn generate-article-pages [env-config]
  (let [article (:article-template-path env-config)
        common-head (:common-head env-config)
        dist (:dist-path env-config)]
    (stasis/empty-directory! dist)
    (->>
      (read-md-files (:kr-md-path env-config))
      (map walk/keywordize-keys)
      ; v render template with map
      ; v render template with common-head
      ; v generate files
      (map #(merge % {:html (clo/render-resource article %)}))
      (map #(merge % {:html (clo/render {:html %} common-head)}))
      (map #(stasis/export-page {:url-path %}
                                {:html %}
                                dist
                                {})))))


