(defproject rutificador "0.1.0-SNAPSHOT"
  :description "Script en clojure que verifica y genera ruts chilenos "
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/test.check "0.9.0"]
                 [ring "1.7.0-RC1"]
                 [compojure "1.6.1"]
                 [hiccup "2.0.0-alpha1"]]
  :min-lein-version "2.0.0"
  :uberjar-name "rutificador.jar"
  :profiles {:dev {:main rutificador.web/-dev-main}})
