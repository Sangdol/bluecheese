;;
;; Copy html, css, js, etc. except for htmls generated from markdown files
;;
(ns bluecheese.ui
  (:require [me.raynes.fs :as fs]
            [clojure.java.io :as io]))


(defn copy-ui [env-config]
  (let [src (io/resource (:web env-config))
        dist (:dist env-config)]
    (fs/copy-dir-into src dist)))


(defn main [env-configs]
  (doseq [env-config env-configs]
    (copy-ui env-config)))
