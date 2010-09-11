(ns rql.core)

(defn where
  "Take a collection of records, and filter on preds"
  [coll & preds]
    (reduce (fn [coll pred] (filter #(= ((first pred) %) (last pred)) coll)) coll (partition 2 preds)))

(defn where!
  "This version of where expects a ref as its first argument. And sets the ref to
  be the value of where."
  [coll & preds]
  (dosync
    (ref-set coll (where @coll preds))))

(defn delete
  "Take a collection of records, and delete records based on preds."
  [coll & preds]
  (reduce (fn [coll pred] (filter #(not= ((first pred) %) (last pred)) coll)) coll (partition 2 preds)))

(defn delete!
  "This version of delete expects a ref as its first argument. And sets the ref to
  be the value of delete."
  [coll & preds]
  (dosync
    (ref-set coll (delete @coll preds))))

(defn update
  [coll key-values & preds]
  "Take a collection of records, and update keys with values, when all preds match.
   If no preds are given every element of the collection gets updated."
  (let [preds (partition 2 preds)]
    (for [c coll] (if (every? #(= ((first %) c) (last %)) preds)
                    (merge c key-values)
                    c))))

(defn update!
  [coll key-values & preds]
  "This version of update expects a ref as its first argument. And sets the ref to
  be the value of update."
  (dosync
    (ref-set coll (update @coll key-values preds))))
    
(defn insert
  "Insert an instance in a collection of records."
  [coll & instances]
  (flatten (list coll instances)))
  
(defn insert!
  "This version of insert expects a ref as its first argument. And sets the ref to
  be the value of insert."
  [coll & instances]
  (dosync
    (ref-set coll (insert @coll instances)))) 

(defn sort-on
  "Take a collection of records, and sort based on the predicate."
  ([coll ky]
    (sort #(compare (ky %1) (ky %2)) coll))
  ([coll operator ky]
    (sort #(operator (ky %1) (ky %2)) coll)))  

(defn sort-on!
  "This version of sort-on expects a ref as its first argument. And sets the ref to
  be the value of sort-on."
  ([coll ky]
    (dosync
      (ref-set coll (sort-on @coll ky))))
  ([coll operator ky]
    (dosync
      (ref-set coll (sort-on @coll operator ky)))))
      
(defn group
  "Takes a collection and creates a partitioned list based on the ky."
  [coll ky]
  (loop [coll coll result ()]
    (if (empty? coll)
      result
      (recur (delete coll ky (ky (first coll))) (cons (where coll ky (ky (first coll))) result)))))           