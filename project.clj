(defproject bluecheese "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "1.0.0"]
                 [org.clojure/data.csv "1.0.0"]]
  ;; https://github.com/weavejester/lein-auto
  :plugins [ [lein-auto "0.1.3"]]
  ;; monitor contents directory to regenerate when some of them change
  ;; e.g., lein auto run all ui
  :auto {"run" {:file-pattern #"\.(html|js)$"
                :paths ["resources/web/contents",
                        "resources/web/template"]}}
  :repl-options {:init-ns bluecheese.core}
  :main bluecheese.core)
