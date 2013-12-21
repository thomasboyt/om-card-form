(ns card.util)

(defn split-digits [number]
  (let [str-number (.toString js/number)]
    (map js/parseInt (.split js/str-number ""))))

(defn luhn10
  "Returns true for a valid luhn10 card and false for an invalid one.

   number - an int representing a credit card number"
  [number]
  (let [digits (split-digits number)]
    (== 0 (-> (reduce + (map-indexed (fn [idx x]
                          (let [doubled (cond
                                          (== (mod idx 2) 1) (* 2 x)  ; Double every other digit
                                          :else x)]
                            (cond
                              (>= doubled 10) (reduce + (split-digits doubled))  ; Sum the digits in numbers over 10
                              :else doubled)))
                        (reverse digits)))
              (mod 10)))))

(defn card-type
  "Returns the type of a card given a naive regex match.

   number - a credit card number, as an int"
  [number]
  (let [types {:visa #"^4"
               :mastercard #"^(51|52|53|54|55)"
               :amex #"^(34|37)"
               :discover #"^(6011|65)"}
        str-number (.toString js/number)]
    (first (first (filter (fn [[card-type regex]]
                     (re-seq regex str-number))
                   types)))))
