(ns bluecheese.config-test
  (:require [clojure.test :refer :all])
  (:require [bluecheese.config :as cf])
  (:require [bluecheese.config :refer [config]]))

(deftest config-test
  (is (= (str cf/project-root "/data/owid-covid-data.csv")
         ((config "local") :csv-owid-data-path))))

