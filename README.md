# What is rql

rql is a bunch of functions for working with a collection of records.

# Usage

Given the following definitions

	(defrecord Person [first-name last-name age])
	
	(def maarten (Person. "Maarten" "Hus" 21))
	(def danny   (Person. "Danny" "Kieanu" 21))
	(def cornel  (Person. "Cornel" "Berberus" 21))
	(def ronald  (Person. "Ronald" "Chocolate" 21))

	(def persons [maarten danny cornel ronald])
	

## Where: filtering records based on predicates.	
	
	(where persons :age 21)  

Results in:
	
	(#Person{:first-name "Maarten", :last-name "Hus", :age 21} #Person{:first-name "Danny", :last-name "Kieanu", :age 21} 
	 #Person{:first-name "Cornel", :last-name "Berberus", :age 21} #Person{:first-name "Ronald", :last-name "Chocolate", :age 21})

	(where persons :first-name "Maarten")

Results in:
	
	(#Person{:first-name "Maarten", :last-name "Hus", :age 21})

## Delete: removing records based on predicates.

 	(delete persons :age 21)

Results in:

	()
	
	(delete persons :last-name "Kieanu")

Results in:
	(#Person{:first-name "Maarten", :last-name "Hus", :age 21} #Person{:first-name "Cornel", :last-name "Berberus", :age 21} 
	 #Person{:first-name "Ronald",:last-name "Chocolate", :age 21})
	
## Insert: adding to a collection of records

	(insert persons (Person. "Tom" "Sawyer" 35) (Person. "Adam" "Sawyer" 35))

Results in:
	
	(#Person{:first-name "Maarten", :last-name "Hus", :age 21} #Person{:first-name "Danny", :last-name "Kieanu", :age 21} 
	#Person{:first-name "Cornel", :last-name "Berberus", :age 21} #Person{:first-name "Ronald", :last-name "Chocolate", :age 21} 
	#Person{:first-name "Tom", :last-name "Sawyer", :age 35} #Person{:first-name "Adam", :last-name "Sawyer", :age 35})

## Update: updating records with a hash-map based on predicates.

	(update persons {:age 10})

Results in:
	
	(#Person{:first-name "Maarten", :last-name "Hus", :age 10} #Person{:first-name "Danny", :last-name "Kieanu", :age 10} 
	 #Person{:first-name "Cornel", :last-name "Berberus", :age 10} #Person{:first-name "Ronald", :last-name "Chocolate", :age 10})

	(update persons {:last-name "Goose"} :last-name "Hus" :first-name "Maarten")

Results in:

	(#Person{:first-name "Maarten", :last-name "Goose", :age 21} #Person{:first-name "Danny", :last-name "Kieanu", :age 21} 
	 #Person{:first-name "Cornel", :last-name "Berberus", :age 21} #Person{:first-name "Ronald", :last-name "Chocolate", :age 21})
	
## Persistence

Update, insert, where and delete all have persistent variants ie. update!, insert!, where! and delete!.
These don't take collections but ref's to collections.

For example:
	(def persons (ref [maarten danny cornel ronald]))
	(update! persons {:age 10})
	
Results in:
	(#Person{:first-name "Maarten", :last-name "Hus", :age 10} #Person{:first-name "Danny", :last-name "Kieanu", :age 10} 
	 #Person{:first-name "Cornel", :last-name "Berberus", :age 10} #Person{:first-name "Ronald", :last-name "Chocolate", :age 10})

# Install

If you're using leiningen just add [rql "1.0.0"] to your dependencies.

