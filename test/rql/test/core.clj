(ns rql.test.core
  (:use [rql.core] :reload)
  (:use [clojure.test]))

(defrecord Person [first-name last-name age])

(def maarten (Person. "Maarten" "Hus" 21))
(def danny   (Person. "Danny" "Kieanu" 21))
(def cornel  (Person. "Cornel" "Berberus" 22))
(def ronald  (Person. "Ronald" "Chocolate" 19))

(def persons [maarten danny cornel ronald])

(deftest where-test
  (is (instance? rql.test.core.Person (first (where persons :age 21))))
  (is (= 2 (count (where persons :age 21))))
  (is (= "Maarten" (:first-name (first (where persons :age 21 :first-name "Maarten"))))))
  
(deftest delete-test
  (is (instance? rql.test.core.Person (first (delete persons :first-name "Maarten" :last-name "Kieanu"))))
  (is (= 2 (count (delete persons :first-name "Maarten" :last-name "Kieanu"))))
  (is (= "Cornel" (:first-name (first (delete persons :first-name "Maarten" :last-name "Kieanu")))))) 

(deftest update-test
  (is (instance? rql.test.core.Person (first (update persons {:age 10}))))
  (is (= 4 (count (update persons {:age 10}))))
  (is (= 10 (:age (first (update persons {:age 10} :first-name "Maarten"))))))
  
(deftest insert-test
  (is (instance? rql.test.core.Person (last (insert persons (Person. "Tom" "Sawyer" 35)))))
  (is (= 5 (count (insert persons (Person. "Tom" "Sawyer" 35)))))
  (is (= 6 (count (insert persons (Person. "Tom" "Sawyer" 35) (Person. "Gabriel" "Lincon" 45)))))
  (is (= 35 (:age (last (insert persons (Person. "Tom" "Sawyer" 35)))))))

(deftest sort-on-test
  (is (instance? rql.test.core.Person (first (sort-on persons :first-name))))
  (is (= "Cornel" (:first-name (first (sort-on persons :first-name )))))
  (is (= 19 (:age (first (sort-on persons < :age)))))
  (is (= 22 (:age (first (sort-on persons > :age))))))
  
(deftest group-test
   (is (instance? rql.test.core.Person (first (first (group persons :age)))))
   (is (= 3 (count (group persons :age))))
   (is (= 1 (count (first (group persons :age)))))
   (is (= 2 (count (last (group persons :age)))))) 
      