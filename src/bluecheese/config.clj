(ns bluecheese.config)

(def base-url "https://iamsang.com")
(def dist-path "dist")

(def common
  {:blog-info             {:kr-blog-title "이상현 IN 베를린"
                           :description   "베를린 사는 소프트웨어 엔지니어 이야기"}
   :kr-md-path            "md/kr/blog"
   :kr-fixed-md-path      "md/kr/fixed"
   :kr-rss-path           (str dist-path "/index.xml")
   :web                   "web"
   :article-template-path "web/template/article-template.html"
   :fixed-template-path   "web/template/fixed-template.html"
   :list-template-path    "web/template/list-template.html"
   :common-head           "web/template/common-head.html"
   :common-header         "web/template/common-header.html"
   :common-footer         "web/template/common-footer.html"
   :kr-blog-path          (str dist-path "/blog")
   :kr-blog-url           (str base-url "/blog")
   :kr-blog-image-url     (str base-url "/ogimage.png")
   :base-url              base-url
   :dist                  dist-path})


(def local {:base-url "http://localhost:8080"})


(def prod {})

(defn config [env]
  ({:local (merge common local)
    :prod  (merge common prod)}
   (keyword env)))




