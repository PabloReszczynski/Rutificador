(ns rutificador.web
  (:require [rutificador.core :refer [generate-ruts beautify-rut]]
            [rutificador.main-page :refer [ruts-list]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [hiccup.core :refer [html]]))

(defn welcome [_]
  (let [ruts (map beautify-rut (generate-ruts 10))]
    (html (ruts-list ruts))))

(def --404 "<h1>404</h1>")

(defroutes app
  (GET "/" [] welcome)
  (not-found --404))

(defn -main
  [port]
  (run-jetty app {:port (Integer. port)}))

(defn -dev-main
  [port]
  (run-jetty (wrap-reload #'app) {:port (Integer. port)}))
