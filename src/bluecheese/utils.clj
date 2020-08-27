(ns bluecheese.utils
  (:require [clojure.string :as str]))


(defn multiline [& ss]
  (str/join "\n" ss))
