(ns bluecheese.article-generator-test
  (:require [clojure.test :refer :all]
            [bluecheese.article-generator :refer :all]
            [bluecheese.utils :as utils]))

(deftest parse-variables-test
  (is (= {"abc" "def"}
         (parse-variables "abc = def")))
  (is (= {"abc" "def g"}
         (parse-variables "abc = \"def g\"")))
  (is (= {"abc" "def g"
          "c" "1"}
         (parse-variables (utils/multiline "abc = \"def g\""
                                           "c=1")))))


(deftest md->map-test
  (is (= {"date" "2018-10-10"
          "slug" "test-slug"
          "url-path" "2018/10/10/test-slug"
          "body" "<h1>hello</h1>"}
         (md->map (utils/multiline "+++"
                                   "date=2018-10-10"
                                   "slug=test-slug"
                                   "+++"
                                   "# hello")))))


(deftest url-path-test
  (is (= "2020/08/27/hello-world"
         (url-path {"date" "2020-08-27"
                    "slug" "hello world"})))
  (is (= "2020/08/27/hello-world"
         (url-path {"date" "2020-08-27T04:53:40+02:00\""
                    "slug" "hello-world"})))

  (is (= "/hello-world"
         (url-path {"date" ""
                    "slug" "hello world"}))))
