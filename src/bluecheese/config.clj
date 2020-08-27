(ns bluecheese.config)


(def common
  {;; html
   :article-template-path           "web/template/article-template.html"
   :common-head                     "web/template/common-head.html"
   :dist-path                       "dist"})


(def local {})

(def prod {})

(defn config [env]
  ({:local (merge common local)
    :prod  (merge common prod)}
   (keyword env)))




