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
   :rss-path           (str dist-path "/index.xml")
   :web                   "web"
   :article-template-path "web/template/kr/article-template.html"
   :fixed-template-path   "web/template/kr/fixed-template.html"
   :list-template-path    "web/template/kr/list-template.html"
   :common-head           "web/template/kr/common-head.html"
   :common-header         "web/template/kr/common-header.html"
   :common-footer         "web/template/kr/common-footer.html"
   :blog-path          (str dist-path "/blog") ;; it's not '/kr' due to a historical reason (aka hugo)
   :blog-url           (str base-url "/blog")})

(def en
  {
   ;; 'title' is needed to set a title on list pages.
   ;; 'blog-title' is needed to prefix titles on article pages.
   ;; TODO Update info
   :blog-info             {:title "Sanghyun Lee"
                           :blog-title "Sanghyun Lee"
                           :description   "Software engineer"}
   :md-path            "md/en/blog"
   :rss-path           (str dist-path "/rss.xml")
   :web                   "web"
   :home-template-path    "web/template/en/home-template.html"
   :article-template-path "web/template/en/article-template.html"
   :fixed-template-path   "web/template/en/fixed-template.html"
   :list-template-path    "web/template/en/list-template.html"
   :common-head           "web/template/en/common-head.html"
   :common-header         "web/template/en/common-header.html"
   :common-footer         "web/template/en/common-footer.html"
   :blog-path          (str dist-path "/en")
   :blog-url           (str base-url "/en")})

(def common
  {:blog-image-url     (str base-url "/logo.png")
   :base-url              base-url
   :dist                  dist-path})


(def local {:base-url "http://localhost:8080"})


(def prod {})

(defn config [env]
  ({:local [(merge kr common local) (merge en common local)]
    :prod  [(merge kr common prod) (merge en common prod)]}
   (keyword env)))
