(ns shen.test
  (:use [clojure.test]
        [shen.primitives :only (value shen-kl-to-clj λ 神 define defmacro parse-shen parse-and-eval-shen)])
  (:refer-clojure :exclude [eval defmacro])
  (:require [shen]
            [shen.primitives :as primitives]))

(define super
  [Value Succ End] Action Combine Zero ->
  (if (End Value)
    Zero
    (Combine (Action Value)
             (super [(Succ Value) Succ End]
                    Action Combine Zero))))

(define for
  Stream Action -> (super Stream Action do 0))

(define filter
  Stream Condition ->
  (super Stream
         (λ Val (if (Condition Val) [Val] []))
         append
         []))

;; (defmacro exec-macro
;;   [exec Expr] -> [trap-error [time Expr] [λ E failed]])

;; (parse-and-eval-shen "(defmacro exec-macro [exec Expr] -> [trap-error [time Expr] [/. E failed]])")

;; (deftest shen-defmacro
;;   (are [shen out] (re-find (re-pattern out) (with-out-str (shen/print shen)))
;;        (神
;;         (exec (/ 8 2)))
;;        (str
;;         "run time: \\d+ secs" "\n"
;;         "4")

;;        (神
;;         (exec (/ 2 0)))
;;         "failed"
;;         ))


(deftest shenlanguage.org
  (are [shen out] (= out (with-out-str (shen/print shen)))

       (神
        (clj/with-out-str
          (for [0 (+ 1) (= 10)] print)))
       "\"0123456789\""

       (神
        (filter [0 (+ 1) (= 100)]
                (λ X (integer? (/ X 3)))))
       "[0 3 6 9 12 15 18 21 24 27 30 33 36 39 42 45 48 51 54 57 60... etc]"

       ))

(deftest partials
  (are [shen result] ((if (fn? result) result #{result}) shen)

       (神
        ((λ X Y (/ X Y)) 10 5))
       2

       (神
        ((λ X Y (+ X Y)) 2))
       fn?

       (神
        ((λ X (integer? (/ X 3))) 3))
       true

       ))

(deftest cons-pair
  (are [shen result] ((if (fn? result) result #{result}) shen)

       (神
        (cons 1 2)
        [1 2])

       (神
        (cons 1 (cons 2 ()))
        '(1 2))

       (神
        [1 2]
        '(1 2))

       ))

(deftest printer
  (are [shen out] (= out (with-out-str (shen/print shen)))

       (神
        ())
       "[]"

       (神
        (cons 1 (cons 2 ())))
       "[1 2]"

       (神
        (cons 1 2))
       "[1 | 2]"

       ;; (神
       ;;  (absvector 1))
       ;; "<fail!>"

       (神
        (vector 1))
       "<...>"

       (神
        (vector 0))
       "<>"

       (神
        (@p 1 2))
       "(@p 1 2)"

       ))

(deftest eval
  (are [shen result] ((if (fn? result) result #{result})
                      (-> shen parse-and-eval-shen))

       "((/. X (+ X 2)) 1)"
       3

       "((/. X Y (+ X Y)) 2 2)"
       4

       "((/. X Y (+ X Y)) 2)"
       fn?

       "(filter [0 (+ 1) (= 100)] (/. X (integer? (/ X 3))))"
       seq?

       ))


(deftest parser
  (are [clj kl-str] (= clj (-> kl-str parse-shen
                               shen-kl-to-clj))
       1 "1"
       1.0 "1.0"
       ''symbol "symbol"
       nil ""
       nil "nil"
       true "true"
       false "false"
       "String" "\"String\""
       () "()"
       '(+ 1 1) "(+ 1 1)"))

(defn test-programs []
  (神
   (do
     (cd "shen/test-programs")
     (load "README.shen")
     (load "tests.shen"))))

;; (deftest README.shen
;;   (is (test-programs))
;;   (is (= 0 (value '*failed*))))

(defn toggle-trace [tfn]
  (require 'clojure.tools.trace)
  (doseq [ns '[shen shen.primitives]]
    ((ns-resolve 'clojure.tools.trace tfn) ns)))
