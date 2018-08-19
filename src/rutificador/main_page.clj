(ns rutificador.main-page
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]))

(defn ruts-list
  [ruts]
  [:div
   [:ul (map (fn [x] [:li x]) ruts)]])
