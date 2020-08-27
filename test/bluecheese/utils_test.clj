(ns bluecheese.utils-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.utils :refer [multiline]]))

(deftest multiline-test
  (is (= "a\nb\nc"
         (multiline "a"
                    "b"
                    "c"))))

