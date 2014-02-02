(ns shrubbery.core
  (:use [hiccup.core]))


(def vector-type (type []))

;(comment
;Need to define types:
;compound:
;map
;vector
;simple:
;string
;int
;float
;boolean
;nil
;
;;;build structure out of types
;build data according to structure
;)
;
;(comment :string :int :boolean :float :map :vector :nil )
;
;(comment
;
;data
;  clients
;    name
;    ttpic
;    context
;      photographer
;      prices
;    pages 
;      1 pic textfile 
;      2 pic textfile 
;      3 pic textfile 
;  portfolio  
;    client 1
;    client 2
;    client 3
;  pages
;    name title textfile
;    name title textfile
;    name title textfile
;  pricing
;    hours
;    fabrics
;
; 
;data {
;  "clients" [ {
;    "name" "bob"
;    "ttpic" "./bob/bobtt.jpg"
;    "context" { 
;      "photographer" { "name" "fred" "url" "http://fredspics.com" }
;      "prices" {
;        "dress" { "hours" 25 "silk" 5 "lace" 1 }
;        "veil" { "hours" 1 "lace" 3 }
;      }
;    }
;    "pages" [
;      { "pic" "./bob/bob1.jpg" "about" "./bob/bob1.txt" }
;      { "pic" "./bob/bob2.jpg" "about" "./bob/bob2.txt" }
;      { "pic" "./bob/bob3.jpg" "about" "./bob/bob3.txt" }
;    ]
;  } ]
;  "portfolio" [
;    "bob"
;    "sally"
;    "tom"
;  ]
;}
;
;structure {
;  "clients" [ { 
;    "name" "string" 
;    "ttpic "string"
;    "context" { "photographer" { "name" "string" "url" "string" } }
;  } ]
;}
;

;"clients" "vector"
;"clients.n" "map"
;"clients.n.name" "string"
;
;map
;  keys allowed / dropdown
;  add key fn
;
;
;)

;(comment 
;each node in structure needs:
;  fn for adding child (optional)
;  fn for removing self)

(def structure {:type :string})

;(def structure {:new vector :fns [{:name "Add" :fn #(conj % nil)}] })


(def rules 
  { :new vector :fns [{ :label "Add" }] }
)

(defn render-button [fn]
  (html [:span.btn (:label fn)]))

(defn render-buttons [rules]
  (map render-button (:fns rules)))


(def data ((rules :new)))

(derive (type []) ::vector)
(derive (type "") ::string)

(defmulti render (fn [x rules] (type x)))
(defmethod render ::vector [x rules] (html [:div {:class "vector"} (render-buttons rules) ]))
(defmethod render ::string [x rules] (str \" x \"))

(comment 
(defn render [x]
  (let [t (type x)]
    (cond
      (= t vector-type) (str "[" (apply str (map render x)) "]")
      :else (println t))))
)


(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render data rules)})
