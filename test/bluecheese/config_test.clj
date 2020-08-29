(ns bluecheese.config-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.config :refer :all]))

(deftest config-test
  (is (= "web"
         ((config "local") :web))))

