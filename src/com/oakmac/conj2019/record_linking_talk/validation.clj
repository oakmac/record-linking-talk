(ns com.oakmac.conj2019.record-linking-talk.validation
  (:require
    [clojure.string :as str]))

(defn all-digits?
  "Is s a string made up of all digit characters? ie: 0-9"
  [s]
  (re-matches #"\d+" s))

(assert (all-digits? "1"))
(assert (all-digits? "111"))
(assert (all-digits? "12345"))
(assert (not (all-digits? "")))
(assert (not (all-digits? "z")))
(assert (not (all-digits? "123a")))

(defn- all-the-same-chars?
  "Does s contain all of the same characters?"
  [s]
  (let [first-char (first s)]
    (every? #(= first-char %) s)))

(assert (all-the-same-chars? "z"))
(assert (all-the-same-chars? "111"))
(assert (all-the-same-chars? "aaaaaaaaaa"))
(assert (not (all-the-same-chars? "a1")))
(assert (not (all-the-same-chars? "ZZZZZZZZZZZZz")))

(defn- all-zeroes?
  "Is s all zero characters?"
  [s]
  (every? #(= \0 %) s))

(assert (all-zeroes? "0"))
(assert (all-zeroes? "00000000000"))
(assert (not (all-zeroes? "123")))
(assert (not (all-zeroes? "xyz")))
(assert (not (all-zeroes? "0000000g00000")))

(defn- ssn-has-zero-groups?
  "Does this SSN have all zeroes in a single group?"
  [ssn]
  (or (= "000" (subs ssn 0 3))
      (= "00" (subs ssn 3 5))
      (= "0000" (subs ssn 5 9))))

(def ^:private invalid-ssns
  #{"078051120"   ;; Woolworth's Wallet Fiasco
    "219099999"}) ;; Social Security Administration Advertisement

(defn valid-ssn? [ssn]
  (and (string? ssn)
       (= 9 (count ssn))
       (all-digits? ssn)
       (not (ssn-has-zero-groups? ssn))
       (not (contains? invalid-ssns ssn))
       (not (all-the-same-chars? ssn))
       (not (str/starts-with? ssn "666"))
       (not (str/starts-with? ssn "9"))))

(assert (not (valid-ssn? "000000000")))
(assert (not (valid-ssn? "000224444")))
(assert (not (valid-ssn? "444004444")))
(assert (not (valid-ssn? "444990000")))
(assert (not (valid-ssn? "078051120")))
(assert (not (valid-ssn? "555555555")))

(defn looks-like-a-valid-id? [id]
  (and (string? id)
       (all-digits? id)
       (> (count id) 4)
       (not (all-zeroes? id))))

(assert (looks-like-a-valid-id? "825889"))
(assert (not (looks-like-a-valid-id? "1")))
(assert (not (looks-like-a-valid-id? "000000000")))
