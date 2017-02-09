(ns optima.core
  (:require [reagent.core :as r]
            [secretary.core :as secretary :refer-macros [defroute]]
            [optima.templates :as t]))



(r/render [t/wireframe] (.getElementById js/document "app"))
