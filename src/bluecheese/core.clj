(ns bluecheese.core
  (:require [bluecheese.article-generator :as article-generator]
            [bluecheese.list-generator :as list-generator]
            [bluecheese.home-generator :as home-generator]
            [bluecheese.rss-generator :as rss-generator]
            [bluecheese.ui :as ui])
  (:use [bluecheese.config :only [config]]))


(def ops
  (array-map
    ; 'article' generates dist directory. It should run first.
    "article" (fn [env-configs]
                (article-generator/main env-configs))
    "copy-ui" (fn [env-configs]
                (ui/main env-configs))
    "home" (fn [env-configs]
             (home-generator/main env-configs))
    "list" (fn [env-configs]
             (list-generator/main env-configs))
    "rss" (fn [env-configs]
            (rss-generator/main env-configs))))



(defn run [op env-configs]
  ((ops op) env-configs))


(defn run-all [env-configs]
  (doseq [op (keys ops)]
    (run op env-configs)))


(defn -main [& args]
  (if (= (count args) 2)
    (let [op (nth args 0)
          env (nth args 1)
          env-configs (config env)]
      (cond
        (= op "all") (run-all env-configs)
        :else (run op env-configs)))
    (throw (Exception. "Op and Env is needed e.g., lein run all local"))))
