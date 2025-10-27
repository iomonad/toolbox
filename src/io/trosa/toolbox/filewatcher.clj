(ns io.trosa.toolbox.filewatcher
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async
             :refer [go <! >! chan go-loop go]])
  (:import java.io.File
           java.nio.file.FileSystems
           java.nio.file.WatchService
           java.nio.file.StandardWatchEventKinds
           java.nio.file.WatchKey
           java.nio.file.ClosedWatchServiceException
           java.util.concurrent.TimeUnit))

;; _____________      __
;; \_   _____/  \    /  \
;;  |    __) \   \/\/   /
;;  |     \   \        /
;;  \___  /    \__/\  /
;;      \/          \/

(def ^:private default-events
  #{StandardWatchEventKinds/ENTRY_CREATE
    StandardWatchEventKinds/ENTRY_DELETE
    StandardWatchEventKinds/ENTRY_MODIFY})

(defn- create-service
  "Create a new `WatchService` instance."
  {:added "1.1.0"}
  ^WatchService []
  (.newWatchService (FileSystems/getDefault)))

(defn- close-service
  "Close `WatchService` instance"
  {:added "1.1.0"}
  ([^WatchService svc]
   (when svc
     (.close svc))))

(defn- poll
  ""
  {:added "1.1.0"}
  ([^WatchService svc]
   (poll svc 3600 TimeUnit/MILLISECONDS))
  ([^WatchService svc timeout ^TimeUnit unit]
   (.poll svc timeout unit)))

(defn- ->coerce-path [target]
  (condp = (type target)
    java.io.File (.toPath target)
    java.nio.file.Path target
    java.lang.String (->coerce-path (io/file target))

    nil))

(defn- register-watch-service!
  "Register "
  ([target ^WatchService svc]
   (register-watch-service! target svc {:events default-events}))
  ([target ^WatchService svc {:keys [events] :as _options}]
   (when-let [path (->coerce-path target)]
     (.register path svc (into-array events)))))
