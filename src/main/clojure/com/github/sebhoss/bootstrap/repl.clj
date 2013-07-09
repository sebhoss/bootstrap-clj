;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See "http://www.wtfpl.net/":http://www.wtfpl.net/ for
;   more details.

(ns com.github.sebhoss.bootstrap.repl
  (:require [clojure.java.classpath :refer [classpath]]
            [clojure.tools.namespace.find :refer [find-namespaces]]
            [clojure.string :refer [blank? split]]))

(defn- ns-alias [^clojure.lang.Symbol namespace]
  (symbol (last (split (.toString namespace) #"\."))))

(defn- ns-alias-split [namespace]
  (vector namespace (ns-alias namespace)))

(defn- require-namespace [[namespace alias]]
  (require (vector namespace :as alias)))

(defn load-namespaces [regex]
  (let [project-namespace? #(not (blank? (re-find regex (.toString %))))
        namespaces (find-namespaces (filter project-namespace? (classpath)))
        namespace-aliases (map ns-alias-split namespaces)]
    (when (empty? (remove nil? (map require-namespace namespace-aliases)))
      :ok)))

(defn load-helpers []
  (require '[clojure.tools.namespace.repl :refer [refresh]])
  (require '[clojure.repl :refer :all])
  (require '[clojure.test :refer :all]))
