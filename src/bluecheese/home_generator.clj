(ns bluecheese.home-generator
  (:require [bluecheese.article-generator :as ag]
            [cljstache.core :as clo]))


;; template placeholders: blog-title, common-head
(defn generate-main-page [env-config]
  (let [home-template (:home-template-path env-config)
        blog-info (:blog-info env-config)
        commons (ag/common-htmls env-config {}) ;; no contents needed
        template-contents (merge blog-info commons)
        html (clo/render-resource home-template template-contents)
        filepath (str (:dist env-config) "/index.html")]
    (println "Writing a file to " filepath)
    (spit filepath html)))


(defn main [env-configs]
  (let [env-config (second env-configs)]
    (generate-main-page env-config)))
