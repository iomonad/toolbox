(ns io.trosa.toolbox.classpath
  (:require [clojure.walk :refer [keywordize-keys]])
  (:import java.util.Properties))

(defn- ->file-properties
  [file]
  (try
    (let [fr (.. (Thread/currentThread)
                 (getContextClassLoader)
                 (getResourceAsStream file))
          props (Properties.)]
      (.load props fr)
      (keywordize-keys
       (into {} props)))
    (catch Exception _e nil)))

(defn- ->pom-props-path
  [group artifact]
  (str "META-INF/maven/" group "/" artifact "/pom.properties"))

(defn get-artifact-version
  "Retrieve artifact POM properties version, yield nil if
   no found in property file."
  ^{:added "1.0.0"}
  ([^String artifact]
   (System/getProperty (str artifact ".version")))
  ([^String group ^String artifact]
   (or (get-artifact-version artifact)
       (some-> (->pom-props-path group artifact)
               (->file-properties)
               :version))))

(defn get-clojure-version
  "Return current Clojure running version"
  ^{:added "1.1.0"}
  []
  (get-artifact-version "org.clojure" "clojure"))

(defn get-lein-project-name
  "Get current leiningen project name"
  ^{:added "1.1.0"}
  []
  (try
    (-> "project.clj" slurp read-string (nth 1))
    (catch Exception _e
      nil)))

(defn get-lein-project-version
  "Get current leiningen project version"
  ^{:added "1.1.0"}
  []
  (try
    (-> "project.clj" slurp read-string (nth 2))
    (catch Exception _e
      nil)))
