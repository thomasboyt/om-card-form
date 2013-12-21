(defproject card "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2127"]
                 [om "0.1.0-SNAPSHOT"]]

  :source-paths ["src"]

  :plugins [[lein-cljsbuild "1.0.1"]]

  :cljsbuild {
    :builds [{:id "card"
              :source-paths ["src"]
              :compiler {
                :output-to "card.js"
                :output-dir "out"
                :optimizations :none
                :source-map true
                :preamble ["om/react.js"]
                :externs ["om/externs/react.js"]}}]})
