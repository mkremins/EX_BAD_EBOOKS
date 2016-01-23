(ns cryptic.vocabulary
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn load-vocab-file [name]
  (-> (io/resource (str "vocabulary/" name ".txt"))
      slurp
      str/split-lines))

(def badjectives (load-vocab-file "badjectives"))
(def weirdjectives (load-vocab-file "weirdjectives"))
(def nouns (load-vocab-file "nouns"))
(def verbs (load-vocab-file "verbs"))
