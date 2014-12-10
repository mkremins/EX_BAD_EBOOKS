(ns cryptic.generate
  (:require [clojure.string :as str]
            [cryptic.vocabulary :as vocab]))

;; helpers

(defn choice [coll]
  (fn [] (rand-nth coll)))

(defn span [& args]
  (str/join " " (remove empty? args)))

(defn upcase [s]
  (if (empty? s)
    s
    (str (str/upper-case (str (first s)))
         (str/join (rest s)))))

;; parts of speech

(def badjective' (choice (conj vocab/badjectives "no such")))
(def badjective (choice vocab/badjectives))
(def weirdjective (choice vocab/weirdjectives))
(def verb (choice vocab/verbs))
(def noun (choice vocab/nouns))

(defn noun-phrase []
  (if (> (rand) 3/4)
    (str (noun) " " (noun))
    (noun)))

;; other generators

(defn prefix []
  (condp > (rand)
    1/3 (rand-nth ["[ERROR]" "[WARN]" "[FAIL]" "[PANIC]"])
    2/3 (rand-nth ["Error:" "Warning:" "Exception:"])
    nil))

(defn camel-case-exception []
  (str/join
    (map upcase
      [(condp > (rand)
         2/4 (weirdjective)
         3/4 (badjective)
         nil)
       (noun) (rand-nth ["error" "exception"]) ":"])))

(defn couldnt-clause []
  (span "couldn't" (verb)
        (condp > (rand)
          1/4 (weirdjective)
          2/4 (badjective)
          nil)
        (str (noun-phrase) ":")))

(defn midfix []
  (condp > (rand)
    1/5 (camel-case-exception)
    2/5 (couldnt-clause)
    nil))

(defn context-clause []
  (when (> (rand) 1/2)
    (str (rand-nth ["for" "of" "in" "at"]) " " (noun))))

(defn expected-clause []
  (when (> (rand) 2/3)
    (str "(expected " (noun-phrase) ", got " (noun-phrase) ")")))

(defn location-clause []
  (when (> (rand) 2/3)
    (str "(" (rand-int 500) ":" (rand-int 100) ")")))

(defn error []
  (span (prefix) (midfix) (badjective') (noun-phrase) (context-clause)
        (or (location-clause) (expected-clause))))
