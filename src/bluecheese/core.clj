(ns bluecheese.core
  (:use [bluecheese.static-generator :only [generate-static-pages]]
        [bluecheese.article-generator :only [generate-article-pages]]
        [bluecheese.ui :only [copy-ui]]
        [bluecheese.config :only [config]]))

(def ops
  {"copy-ui" (fn [env-config]
               (copy-ui env-config))
   "article" (fn [env-config]
               (generate-article-pages env-config))
   "static"  (fn [env-config]
               (generate-static-pages env-config))})


(defn run [op env-config]
  ((ops op) env-config))


(defn run-all [env-config]
  (doseq [op (keys ops)]
    (run op env-config)))


(defn -main [& args]
  (if (= (count args) 2)
    (let [op (nth args 0)
          env (nth args 1)
          env-config (config env)]
      (cond
        (= op "all") (run-all env-config)
        :else (run op env-config)))
    (throw (Exception. "Op and Env is needed e.g., lein run all local"))))
