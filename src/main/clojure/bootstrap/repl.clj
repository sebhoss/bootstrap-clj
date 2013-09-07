;
; Copyright © 2013 Sebastian Hoß <mail@shoss.de>
; This work is free. You can redistribute it and/or modify it under the
; terms of the Do What The Fuck You Want To Public License, Version 2,
; as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
;

(ns bootstrap.repl
  (:require [clojure.tools.namespace.find :refer [find-namespaces-in-dir]]
            [clojure.string :refer [blank? split]]
            [clojure.test :as test]))

(defn- ns-alias [namespace]
  (symbol (last (split (str namespace) #"\."))))

(defn- ns-alias-split [namespace]
  (vector namespace (ns-alias namespace)))

(defn- require-namespace [[namespace alias]]
  (require (vector namespace :as alias)))

(defn load-ns-aliased
  "Loads the given namespaces aliased with their last namespace-segment."
  [namespaces]
  (let [namespace-aliases (map ns-alias-split namespaces)]
    (when (empty? (remove nil? (map require-namespace namespace-aliases)))
      :ok)))

(defn load-ns-in-dir-aliased
  "Loads all namespaces in the given directory (recursively)."
  [^String dir]
  (load-ns-aliased (find-namespaces-in-dir (java.io.File. dir))))

(defn load-helpers
  "Loads REPL helper functions. Includes every var from:
    - clojure.tools.namespace.repl
    - clojure.repl
    - clojure.test"
  []
  (require '[clojure.tools.namespace.repl :refer :all])
  (require '[clojure.repl :refer :all])
  (require '[clojure.test :refer :all]))

(defn test-shortcut
  "Defines a shortcut for (run-all-tests) called (rat) which can be called without any argument
   to execute all tests matching the given macro regex. Otherwise the given parameter for (rat)
   is used to determine which tests to run.

   Parameter:
     * regex - Regular expression to match all namespaces which contain tests

   Example:
     * (def rat (test-shortcut #\"foo.bar.*-test\"))
       => Calling (rat) runs all tests in namespaces matching the above regex.
          Calling (rat \"baz\") runs all tests inside the 'foo.bar.baz-test' namespace."
  [regex]
  (fn
    ([] (test/run-all-tests regex))
    ([namespace]
      (let [ns-regex (clojure.string/replace (str regex) #"\*" "%s")]
        (test/run-all-tests (re-pattern (format ~ns-regex ~'namespace)))))))
