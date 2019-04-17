(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.10.0"]
                  [adzerk/boot-reload "0.6.0"]])

(task-options!
  pom {:project 'elwood
       :version "0.1.0"
       :description ""}
  jar {:main 'elwood.core}
  repl {:int-ns 'elwood.core
        :eval '(set! *print-length* 20)})

(deftask build
  []
  (comp (pom)
        (jar)
        (install)))

(require '[elwood.core :as elwood]
         '[adzerk.boot-reload :refer [reload]])

(deftask run []
  (fn [next-handler]
    (fn [fileset]
      (require '[elwood.core :as elwood])
      (println (elwood/-main))
      (next-handler fileset))))

(deftask rrun []
  (comp
    (reload)
    (run)
    (repl)))

(deftask dev []
  (repl))

(deftask start []
  (fn [next-handler]
    (fn [fileset]
      (next-handler fileset))))

(deftask stop []
  (fn [next-handler]
    (fn [fileset]
      (next-handler fileset))))

(deftask connect []
  (fn [next-handler]
    (fn [fileset]
      (next-handler fileset))))
