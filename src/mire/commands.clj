(ns mire.commands
  (:require [clojure.string :as str]
            [mire.rooms :as rooms]
            [mire.player :as player]))

(defn- move-between-refs
  "Move one instance of obj between from and to. Must call in a transaction."
  [obj from to]
  (alter from disj obj)
  (alter to conj obj))

;; Command functions

(defn stats
  "See your stats"
  []
  (str "\nStrength: " player/*strength*
  (str "\nIntelligence: " player/*intelligence*
  (str "\nPerception: " player/*perception*)))) ;;ueeeeeeee

(defn look
  "Get a description of the surrounding environs and its contents, including player names."
  []
  (let [current-room @player/*current-room*
        room-desc (:desc current-room)
        exits (keys @(:exits current-room))
        items (map #(str "There is " % " here.\n") @(:items current-room))
        inhabitants @(:inhabitants current-room)
        players-in-room (filter #(contains? inhabitants %) (keys @player/streams))]
        (str room-desc
         "\nExits: " exits "\n"
         (str/join "\n" items)
         (when-not (empty? players-in-room)
           (str "\nPlayers here: " (str/join ", " players-in-room))))))

(defn move
  "\"♬ We gotta get out of this place... ♪\" Give a direction."
  [direction]
  (dosync
   (let [target-name ((:exits @player/*current-room*) (keyword direction))
         target (@rooms/rooms target-name)]
     (if target
       (do
         (move-between-refs player/*name*
                            (:inhabitants @player/*current-room*)
                            (:inhabitants target))
         (ref-set player/*current-room* target)
         (look))
       "You can't go that way."))))

(defn grab
  "Pick something up."
  [thing]
  (dosync
   (if (rooms/room-contains? @player/*current-room* thing)
     (do (move-between-refs (keyword thing)
                            (:items @player/*current-room*)
                            player/*inventory*)
         (str "You picked up the " thing "."))
     (str "There isn't any " thing " here."))))

(defn discard
  "Put something down that you're carrying."
  [thing]
  (dosync
   (if (player/carrying? thing)
     (do (move-between-refs (keyword thing)
                            player/*inventory*
                            (:items @player/*current-room*))
         (str "You dropped the " thing "."))
     (str "You're not carrying a " thing "."))))

(defn inventory
  "See what you've got."
  []
  (str "You are carrying:\n"
       (str/join "\n" (seq @player/*inventory*))))

(defn detect
  "If you have the detector, you can see which room an item is in."
  [item]
  (if (@player/*inventory* :detector)
    (if-let [room (first (filter #((:items %) (keyword item))
                                 (vals @rooms/rooms)))]
      (str item " is in " (:name room))
      (str item " is not in any room."))
    "You need to be carrying the detector for that."))

(defn say
  "Say something out loud so everyone in the room can hear."
  [& words]
  (let [message (str/join " " words)]
    (doseq [inhabitant (disj @(:inhabitants @player/*current-room*) player/*name*)]
      (binding [*out* (player/streams inhabitant)]
        (println (str player/*name* ":") message)
        (println player/prompt)))
    (str "You said " message)))

(defn yell
  "Say something out loud so everyone in the dungeon can hear."
  [& words]
  (let [message (str/join " " words)]
    (doseq [inhabitant @player/streams]
      (if (false? (= (first inhabitant) player/*name*))
        (binding [*out* (player/streams (first inhabitant))]
          (println (str player/*name* ":") message)
          (println player/prompt))))
    (str "You yelled " message)))

(defn whisper
  "Say something very quiet so only target person in the room can hear."
  [& words]
  (let [player-target (str (first words)) message (str/join " " (rest words))]
    (doseq [inhabitant (disj @(:inhabitants @player/*current-room*) player/*name*)]
      (let [inhabitant-name inhabitant] ;(subs inhabitant 1 (count inhabitant))
        (if (true? (= player-target inhabitant-name))
          (binding [*out* (player/streams inhabitant)]
            (println (str player/*name* "->" player-target ":") message)
            (println player/prompt)))))
    (str "You whispered to " player-target " " message)))

(defn help
  "Show available commands and what they do."
  []
  (str/join "\n" (map #(str (key %) ": " (:doc (meta (val %))))
                      (dissoc (ns-publics 'mire.commands)
                              'execute 'commands))))

;; Command data

(def commands {"move" move,
               "north" (fn [] (move :north)),
               "south" (fn [] (move :south)),
               "east" (fn [] (move :east)),
               "west" (fn [] (move :west)),
               "grab" grab
               "discard" discard
               "inventory" inventory
               "detect" detect
               "look" look
               "say" say
               "stats" stats
               "yell" yell
               "help" help
               "whisper" whisper})

;; Command handling

(defn execute
  "Execute a command that is passed to us."
  [input]
  (try (let [[command & args] (.split input " +")]
         (apply (commands command) args))
       (catch Exception e
         (.printStackTrace e (new java.io.PrintWriter *err*))
         "You can't do that!")))
