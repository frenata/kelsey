(ns kelsey.core
  (:require [kelsey.generate :as gen])
  (:import (net.frenata.ArrayInit ArrayInitBaseListener)
           (org.antlr.v4.runtime.tree ParseTreeWalker)))

(def arrayInitParser (gen/parser :net.frenata.ArrayInit.ArrayInit))

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
