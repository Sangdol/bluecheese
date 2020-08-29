(ns bluecheese.config)

(def dist-path "dist")

(def common
  {:kr-md-path          "md/kr/blog"
   :kr-fixed-md-path    "md/kr/fixed"
   :web                 "web"
   :basic-template-path "web/template/basic-template.html"
   :list-template-path  "web/template/list-template.html"
   :common-head         "web/template/common-head.html"
   :kr-blog-path        (str dist-path "/blog")
   :dist                dist-path})


(def local {})

(def prod {})

(defn config [env]
  ({:local (merge common local)
    :prod  (merge common prod)}
   (keyword env)))




