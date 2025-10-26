(defproject io.trosa/toolbox "1.1.0-SNAPSHOT"
  :description "Utils and Algorithms used on daily basis"
  :url "https://github.com/iomonad/toolbox"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :deploy-repositories [["snapshots" {:url "https://repo.clojars.org"
                                      :username :env/clojars_username
                                      :password :env/clojars_password
                                      :sign-releases false}]
                        ["releases"  {:url "https://repo.clojars.org"
                                      :username :env/clojars_username
                                      :password :env/clojars_password
                                      :sign-releases false}]]
  :min-lein-version "2.5.3"
  :dependencies [[org.clojure/clojure "1.12.0"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "1.5.0"]]
                   :source-paths ["dev"]}
             :uberjar {:aot :all}}
  :aliases {"ci"      ["test"]}
  :repl-options {:init-ns user})
