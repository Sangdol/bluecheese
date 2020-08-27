(ns bluecheese.article-generator
  (:require [clojure.string :as str]
            [markdown.core :as md])
  (:use [bluecheese.config :only [config]]))

(defn interpolate-variable [template variable data]
  "interpolate single variable with single data"
  (str/replace template
               (str "{{" variable "}}")
               data))


(defn interpolate
  "interpolate multiple variables with multiple data"
  ([template variables data-list]
   (interpolate template (zipmap variables data-list)))
  ([template m]
   (reduce (fn [[k v]]
             (interpolate-variable template k v))
           template
           m)))


(defn parse-variables [variables]
  "parse `key = value` style text"
  (->>
    (str/split variables #"(?m)\n")
    (mapcat #(str/split % #"="))
    (map str/trim)
    (map #(str/replace % #"[\^\"\"$]" ""))
    (apply hash-map)))


; TODO add file path info
(defn convert-md-to-map [md-file-content]
  "read a markdown file and return a map with metadata variables and converted html"
  (->>
    (str/split md-file-content #"(?m)^\+\+\+")
    (map str/trim)
    ((fn [[_, variables, md]]
       (merge
         (parse-variables variables)
         {"html" (md/md-to-html-string md)})))))


(defn read-md-files [md-path]
  "return a vector of article metadata and html from markdown files"
  (->> md-path
       clojure.java.io/file
       file-seq
       (map #((convert-md-to-map (slurp %))))))


(defn generate-article-pages [env-config]
  (->>
    (read-md-files (:kr-md-path env-config))
    (map (partial interpolate (:article-template-path env-config)))))

