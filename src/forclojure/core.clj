(ns forclojure.core
  (:gen-class))

(defn -main
  "I am completely useless and should be ignored"
  [& args]
  (println "zz zzz"))

;; -----------------------------------------------

;; Problem 130
(fn [node tree]
  (letfn [(append-back [l1 l2]
            (concat l1 (list l2)))

          (find-path [node tree]
            (cond
             (empty? tree) '()
             (= node (first tree)) (list node)
             :else (let [leads-to-node? #(if (= node (last %)) %)]
                     (conj (some leads-to-node? (map (partial find-path node) (rest tree)))
                           (first tree)))))

          (pull-path [path tree]
            (if (empty? path) 
              tree
              (let [current-node (first path)
                    root (first tree)
                    children (rest tree)
                    root-is-node? #(if (= current-node (first %)) %)
                    target-subtree (some root-is-node? children)]
                (pull-path (rest path)
                           (append-back target-subtree (conj (remove root-is-node? children)
                                                             root))))))]
    (pull-path (rest (find-path node tree)) tree)))

