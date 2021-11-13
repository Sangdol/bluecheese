(ns bluecheese.home-generator
  (:require [bluecheese.list-generator :as list-generator]))


(defn generate-main-page [env-config]
  (list-generator/main [env-config]))


(defn main [env-configs]
  (let [env-config (first env-configs)]
    (generate-main-page (merge env-config {:blog-path (:dist env-config)}))))
