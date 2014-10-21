(ns cryptic.core
  (:require [clojure.string :as str]))

(defn prefix []
  [:choose
    [:choose "[ERROR]" "[WARN]" "[FAIL]" "[PANIC]"]
    [:choose "Error:" "Warning:" "Exception:"]
    :nil])

(defn badjective []
  [:choose
    "no such" "bad" "invalid" "illegal" "improper" "missing" "incorrect"])

(defn noun []
  [:choose
    "accusation" "admixture" "assumption" "argument" "axiom" "batch"
    "constraint" "corpus" "gender" "fugitive" "party" "entity" "word" "rule"
    "number" "parameter" "operation" "suggestion" "advice" "insertion"
    "deletion" "removal" "access" "symbol" "variable" "formula" "request"
    "command" "action" "permission" "type" "name" "reference" "object" "plea"
    "article" "inquiry" "query" "quandary" "question" "file" "path" "error"
    "exception" "warning" "violation" "activity" "task" "performance" "branch"
    "case" "switch" "function" "default" "handler" "clause" "handle" "queue"
    "collection" "iterator" "item" "data" "transaction" "transformation"
    "meme" "deity" "invocation" "matrix" "array" "set" "map" "hash" "test"
    "condition" "consequent" "alternative" "scene" "graph" "plot" "wizard"
    "anomaly" "prophecy" "operator" "functor" "application" "thread" "process"
    "overflow" "jail" "escape" "recursion" "depth" "vector" "window"
    "component" "operand" "machinery" "pipe" "widget" "spell" "arrow"
    "database" "package" "statement" "expression"])

(defn subordinate-clause []
  [:choose
    [:span "for" noun]
    [:span "of" noun]
    [:span "in" noun]
    [:span "at" noun]
    :nil :nil :nil])

(defn suffix []
  [:choose
    (str "(" (rand-int 500) ":" (rand-int 100) ")")
    :nil :nil])

(defn error []
  [:span prefix badjective noun subordinate-clause suffix])

(defn emit [doc]
  (cond
    (vector? doc)
      (let [[op & children] doc]
        (case op
          :choose (recur (rand-nth children))
          :span (str/join " " (remove empty? (map emit children)))
          :nil ""))
    (fn? doc) (recur (doc))
    (keyword? doc) (recur [doc])
    (string? doc) doc))
