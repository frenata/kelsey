(ns kelsey.core
  (:require [kelsey.generate :as gen]
            [clojure.java.io :as io])
  (:import (net.frenata.kelsey ArrayInitBaseListener
                               PropBaseListener)
           (org.antlr.v4.runtime.tree ParseTreeWalker)))

(def arrayInitParser (gen/parser :net.frenata.kelsey.ArrayInit))

(def ^:private slurrp (comp slurp io/resource))

(defn- stripQuotes [s]
  (let [[f l] ((juxt first last) s)]
    (if (= f l \")
      (subs s 1 (dec (count s)))
      s)))

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

(defn collect-props [props]
  (proxy [PropBaseListener] []
    (exitProp [ctx]
      (let [id (-> ctx .ID .getText keyword)
            val (-> ctx .STRING .getText stripQuotes)]
        (swap! props #(assoc % id val))))))

(defn return-props [props]
  (proxy [PropBaseVisitor] []
    (visitProp [ctx]
      (let [id (-> ctx .ID .getText keyword)
            val (-> ctx .STRING .getText stripQuotes)]
        {id val}))))

(defn prop [input]
  (let [parser ((gen/parser :net.frenata.kelsey.Prop) input)
        tree (.file parser)
        props (atom {})
        walker (ParseTreeWalker.)]

    (.walk walker (collect-props props) tree)
    @props))
