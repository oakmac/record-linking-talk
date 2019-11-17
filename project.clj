(defproject record-linking-talk "1.0.0"

  :description "This is code for the talk 'Probabilistic Record Linkage of Hospital Patients' by Chris Oakman at Clojure/conj 2019"
  :url "https://github.com/oakmac/record-linking-talk"
  :author "Chris Oakman <chris@oakmac.com>"

  :license {:name "ISC"
            :url "https://github.com/oakmac/record-linking-talk/blob/master/LICENSE.md"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [clj-fuzzy "0.4.1"]]

  :repl-options {:init-ns com.oakmac.conj2019.record-linking-talk.core})
