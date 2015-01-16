(ns cryptic.test-vocab
  (:use clojure.test)
  (:require [cryptic.vocabulary :as vocab]))

;; We define our own custom `duplicates` function instead of just using
;; `(apply distinct? words)` in order to get more useful error messages in the
;; event that one of the tests fails. `(apply distinct? words)` doesn't tell us
;; *which* words are duplicated, whereas `duplicates` does.

(defn duplicates [words]
  (loop [seen #{}
         dupes #{}
         words words]
    (if-let [word (first words)]
      (recur (conj seen word)
             (if (contains? seen word) (conj dupes word) dupes)
             (rest words))
      dupes)))

(deftest no-duplicates
  (is (empty? (duplicates vocab/badjectives)))
  (is (empty? (duplicates vocab/weirdjectives)))
  (is (empty? (duplicates vocab/nouns)))
  (is (empty? (duplicates vocab/verbs))))
