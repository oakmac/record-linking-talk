(ns com.oakmac.conj2019.record-linking-talk.core
  (:require
    [clj-fuzzy.phonetics :refer [double-metaphone]]
    [clojure.string :as str]))

(def clarice1
  {:address {:street nil
             :zip nil}
   :dob ""
   :fname "Clarice"
   :lname "Lang"
   :medicalRecordNumber nil
   :ssn "000000000"
   :visitNumber nil})

(def katie1
  {:address {:street "9993 Lola Freeway"
             :zip "73924"}
   :dob "1970-10-04"
   :fname "Katy"
   :lname "Framingham"
   :medicalRecordNumber "78831"
   :ssn "710359155"
   :visitNumber "442878"})

(def katie2
  {:dob "1970-10-04"
   :fname "Katie"
   :lname "Framing"})

;; -----------------------------------------------------------------------------
;; Probabilistic Approach

(defn- default-match-fn
  "Default function for determining if two values are a match."
  [a b]
  (cond
    (and (string? a) (string? b))
    (= (str/lower-case a) (str/lower-case b))

    :else
    (= a b)))

(defn- names-match?
  "Do name-a or name-b share any matching phoenitic values via double-metaphone?"
  [name-a name-b]
  (let [[a1 a2] (double-metaphone name-a)
        [b1 b2] (double-metaphone name-b)]
    (or (= a1 b1)
        (= a1 b2)
        (= a2 b1)
        (= a2 b2))))

(def identifier-fields
  "List of patient identifier fields that we want to compare patients with."
  [{:key :address
    :match-prob 0.90
    :unmatch-prob 0.10}
   {:key :dob
    :match-prob 0.95
    ;; NOTE: we could be more precise here by calculating patient age
    ;; not super important though
    :unmatch-prob 0.01}
   {:key :fname
    :match-fn names-match?
    :match-prob 0.60
    :unmatch-prob 0.20}
   {:key :lname
    :match-fn names-match?
    :match-prob 0.90
    :unmatch-prob 0.10}
   {:key :medicalRecordNumber
    :match-prob 0.95
    :unmatch-prob 0.05}
   {:key :ssn
    :match-prob 0.98
    :unmatch-prob 0.01}
   {:key :visitNumber
    :match-prob 0.95
    :unmatch-prob 0.05}])

(defn compare-patients
  "Compare two patients and return a match score"
  [patientA patientB]
  (reduce
    (fn [weight {:keys [key match-fn match-prob unmatch-prob]}]
      (let [fieldA-value (get patientA key)
            fieldB-value (get patientB key)]
        ;; skip weight calculation if either field value is nil
        (if (or (nil? fieldA-value) (nil? fieldB-value))
          weight
          ;; else calculate weight score using the Fellegi-Sunter method
          ;; http://www.bristol.ac.uk/media-library/sites/cmm/migrated/documents/problinkage.pdf
          (let [match-fn2 (if (fn? match-fn) match-fn default-match-fn)
                is-a-match? (match-fn2 fieldA-value fieldB-value)]
            (if is-a-match?
              (+ weight (Math/log (/ match-prob unmatch-prob)))
              (+ weight (Math/log (/ (- 1 match-prob) (- 1 unmatch-prob)))))))))
    0
    identifier-fields))
