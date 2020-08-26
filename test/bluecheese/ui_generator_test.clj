(ns bluecheese.ui-generator-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.ui-generator :refer :all]))

(deftest interpolate-test
  (is (= "Hello, world!"
         (interpolate-variable "Hello, {{data}}!" "data" "world")))

  (is (= "Hello, new world!"
         (interpolate "Hello, {{adj}} {{noun}}!" ["adj" "noun"] ["new" "world"]))))


