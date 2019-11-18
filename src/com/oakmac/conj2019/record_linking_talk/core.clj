(ns com.oakmac.conj2019.record-linking-talk.core
  (:require
    [clj-fuzzy.phonetics :refer [double-metaphone]]
    [clojure.string :as str]
    [com.oakmac.conj2019.record-linking-talk.validation :refer [looks-like-a-valid-id? valid-ssn?]]))

;; -----------------------------------------------------------------------------
;; Some Fake Patient Data

(def katy1
  {:address {:street "9993 Lola Freeway"
             :zip "73924"}
   :dob "1970-10-04"
   :fname "Katy"
   :lname "Framingham"
   :medicalRecordNumber "78831"
   :ssn "710359155"
   :visitNumber "442878"})

(def katy2
  {:address nil
   :dob "1970-10-04"
   :fname "Katie"
   :lname "Framing"
   :medicalRecordNumber nil
   :ssn nil
   :visitNumber nil})

;; ~~~~~~~~~~~~

(def brian
  {:address {:street "4761 Tad Glens"
             :zip "49867"}
   :dob "2017-11-24"
   :fname "Brian"
   :lname "Lang"
   :medicalRecordNumber "78831"
   :ssn "467813477"
   :visitNumber "825889"})

(def julian
  {:address {:street "4761 Tad Glens"
             :zip "49867"}
   :dob "2017-11-24"
   :fname "Julian"
   :lname "Lang"
   :medicalRecordNumber "78832"
   :ssn "681812714"
   :visitNumber nil})

;; ~~~~~~~~~~~~

(def ethan
  {:address {:street "4729 Esteban Hills"
             :zip "59030"}
   :dob "1965-02-06"
   :fname "Ethan"
   :lname "Kessler"
   :medicalRecordNumber "34785"
   :ssn "335345129"
   :visitNumber "761325"})

(def lukas1
  {:address {:street "1066 Kling Skyway"
             :zip "82403"}
   :dob "1992-09-25"
   :fname "Lukas"
   :lname "Fisher"
   :medicalRecordNumber "34788"
   :ssn "335345129"
   :visitNumber "761329"})

(def lukas2
  {:address {:street "1066 Kling Skyway"
             :zip "82403"}
   :dob "1992-09-25"
   :fname "Lukas"
   :lname "Fisher"
   :medicalRecordNumber "34788"
   :ssn "283737684"
   :visitNumber "761329"})

;; -----------------------------------------------------------------------------
;; Deterministic Approach

(defn patient-ids-match?
  "Compares IDs to see if two patients are the same person."
  [patient-a patient-b]
  (let [ssn-a (:ssn patient-a)
        ssn-b (:ssn patient-b)
        vn-a (:visitNumber patient-a)
        vn-b (:visitNumber patient-b)
        mrn-a (:medicalRecordNumber patient-a)
        mrn-b (:medicalRecordNumber patient-b)]
    (cond
      ;; Both patients have a matching SSN
      (and (valid-ssn? ssn-a) (valid-ssn? ssn-b)
           (= ssn-a ssn-b))
      true

      ;; Both patients have a matching Visit Number
      (and (looks-like-a-valid-id? vn-a) (looks-like-a-valid-id? vn-b)
           (= vn-a vn-b))
      true

      ;; Both patients have a matching Medical Record Number
      (and (looks-like-a-valid-id? mrn-a) (looks-like-a-valid-id? mrn-b)
           (= mrn-a mrn-b))
      true

      :else false)))



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
  "Patient identifier fields that we want to use when comparing patients using
   the Fellegi-Sunter method."
  [{:key :address
    :match-prob 0.90
    :unmatch-prob 0.10}
   {:key :dob
    :match-prob 0.95
    ;; NOTE: this is much higher than the real odds of a random dob match
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
          (let [match-fn2 (if (fn? match-fn) match-fn default-match-fn)
                is-a-match? (match-fn2 fieldA-value fieldB-value)]
            (if is-a-match?
              (+ weight (Math/log (/ match-prob unmatch-prob)))
              (+ weight (Math/log (/ (- 1 match-prob) (- 1 unmatch-prob)))))))))
    0
    identifier-fields))
