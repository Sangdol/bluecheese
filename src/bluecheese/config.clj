(ns bluecheese.config)

(def dist-path "dist")

(def common
  {:kr-md-path                      "md/kr/blog"
   :article-template-path           "web/template/article-template.html"
   :common-head                     "web/template/common-head.html"
   :kr-blog-path                    (str dist-path "/blog")})


(def local {})

(def prod {})

(defn config [env]
  ({:local (merge common local)
    :prod  (merge common prod)}
   (keyword env)))




