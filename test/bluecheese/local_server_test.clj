(ns bluecheese.local-server-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.local-server :refer :all]))

(deftest uri->path-test
  (is (= "resources/md/kr/blog/custom-static-site-generator.md"
         (uri->path "/blog/2020/10/10/custom-static-site-generator" (first env-configs))))

  (is (= "resources/md/kr/blog/fixed-about.md"
         (uri->path "/about" (first env-configs)))))

