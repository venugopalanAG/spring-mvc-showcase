(ns my-first-pipeline.pipeline
  (:use [lambdacd.steps.control-flow]
        [my-first-pipeline.steps])
  (:require
        [lambdacd.steps.manualtrigger :as manualtrigger]))

(def pipeline-def
  `(
     (either
       manualtrigger/wait-for-manual-trigger
       wait-for-repo)
     (with-workspace
       clone
       run-some-tests
       mvn-install
       copy-all)))

