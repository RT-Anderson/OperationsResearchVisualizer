(ns optima.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[optima started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[optima has shut down successfully]=-"))
   :middleware identity})
