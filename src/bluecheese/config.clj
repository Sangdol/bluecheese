(ns bluecheese.config)

(def home (System/getProperty "user.home"))
(def project-root (str home "/projects/bluecheese"))
(def data (str project-root "/data"))
(def web (str project-root "/bluecheese/resources/web"))

(def common
  {;; html
   :html-template-path              (str web "/template/template.html")
   :html-body-content-path-template (str web "/contents/{{page}}-body.html")
   :html-output-path-template       (str web "/{{page}}.html")})


(def local {})

(def prod {})

(defn config [env]
  ({:local (merge common local)
    :prod  (merge common prod)}
   (keyword env)))




