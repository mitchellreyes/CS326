;1. (13 pts) Write a recursive Scheme function (subst x y L), which returns a list identical
;to L except that every occurrence of x has been replaced with y. The following example
;illustrates the use of this function:
;ex: > (subst 'c 'k '(c o c o n u t))
;        (k o k o n u t) 

(define (subst x y L)
    (cond
        (
(equal? (length L) 0) L)
        (
(equal? x (car L)) (set-car! L y) (subst x y (cdr L)) L)
        (
(subst x y (cdr L)) L)
         )
     )

;2. (13 pts) Write a recursive Scheme function (all-different? L), which determines
;whether all elements of list L are distinct (that is, not equal?). The following example
;illustrates the use of this function:
;> (all-different? '(3 7 2 7))
;#f

(define (member? x L)
    (cond
        ((null? L) #f)
        ((equal? x (car L)) #t)
        (else (member? x (cdr L)))
    )
)

(define (all-different? L)
    (cond
        ((null? L) #t)
        ((equal? (member? (car L) (cdr L)) #t) #f)
        (else (all-different? (cdr L)))
    )
)

;3. (60 pts) Consider an implementation of binary trees with Scheme lists, as in the following example: (define T '(13 (5 (1 () ()) (8 () (9 () ()))) (22 (17 () ()) (25 () ())))) Before proceeding, it may be useful to define three auxiliary functions (left T), (right T) and (val T) which return the left subtree, the right subtree, and the value in the root of tree T, respectively. 

(define (left T)
    ;returns the left subtree
    (if (null? T) '()
        (cadr T)
    )
)

(define (right T)
    ;returns the right subtree
    (if (null? T) '()
        (caddr T)
    )
)

(define (val T)
    ;returns the value in the root of tree
    (if (null? T) '()
        (car T)
    )
)

;a) (15 pts) Write a recursive function (n-nodes T), which returns the number of nodes in the tree T. 
 
(define (n-nodes T)
    ;finds the number of nodes in a tree
    (if (null? T) 0
      (+ 1 (n-nodes (left T)) (n-nodes (right T)))
    )
)
         
;b) (15 pts) Write a recursive function (n-leaves T), which returns the number of leaves in the tree T. 

(define (n-leaves T)
    (cond
        [(null? T) 0]
        [(and (null? (left T)) (null? (right T))) 1]
        [(+ (n-leaves (left T)) (n-leaves (right T)))]
    )
)

;c) (15 pts) The height of a tree is defined as the maximum number of nodes on a path from the root to a leaf. Write a recursive function (height T), which returns the height of the tree T. 

(define (max? x y)
    (if (> x y) x
        y)
)

(define (height T)
    (cond
        [(null? T) 0]
        [(+ 1 (max? (height (left T)) (height (right T))))]
    )
)


;d) (15 pts) Write a recursive function (postorder T), which returns the list of all elements in the tree T corresponding to a postorder traversal of the tree.
 
(define (postorder T)
    (if (null? T) '()
        (append (postorder (left T))
                (postorder (right T))   
                (list (val T))
        )
    )
)

;4. (14 pts) Write a recursive Scheme function (flatten L), which takes as arguments a list L (possibly containing sublists), and returns a list containing all elements in L and its sublists, but all at the same level.

(define (flatten L)
    (cond 
        [(null? L) '()]
        [(list? (car L)) (append (flatten (car L))
            (flatten (cdr L)))]
        [else (cons (car L) (flatten (cdr L)))]
    )
)
;5. (Extra Credit - 10 pts) A binary search tree is a binary tree for which the value in each node is greater than or equal to all values in its left subtree, and less than all values in its right subtree. The binary tree given as example in problem 3 also qualifies as a binary search tree. Using the same list representation, write a recursive function (member-bst? V T), which determines whether V appears as an element in the binary search tree T. Make sure you actually use binary search.

(define (bst-member? V T)
    (cond
        [(null? T) #f]
        [(= V (val T)) #t]
        [(> V (val T)) (bst-member? V (right T))]
        [else (bst-member? V (left T))]
    )
)


            


