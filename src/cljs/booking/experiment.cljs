(ns booking.experiment)

(do
  (defprotocol Someprot
    (init [_ _])
    (draw [])
    (alter [field]))

  (defrecord Component [m]
    Someprot
    (init [_ _])
    (draw [])
    (alter [field] (prn 'altering field)))

  (let [s (->Somerec {})]
    (init s 2)))