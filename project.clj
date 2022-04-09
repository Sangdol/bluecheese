(defproject bluecheese "0.1.0-SNAPSHOT"
  :description "bluecheese"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 ^{:voom {:repo "https://github.com/sangdol/markdown-clj" :branch "improve-heading-anchors"}}
                 [markdown-clj "1.11.0-20220409_171002-gbc58c78"]
                 [cljstache "2.0.6"]
                 [me.raynes/fs "1.4.6"]
                 [clojure.java-time "0.3.2"]
                 [clj-rss "0.2.7"]
                 [ring "1.8.2"]
                 [cheshire "5.10.0"]]
  :ring {:handler bluecheese.local-server/app}
  ;; https://github.com/weavejester/lein-auto
  :plugins [[lein-auto "0.1.3"]
            [lein-ring "0.12.5"]]
  ;; monitor contents directory to regenerate when some of them change
  ;; e.g., lein auto run all ui
  :auto {"run" {:file-pattern #"\.(html|js|css)$"
                :paths        ["resources/web"]}}
  :repl-options {:init-ns bluecheese.core}
  :main bluecheese.core)
