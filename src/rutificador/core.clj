(ns rutificador.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))

(defn char->int
  "Convierte un char en un numero segun su representacion en base 10"
  [c]
  (Integer. (str c)))

(defn int->char
  "Convierte un entero a su representacion en base 10."
  [i]
  (-> (Integer/toString i)
      seq
      first))

(defn get-ver
  "convierte el valor de i en un digito verificador"
  [i]
  (cond (= i 11) \0
        (= i 10) \k
        :else (int->char i)))

(defn rut->seq
  "Transforma un string de rut (sin puntos ni guion) a una sequencia de enteros"
  [rut]
  (->> rut
       seq
       reverse
       next
       (map char->int)
       (into [])))

(defn rut-valid?
  "Valida un rut (string). Sin puntos ni guion"
  [rut]
  (let [nums (cycle [2 3 4 5 6 7])
        rut-numbers (rut->seq rut)
        verif (last (seq rut))]
    (->> (map vector rut-numbers nums)
         (map #(apply * %))
         (reduce +)
         ((fn [x] (mod x 11)))
         (- 11)
         (get-ver)
         (= verif))))

(s/def ::first-digit #{\1 \2 \3 \4 \5 \6 \7 \8 \9})
(s/def ::digit #{\1 \2 \3 \4 \5 \6 \7 \8 \9 \0})
(s/def ::rut-seq (s/cat :first-digit ::first-digit
                        :middle-digit (s/+ ::digit)
                        :last-digit (s/? #{\k})))

(s/def ::rut (s/and string? rut-valid?))

(def rut-gen
  (gen/such-that (comp rut-valid? #(apply str %))
                 (s/gen ::rut-seq)
                 100))

(defn generate-rut
  "Genera un rut valido"
  []
  (apply str (gen/generate rut-gen)))

(defn generate-ruts
  "Genera multiples ruts validos"
  [n]
  (->> (gen/sample rut-gen n)
       (map #(apply str %))))

(defn beautify-rut
  "20345678k -> 20.345.678-k"
  [rut]
  (let [ver (last (seq rut))]
    (->> (seq rut)
        reverse
        next
        (partition-all 3)
        (interpose \.)
        flatten
        reverse
        doall
        (apply str)
        ((fn [x] (str x "-" ver))))))
