(ns card
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [card.form :as card-form]
            [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(def state (atom {:card {:number "" :is-valid false :cursor-pos 0}}))

(defn app [data]
  (om/component
    (om/build card-form/widget data {:path [:card]})))

(om/root state app js/document.body)
