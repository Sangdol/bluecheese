(ns bluecheese.config)

(def base-url "https://iamsang.com")
(def dist-path "dist")

(def kr
  {
   ;; 'title' is needed to set a title on list pages.
   ;; 'blog-title' is needed to prefix titles on article pages.
   :blog-info             {:title "이상현 IN 베를린"
                           :blog-title "이상현 IN 베를린"
                           :description   "베를린 사는 소프트웨어 엔지니어 이야기"}
   :md-path            "md/kr/blog"
   :fixed-md-path      "md/kr/fixed"
   :rss-path           (str dist-path "/index.xml")
   :web                   "web"
   :article-template-path "web/template/article-template.html"
   :fixed-template-path   "web/template/fixed-template.html"
   :list-template-path    "web/template/list-template.html"
   :common-head           "web/template/common-head.html"
   :common-header         "web/template/common-header.html"
   :common-footer         "web/template/common-footer.html"
   :blog-path          (str dist-path "/blog")
   :blog-url           (str base-url "/blog")})

;; TODO
(def en
  {
   ;; 'title' is needed to set a title on list pages.
   ;; 'blog-title' is needed to prefix titles on article pages.
   :blog-info             {:title "Sanghyun Lee"
                           :blog-title "Sanghyun Lee"
                           :description   "베를린 사는 소프트웨어 엔지니어 이야기"}
   :md-path            "md/kr/blog"
   :fixed-md-path      "md/kr/fixed"
   :rss-path           (str dist-path "/index.xml")
   :web                   "web"
   :article-template-path "web/template/article-template.html"
   :fixed-template-path   "web/template/fixed-template.html"
   :list-template-path    "web/template/list-template.html"
   :common-head           "web/template/common-head.html"
   :common-header         "web/template/common-header.html"
   :common-footer         "web/template/common-footer.html"
   :blog-path          (str dist-path "/blog")
   :blog-url           (str base-url "/blog")})

(def common
  {:blog-image-url     (str base-url "/logo.png")
   :base-url              base-url
   :dist                  dist-path})


(def local {:base-url "http://localhost:8080"})


(def prod {})

(defn config [env]
  ({:local [(merge kr common local)] ;; (merge en common local)]
    :prod  [(merge kr common prod)]} ;; (merge en common prod)]}
   (keyword env)))




