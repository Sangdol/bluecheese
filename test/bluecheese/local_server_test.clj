(ns bluecheese.local-server-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.local-server :refer :all]))


;(deftest articles->pages-test
;  (is (= {"abc" "<b>hello</b>"}
;         (articles->pages [{:url-path "abc" :html "<b>hello</b>"}]))))

(deftest handler-test
  (is (= ""
         (handler {:uri "/blog/2020/10/10/test-article"}))))



(deftest uri->path-test
  (is (= (str "resources/md/kr/blog/custom-static-site-generator.md")
         (uri->path "/blog/2020/10/10/custom-static-site-generator"))))

