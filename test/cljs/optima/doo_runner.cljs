(ns optima.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [optima.core-test]))

(doo-tests 'optima.core-test)

