(defproject record-linking-talk "1.0.0"

  :description "This is code for the talk 'Probabilistic Record Linkage of Hospital Patients' by Chris Oakman at Clojure/conj 2019"
  :url "https://github.com/oakmac/record-linking-talk"
  :author "Chris Oakman <chris@oakmac.com>"

  :license {:name "ISC"
            :url "https://github.com/oakmac/record-linking-talk/blob/master/LICENSE.md"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.postgresql/postgresql "42.1.4"]
                 [com.fzakaria/slf4j-timbre "0.3.14"]
                 [com.taoensso/timbre "4.10.0"]
                 [migratus "1.2.7"]]

  :plugins [[migratus-lein "0.7.2"]]

  ;; NOTE: please create a profiles.clj file based on example.profiles.clj file
  ;;       in order to fill in database information
  :migratus {}

  :repl-options {:init-ns com.oakmac.conj2019.record-linking-talk.core})
