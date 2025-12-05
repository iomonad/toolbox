(ns io.trosa.toolbox.jvm.classpath
  (:require [clojure.walk :refer [keywordize-keys]]
            [io.trosa.toolbox.jvm.java :refer [java-informations]]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [print-table]])
  (:import java.util.Properties))

(defn- ->file-properties
  "Convert properties file to map"
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
  "Resolve the path from META-INF"
  [group artifact]
  (str "META-INF/maven/" group "/" artifact "/pom.properties"))

(defn get-artifact-version
  "Retrieve artifact POM properties version, yield nil if
   no found in property file."
  {:added "1.0.0"}
  ([^String artifact]
   (System/getProperty (str artifact ".version")))
  ([^String group ^String artifact]
   (or (get-artifact-version artifact)
       (some-> (->pom-props-path group artifact)
               (->file-properties)
               :version))))

(defn get-clojure-version
  "Return current Clojure running version

   Note: based from META-INF and not the actual leiningen project
         file."
  {:added "1.1.0"}
  []
  (get-artifact-version "org.clojure" "clojure"))

(defn get-lein-project-name
  "Get current leiningen project name, from project definition"
  {:added "1.1.0"}
  []
  (try
    (-> "project.clj" slurp read-string (nth 1))
    (catch Exception _e
      nil)))

(defn get-lein-project-version
  "Get current leiningen project version, from project definition"
  {:added "1.1.0"}
  []
  (try
    (-> "project.clj" slurp read-string (nth 2))
    (catch Exception _e
      nil)))

(defn cp-jars
  "List JAR's the current class-path"
  {:added "1.1.0"}
  []
  (->> (get-in (java-informations) [:class :path])
       (re-seq #"[^;]+")
       (mapcat (fn [x]
                 (str/split x #":")))
       (filter (fn [x] (str/ends-with? x ".jar")))
       (map io/file)))

(defn get-cp-libraries
  "Retrieve all the libraries version as map tree.

  Example:
     (get-cp-libraries)

     ;; => {\"org.clojure/tools.namespace\" \"1.5.0\"
             \"nrepl/repl\" \"1.3.1\"
             ......"
  {:added "1.1.0"}
  []
  (->> (cp-jars)
       (map (fn [jar]
              (let [cpath (-> (str/split (str jar) #"\/repository/")
                              (last))
                    cpp (->> (str/split cpath #"\/")
                             (drop-last 2))
                    artifact (last cpp)]
                (if (= 1 (count cpp))
                  [artifact]
                  [(str/join "." (drop-last cpp)) artifact]))))
       (reduce (fn [acc art]
                 (let [version (apply get-artifact-version art)]
                   (assoc acc (str/join "/" art) version)))
               {})))

(defn dump-cp-libraries
  "Dump Clojure libraries versions as table


  Example:

   (dump-cp-libraries)

|                     :artefact | :version |
|-------------------------------+----------|
|                 cider/orchard |   0.36.0 |
|   org.clojure/tools.namespace |    1.5.0 |
|      org.clojure/tools.reader |    1.4.0 |
|               mx.cider/logjam |    0.3.0 |
|  org.clojure/core.specs.alpha |   0.4.74 |
| refactor-nrepl/refactor-nrepl |          |
|               io.aviso/pretty |    1.1.1 |
|        org.clojure/spec.alpha |  0.5.238 |
|                   nrepl/nrepl |    1.3.1 |
|             cider/cider-nrepl |          |
|          org.nrepl/incomplete |    0.1.0 |"
  {:added "1.1.0"}
  []
  (->> (get-cp-libraries)
       (map (partial zipmap [:artefact :version]))
       (print-table)))
