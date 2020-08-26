(ns bluecheese.article-generator-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.article-generator :refer :all]))

(deftest parse-variables-test
  (is (= {"abc" "def"}
         (parse-variables "abc = def")))
  (is (= {"abc" "def g"}
         (parse-variables "abc = \"def g\"")))
  (is (= {"abc" "def g"
          "c" "1"}
         (parse-variables (str "abc = \"def g\"\n"
                               "c=1")))))


(deftest convert-md-to-html-test
  (is (= {:variables {"abc" "def"}
          :html "<h1>hello</h1>"}
         (convert-md-to-html (str "+++\n"
                                  "abc=def\n"
                                  "+++\n"
                                  "# hello")))))
