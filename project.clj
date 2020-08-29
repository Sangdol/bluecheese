(defproject bluecheese "0.1.0-SNAPSHOT"
  :description "bluecheese"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [markdown-clj "1.10.5"]
                 [de.ubercode.clostache/clostache "1.4.0"]
                 [me.raynes/fs "1.4.6"]
                 [clojure.java-time "0.3.2"]]
  ;; https://github.com/weavejester/lein-auto
  :plugins [ [lein-auto "0.1.3"]]
  ;; monitor contents directory to regenerate when some of them change
  ;; e.g., lein auto run all ui
  :auto {"run" {:file-pattern #"\.(html|js)$"
                :paths ["resources/web/contents",
                        "resources/web/template"]}}
  :repl-options {:init-ns bluecheese.core}
  :main bluecheese.core)
