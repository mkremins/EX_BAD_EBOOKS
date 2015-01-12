(ns cryptic.test-vocab
  (:use clojure.test)
  (:require [cryptic.vocabulary :as vocab]))

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
