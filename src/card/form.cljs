(ns card.form
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [clojure.string :as string]
            [card.util :as util]))

(enable-console-print!)

; (prn (util/luhn10 79927398713))
; (prn (util/card-type 4111111111111111))
; (prn (util/card-type 1))

(defn pretty-number [number]
  (let [card-type (util/card-type number)]
    (cond
      (some #{card-type} '(:visa :mastercard :discover))
        (apply str (flatten (interpose " " (partition-all 4 number))))
      ; TODO: amex (space after 4 and 10)
      (= card-type :amex) ())))

(defn unpretty-number [number]
  (string/replace number #"\s" ""))

(defn handle-change [e card]
  (let [number (unpretty-number (.. e -target -value))  ;; let number = e.target.value
        show-valid (>= (count number) 16)
        is-valid (util/luhn10 number)
        cursor-pos (.. e -target -selectionStart)]
    (om/update! card #(assoc % :number number
                               :is-valid is-valid
                               :show-valid show-valid
                               :cursor-pos cursor-pos))))

(defn widget [{:keys [number cvv expiration is-valid show-valid cursor-pos] :as card}]
  (reify
    om/IDidUpdate
    (did-update [_ owner _ _ _]
      (let [input-node (om/get-node owner "card-input")]
        (.setSelectionRange input-node cursor-pos cursor-pos)))
    om/IRender
    (render [_ _]
      (let [input-classes (cond-> []
                            (and is-valid show-valid) (conj "valid")
                            (and (not is-valid) show-valid) (conj "invalid"))]
        (dom/div nil
          (dom/input #js {:value (pretty-number number)
                          :ref "card-input"
                          ;; :onKeyDown #(handle-key-down % card)
                          :onChange #(handle-change % card)
                          :className (string/join " " input-classes)}))))))
