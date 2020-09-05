(defproject bluecheese "0.1.0-SNAPSHOT"
  :description "bluecheese"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [markdown-clj "1.10.5"]
                 [cljstache "2.0.6"]
                 [me.raynes/fs "1.4.6"]
                 [clojure.java-time "0.3.2"]
                 [clj-rss "0.2.5"]]
  ;; https://github.com/weavejester/lein-auto
  :plugins [ [lein-auto "0.1.3"]]
  ;; monitor contents directory to regenerate when some of them change
  ;; e.g., lein auto run all ui
  :auto {"run" {:file-pattern #"\.(html|js|css)$"
                :paths ["resources/web"]}}
  :repl-options {:init-ns bluecheese.core}
  :main bluecheese.core)
