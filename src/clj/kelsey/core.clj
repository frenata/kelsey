(ns kelsey.core
  (:require [kelsey.generate :as gen])
  (:import (net.frenata.kelsey ArrayInitBaseListener
                               )
           (org.antlr.v4.runtime.tree ParseTreeWalker)))

(def arrayInitParser (gen/parser :net.frenata.kelsey.ArrayInit))

(defn tree [input]
  (let [parser (arrayInitParser input)
        tree (.init parser)]

    (println (.toStringTree tree parser))))

(defn short->unicode [mem]
  (proxy [ArrayInitBaseListener] []
    #_(enterInit [ctx]
      (print \"))
    #_(exitInit [ctx]
      (print \"))
    (enterValue [ctx]
      (let [val (-> ctx
                    (.INT)
                    (.getText)
                    (Integer/valueOf))]
        (swap! mem (partial + val))))))

(defn sum [input]
  (let [parser (arrayInitParser input)
        tree (.init parser)
        mem (atom 0)
        walker (ParseTreeWalker.)]

    (.walk walker (short->unicode mem) tree)
    (println @mem)))

(defn csv [input]
  (let [parser ((gen/parser :net.frenata.kelsey.CSV) input)
        tree (.file parser)]
    (println (.toStringTree tree parser))))

(defn json [input]
  (let [parser ((gen/parser :net.frenata.kelsey.JSON) input)
        tree (.json parser)]
    (println (.toStringTree tree parser))))

(defn dot [input]
  (let [parser ((gen/parser :net.frenata.kelsey.Dot) input)
        tree (.graph parser)]
    (println (.toStringTree tree parser))))
