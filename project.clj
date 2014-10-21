(defproject cryptic "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [im.chit/cronj "1.4.2"]
                 [twitter-api "0.7.7"]]
  :min-lein-version "2.0.0"
  :main ^:skip-aot cryptic.bot
  :uberjar-name "cryptic-standalone.jar"
  :profiles {:uberjar {:aot :all}})
