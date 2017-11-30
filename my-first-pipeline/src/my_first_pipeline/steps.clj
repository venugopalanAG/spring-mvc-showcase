(ns my-first-pipeline.steps
  (:require [lambdacd.steps.shell :as shell]
            [lambdacd-git.core :as lambdacd-git]))


(def repo-uri "https://github.com/spring-projects/spring-mvc-showcase.git")
;;(def repo-uri "https://github.com/jitpack/maven-simple.git")
;;(def repo-uri "https://github.com/venugopalanAG/appfuse.git")
(def repo-branch "master")
(def cwd "/home/ubuntu/git/")

(defn some-step-that-does-nothing [args ctx]
  {:status :success})

(defn some-step-that-echos-foo [args ctx]
  (shell/bash ctx "/" "echo foo"))

(defn some-step-that-echos-bar [args ctx]
  (shell/bash ctx "/" "echo bar"))

(defn some-failing-step [args ctx]
  (shell/bash ctx "/" "echo \"i am going to fail now...\"" "exit 1"))


(defn clear-repo [args ctx]
  (shell/bash ctx cwd "rm -rf /home/ubuntu/git"))

(defn wait-for-repo [args ctx]
  (lambdacd-git/wait-for-git ctx repo-uri :ref (str "refs/heads/" repo-branch)))

(defn clone [args ctx]
  (let [revision (:revision args)
        cwd      (:cwd args)
        ref      (or revision repo-branch)]
    (lambdacd-git/clone ctx repo-uri ref cwd)))

(defn run-some-tests [args ctx]
  (shell/bash ctx (:cwd args) "mvn test"))

(defn mvn-install [args ctx]
  (shell/bash ctx (:cwd args) "mvn install"))


(defn copy-all [args ctx]
  (shell/bash ctx (:cwd args) "cp -r * /home/ubuntu/copied/"))

(defn build-image [args ctx]
  (shell/bash ctx (:cwd args) "docker build -t ideas2it ."))

(defn tag-image [args ctx]
  (shell/bash ctx (:cwd args) "docker tag ideas2it 517994239768.dkr.ecr.us-east-1.amazonaws.com/ideas2it"))


(defn ecr-login [args ctx]
  (shell/bash ctx (:cwd args) "aws ecr get-login > /tmp/1 && sed -i 's/-e none//g' /tmp/1 && bash /tmp/1"))
