(ns io.trosa.toolbox.collections)

(defn- merge-objs
  ^{:added "1.1.0"}
  [{:keys [merge-colls
           scalar-into-coll]
    :or {scalar-into-coll false
         merge-colls true}
    :as opts} a b]
  (cond
    (nil? a)                        b
    (nil? b)                        a
    (and (map? a) (map? b))         (merge-with (partial merge-objs opts) a b)
    (and merge-colls
         (coll? a) (coll? b))       (into a b)
    (and scalar-into-coll
         (coll? a) (not (coll? b))) (conj a b)
    :else                           b))

(defn deep-merge-with-opts
  "Deep-merge with options"
  ^{:added "1.1.0"}
  ([_merge-opts] nil)
  ([merge-opts obj & objs]
   (reduce (partial merge-objs merge-opts) obj objs)))

(defn deep-merge
  "Deep merge the structures"
  ^{:added "1.1.0"}
  [& objs]
  (apply deep-merge-with-opts {} objs))
