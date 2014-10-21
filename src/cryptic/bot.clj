(ns cryptic.bot
  (:require [cryptic.emit :refer [emit error]]
            [cronj.core :refer [cronj start!]])
  (:use [twitter.oauth]
        [twitter.api.restful])
  (:gen-class))

(def creds
  (make-oauth-creds
    (System/getenv "EBOOKS_CONSUMER_KEY")
    (System/getenv "EBOOKS_CONSUMER_SECRET")
    (System/getenv "EBOOKS_OAUTH_TOKEN")
    (System/getenv "EBOOKS_OAUTH_TOKEN_SECRET")))

(defn tweet [_ _]
  (let [status (emit error)]
    (println (str "Tweeting: " status))
    (statuses-update :oauth-creds creds :params {:status status})))

(def scheduler
  (cronj :entries [{:id "tweet-task"
                    :handler tweet
                    :schedule "0 0 /2 * * * *"}]))

(defn -main []
  (println "Starting up...")
  (start! scheduler)
  (println "Started!"))
