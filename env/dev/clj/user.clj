(ns user
  (:require [mount.core :as mount]
            [optima.figwheel :refer [start-fw stop-fw cljs]]
            optima.core))

(defn start []
  (mount/start-without #'optima.core/http-server
                       #'optima.core/repl-server))

(defn stop []
  (mount/stop-except #'optima.core/http-server
                     #'optima.core/repl-server))

(defn restart []
  (stop)
  (start))


