(ns kelsey.generate
  (:import (org.antlr.v4.runtime ANTLRInputStream
                                 CommonTokenStream)))

(defmacro parser
  "Generates a parsing pipeline from a grammar"
  [grammar]
  {:pre [(or (string? grammar)
             (symbol? grammar)
             (keyword? grammar))]}

  (let [grammar (cond-> grammar keyword? name)
        lexer (symbol (str grammar "Lexer"))
        parser (symbol (str grammar "Parser"))]

    `#(->> %
           (new ANTLRInputStream)
           (new ~lexer)
           (new CommonTokenStream)
           (new ~parser))))

(defmacro visit
  "Visit part of the parse tree.
  Expects to be defined when `ctx` is in scope as the current context."
  [& subtree]
  `(.visit ~'this (-> ~'ctx (~@subtree))))
