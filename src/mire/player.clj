(ns mire.player)

(def ^:dynamic *current-room*)
(def ^:dynamic *inventory*)
(def ^:dynamic *name*)
(def ^:dynamic *description*)
(def ^:dynamic *strength*) ;;uee
(def ^:dynamic *intelligence*) ;;ueeee
(def ^:dynamic *perception*) ;;ueeeee

(def prompt "> ")
(def streams (ref {}))

(defn carrying? [thing]
  (some #{(keyword thing)} @*inventory*))
