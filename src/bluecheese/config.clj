(ns bluecheese.config)

(def home (System/getProperty "user.home"))
(def project-root (str home "/projects/corona-project"))
(def data (str project-root "/data"))
(def web (str project-root "/bluecheese/resources/web"))

(def common
  {:csv-owid-data-path              (str data "/owid-covid-data.csv")
   ;; js
   :json-daily-data-path            (str data "/europe-data.json")
   :js-template-path                (str web "/template/bluecheese-milk.js")
   :js-output-path                  (str web "/dist/bluecheese-cheese.js")
   ;; html
   :html-template-path              (str web "/template/template.html")
   :html-body-content-path-template (str web "/contents/{{page}}-body.html")
   :html-output-path-template       (str web "/{{page}}.html")})


(def local {})

(def prod {})

(defn config [env]
  ({:local (merge common local)
    :prod  (merge common prod)}
   (keyword env)))




