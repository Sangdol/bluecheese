(ns bluecheese.local-server-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.local-server :refer :all]))


(deftest read-md-as-html-test
  (is (= "<!DOCTYPE html>\n<html lang=\"ko"
         (subs (read-md-as-html "resources/md/kr/test/test-article.md")
               0 30))))


(deftest uri->path-test
  (is (= "resources/md/kr/blog/custom-static-site-generator.md"
         (uri->path "/blog/2020/10/10/custom-static-site-generator")))

  (is (= "resources/md/kr/blog/fixed-about.md"
         (uri->path "/about"))))

