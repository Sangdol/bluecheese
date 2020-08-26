(ns bluecheese.static-generator
  (:require [clojure.string :as str])
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

(defn generate-static-pages [env-config]
  ;; html
  (generate-html env-config "index")
  (generate-html env-config "about-data"))
