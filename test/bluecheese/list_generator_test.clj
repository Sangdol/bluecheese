(ns bluecheese.list-generator-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.list-generator :refer :all]))

(deftest formatted-date-test
  (is (= "Feb 4, 2019"
         (formatted-date "2019-02-04T20:17:00+01:00"))))

