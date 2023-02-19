(ns user
  (:require
   [clojure.pprint :as pprint]
   [clojure.test.check.generators :as gen]
   [clojure.test.check.properties :as prop]
   [clojure.test.check :refer [quick-check]]
   [clojure.string :as str]))

(defn indexof
  "given two strings, returns the index where sb begins in sa. Nil if not found"
  ([sa sb]
   (indexof sa sb 0))
  ([sa sb i]
   (let [sa-len (count sa)
         sb-len (count sb)]
     (cond
       (>= 0 sa-len) nil
       (>= 0 sb-len) nil
       ; this is hit when element not found
       (> (+ i sb-len) sa-len) nil
       (= (subs sa i (+ i sb-len)) sb) i
       :else (indexof sa sb (inc i))))))

(defn gen-alphanumeric   "A generator that accepts a minimum and maximum string length" [min max]
    (gen/fmap str/join (gen/vector gen/char-alphanumeric min max)))

(def indexof-functionality-test
  (prop/for-all [s1 (gen-alphanumeric 100 200)
                 begin gen/nat 80]
    (let [end (+ begin 10)
          s2 (subs s1 begin end)]
      (= (str/index-of s1 s2) (indexof s1 s2)))))


(pprint/pprint
 (quick-check 100 indexof-functionality-test
              :seed 1676231835751))
