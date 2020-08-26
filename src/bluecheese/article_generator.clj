(ns bluecheese.article-generator
  (:require [clojure.string :as str]
            [markdown.core :as md])
  (:use [bluecheese.config :only [config]]))

(defn interpolate-variable [template variable data]
  "interpolate single variable with single data"
  (str/replace template
               (str "{{" variable "}}")
               data))

(defn interpolate [template variables data-list]
  "interpolate multiple variables with multiple data"
  (reduce (fn [template zip]
            (interpolate-variable template (first zip) (second zip)))
          template
          (map vector variables data-list)))

(defn generate-message [output-path]
  (str "Success: " output-path))

(defn generate-file [template-path variables data-paths output-path]
  (->>
    (interpolate
      (slurp template-path)
      variables
      (map slurp data-paths))
    (spit output-path))
  (->>
    (generate-message output-path)
    (println)))

(defn generate-html [env-config page]
  (generate-file (:html-template-path env-config)
                 ["body"]
                 [(interpolate-variable
                    (:html-body-content-path-template env-config)
                    "page"
                    page)]
                 (interpolate-variable
                   (:html-output-path-template env-config)
                   "page"
                   page)))


(defn parse-variables [variables]
  "parse `key = value` style text"
  (-> variables
      (str/split #"(?m)\n")
      ((fn [xs]
         (mapcat #(str/split % #"=") xs)))
      (#(map str/trim %))
      ((fn [xs]
         ; remove starting and ending quotes
         (map #(str/replace % #"[\^\"\"$]" "") xs)))
      (#(apply hash-map %))))


(defn convert-md-to-html [md-file-content]
  "read a markdown file and return a map with metadata variables and converted html"
  (->
    md-file-content
    (str/split #"(?m)^\+\+\+")
    (#(map str/trim %))
    ((fn [[_, variables, md]]
      {:variables (parse-variables variables)
       :html      (md/md-to-html-string md)}))))


(defn read-md-files [md-path]
  "return a vector of article metadata and html from markdown files"
  (->> md-path
       clojure.java.io/file
       file-seq
       (map #((convert-md-to-html (slurp %))))))


(defn generate-htmls [articles]
  "")



(defn generate-article-pages [env-config]
  (->
    ; returns [{variables: {}, html: ""}]
    (read-md-files (:kr-md-path env-config))
    generate-htmls))
