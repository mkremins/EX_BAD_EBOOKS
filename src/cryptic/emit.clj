(ns cryptic.emit
  (:require [clojure.string :as str]))

(defn prefix []
  [:choose
    [:choose "[ERROR]" "[WARN]" "[FAIL]" "[PANIC]"]
    [:choose "Error:" "Warning:" "Exception:"]
    :nil])

(def badjectives
  ["bad" "invalid" "illegal" "improper" "missing" "incorrect" "insufficient"
   "ambiguous" "malformed"])

(defn badjective' []
  (rand-nth (conj badjectives "no such")))

(defn badjective []
  (rand-nth badjectives))

(def weirdjectives
  ["ambitious" "operatic" "cryptic" "arcane" "archaic" "deliberate" "malicious"
   "aggressive" "ambient" "mysterious" "abundant" "incessant" "weird" "strange"
   "falsifiable" "impetuous" "skeptical" "forgettable" "laborious"
   "performative" "impeccable" "hasty" "pneumatic" "arable" "incandescent"
   "illegible" "aromatic" "flammable" "unfriendly" "omniscient" "benevolent"
   "suboptimal" "unrecoverable" "moribund" "gelatinous" "ecstatic"
   "indivisible" "implausible" "vacillating" "abhorrent" "abstract"
   "productive" "intangible" "factual" "irrelevant" "monochromatic"
   "undecidable" "entertaining" "fatuous" "gratuitous" "ingenuous"
   "superfluous" "ironic" "ambivalent" "philosophical" "majestic" "grandiose"
   "inarticulate" "immutable" "indirect" "monadic" "variadic" "inevitable"
   "colossal" "mellifluous" "recondite" "abstruse" "recalcitrant" "obstinate"
   "perverse" "defiant" "difficult" "wayward" "misleading" "inferior"
   "laborious" "stereotypical" "obscure" "inscrutable" "excruciating"
   "phosphorescent" "unfinished" "unbalanced" "impartial" "unqualified"
   "derivative" "unoriginal" "particulate"])

(defn weirdjective []
  (rand-nth weirdjectives))

(def nouns
  ["accusation" "admixture" "assumption" "argument" "axiom" "batch"
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
   "database" "package" "statement" "expression" "justification" "parse"
   "tree" "parser" "analyzer" "loader" "linker" "library" "module" "routine"
   "class" "hierarchy" "attribute" "method" "callback" "monad" "architecture"
   "target" "assembler" "language" "line" "column" "row" "scope" "closure"
   "binding" "abstraction" "entry" "token" "trace" "hack" "dependency"
   "resolution" "keyword" "stopword" "atom" "procedure" "directory" "mode"
   "user" "flag" "box" "property" "phrase" "traceback" "stack" "heap"
   "state" "arity" "leaf" "signal" "password" "character" "factory" "filter"
   "lambda" "recipe" "transducer" "system" "interface" "implementation"
   "logic" "form" "syntax" "icon" "effigy" "comment" "document" "namespace"
   "primitive" "lexer" "reader" "loop" "event" "predicate" "promise" "job"
   "rune" "monolith" "judge" "spline"])

(defn noun []
  (rand-nth nouns))

(defn subordinate-clause []
  [:choose
    [:span "for" noun]
    [:span "of" noun]
    [:span "in" noun]
    [:span "at" noun]
    :nil :nil :nil])

(defn upcase [s]
  (if (empty? s)
    s
    (str (str/upper-case (str (first s)))
         (str/join (rest s)))))

(defn camel-case-exception []
  (str/join
    (map upcase
         [(if (> (rand) 0.5)
            (if (> (rand) 0.5) (badjective) "")
            (weirdjective))
          (rand-nth nouns)
          (rand-nth ["error" "exception"])
          ":"])))

(defn midfix []
  [:choose camel-case-exception :nil :nil])

(defn suffix []
  [:choose
    (str "(" (rand-int 500) ":" (rand-int 100) ")")
    :nil :nil])

(defn error []
  [:span prefix midfix badjective' noun subordinate-clause suffix])

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
