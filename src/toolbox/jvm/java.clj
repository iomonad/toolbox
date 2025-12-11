(ns toolbox.jvm.java
  (:require [clojure.reflect :as r]
            [clojure.pprint :refer [print-table]]
            [clojure.string :as str]
            [toolbox.collections :refer [deep-merge]]))

(defn class-methods
  "Retrieve all the class methods"
  {:added "1.1.0"}
  [object]
  (->> (r/reflect object)
       :members
       (filter :exception-types)
       (sort-by :name)))

(defn print-class-methods
  "Print as table all the methods of a class Object"
  {:added "1.1.0"}
  [object]
  (some-> (class-methods object)
          (print-table)))

(defn get-properties-map
  "Return all the properties, by nested map"
  {:added "1.1.0"}
  []
  (->> (System/getProperties)
       (map (fn [[k v]]
              (let [k' (mapv keyword (str/split k #"\."))]
                (assoc-in {} k' v))))
       (apply deep-merge)))

(defn java-informations
  "Return compiled JVM/JAVA informations as structured
   map"
  {:added "1.1.0"}
  []
  (:java (get-properties-map)))
