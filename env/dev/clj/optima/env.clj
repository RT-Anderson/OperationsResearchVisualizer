(ns optima.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [optima.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[optima started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[optima has shut down successfully]=-"))
   :middleware wrap-dev})
