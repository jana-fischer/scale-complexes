;; Copyright ⓒ the conexp-clj developers; all rights reserved.
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file LICENSE at the root of this distribution.
;; By using this software in any fashion, you are agreeing to be bound by
;; the terms of this license.
;; You must not remove this notice, or any other, from this software.

(ns scale-complexes.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [conexp.io.contexts :refer :all]
            [conexp.fca.ordinal-motifs :refer :all]
            [clojure.java.io :as io]
            [clojure.data.json :as json])
  (:gen-class))

(def cli-options
  [["-l" "--load FILE" "Load file path (for context file in burmeister format)."
    :validate [#(.exists (io/file %)) "The file does not exists."]]
   ["-s" "--save FOLDER" "Save folder path (for resulting scale-complex files)."]
   ["-h" "--help"]])

(defn write-complex
  [[file complex]]
  (with-open [w (clojure.java.io/writer file)]
    (.write w (json/write-str complex))))

(defn -main
  [& args]
  (let [{:keys [options arguments summary errors]}
        (parse-opts args cli-options)]
    
    (when errors
      (doseq [error errors]
        (println error))
      (System/exit 1))

    (cond 
      (contains? options :help)
      (println summary)
      
      :else
      (do 
        (assert (:load options) "Input file (-l, --load) must be specified.")
        (let [ctx (read-context (:load options) :burmeister)
              complex (make-scale-complex ctx)
              scale-types [:nominal :ordinal :interordinal :contranominal :crown]
              complexes (map #(get-complex complex %) scale-types)]
          
          (assert (:save options) "Output file (-s, --save) must be specified.")
          (if-not (.exists (io/file (:save options)))
            (.mkdir (java.io.File. (:save options))))
          (let [files (map #(str (:save options) "/" (name %) ".json") scale-types)
                file-complex-zips (map vector files complexes)]
            (doseq [fc file-complex-zips]
              (write-complex fc)))))))
  
  (System/exit 0))
