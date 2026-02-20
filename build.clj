(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib       'io.trosa/toolbox)
(def version   "1.1.6-SNAPSHOT")
(def class-dir "target/classes")
(def jar-file  (format "target/%s-%s.jar" (name lib) version))
(def basis     (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(def pom-data-base
  {:scm {:url "http://github.com/iomonad/toolbox"
         :connection "scm:git:git://github.com/iomonad/toolbox.git"
         :developerConnection "scm:git:ssh://git@github.com/iomonad/toolbox.git"
         :tag version}
   :pom-data [[:licenses
               [:license
                [:name "Eclipse Public License 2.0"]
                [:url "http://www.eclipse.org/legal/epl-2.0"]]]
              [:organisation "io.trosa"]]})

(defn jar [_]
  (b/write-pom (merge
                pom-data-base
                {:class-dir class-dir
                 :lib lib
                 :version version
                 :basis @basis
                 :src-dirs ["src"]}))
  (b/copy-dir  {:src-dirs ["src" "resources"]
                :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file})
  (println "JAR" jar-file))

(defn deploy [_]
  (dd/deploy {:installer :remote
              :artifact jar-file
              :pom-file (b/pom-path {:lib lib :class-dir class-dir})}))
