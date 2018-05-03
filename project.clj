(defproject kelsey "0.1.0-SNAPSHOT"
  :description "Test bed for playing with Antlr grammars."
  :url "https://github.com/frenata/kelsey"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.antlr/antlr4 "4.5"]]
  :main kelsey.core

  :source-paths ["src/clj"]
  :java-source-paths ["src/java"] ;; generated sources

  :plugins [[lein-antlr "0.3.0"]]
  :hooks [leiningen.antlr]
  :prep-tasks [["antlr"] "javac" "compile"]
  :antlr-src-dir "src/antlr"
  :antlr-dest-dir "src/java/net/frenata/kelsey"
  :antlr-options {:package "net.frenata.kelsey"}


  :profiles {:dev {:jvm-opts ["-Xmx1g"]
                   :source-paths ["dev"]
                   :dependencies [[org.clojure/test.check "0.9.0"]]}}

  :aliases {"tree" ["run" "-m" "kelsey.core/tree"]
            "sum"  ["run" "-m" "kelsey.core/sum"]})
