(ns bluecheese.home-generator
  (:require [bluecheese.article-generator :as ag]
            [cljstache.core :as clo]))


(defn home-html [env-config]
  (let [home-template (:home-template-path env-config)
        blog-info (:blog-info env-config)
        commons (ag/common-htmls env-config blog-info)
        template-contents (merge blog-info commons)]
    (clo/render-resource home-template template-contents)))


;; template placeholders: blog-title, common-head
(defn generate-main-page [env-config]
  (let [html (home-html env-config)
        filepath (str (:dist env-config) "/index.html")]
    (println "Writing a file to " filepath)
    (spit filepath html)))


(defn main [env-configs]
  (let [env-config (second env-configs)]
    (generate-main-page env-config)))
