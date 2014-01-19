/* 
 * Class AVLTree<T>: 
 * A class representing a self-balancing AVL tree
 */

import java.util.*;

public class AVLTree<T implements Comparable<T>> extends Tree<T>  {

   public AVLTree(Node<T> root) { 
	assert (!root.isRooted() || this = root.getTree()) : "RootNodeError: Node already rooted."
	    _root = root;
	_head = _root;
    }

    /* protected static int height(Node<T> head) 
     * static method returning height from a base-node, which is the value of
     * the argument to the method.
     */
    protected static int height(Node<T> head) {
	assert head.inTree() : "Indicated head not in tree";
	int l = l_height(head);
	int r = r_height(head);
	return (1 + (l > r) ? l : r);
    }

    /* left and right heights */ 
    protected static int l_height(Node<T> head) {
	return (head.hasLeft()) ? height(head.getLeft()) : 0;
    }
    protected static int r_height(Node<T> head) {
	return (head.hasRight()) ? height(head.getRight()) : 0;
    }

    /* protected static int balance(Node<T> head)
     *     Static method returning the balance-factor from a base-node.
     * In the AVL-Tree definition, the balance-factor for a node is given as the
     * value of the left-height minus the value of the right-height.
     */
    protected static int balance(Node<T> head) {
	return (l_height(head) - r_height(head));
    }

    /* public void add( Node<T> node ) :
       preconditions: No node currently in the tree has the same key as the new
		      node to be added
       arguments:
           > Node<T> node:
               - A node to be added to the tree
	       - Node has key not in tree
       function: roots head, recursively descends tree until it reaches the
           proper parent for the new node, determined by testing for children
	   and comparison
       modifications: adds a child to one node
       post-modifications: makes a call to the re-balance method after success.
     */
    public void add(Node<T> node) {
	if _root.add(node) {
	    rebalance(node);
	}
    }

    /* protected void rebalance(Node<T> node)
     *     rebalances the AVL tree after insertion
     * performs a recursive balance check on each succesive direct ancestor of
     * the newly-added node, and executes tree-rotations when the value of the
     * balance test is greater in magnitude than 1 (less than -1 or greater than
     * 1). Depending on the value of the balance test, a different tree-rotation
     * is performed.
     */
    protected void rebalance(Node<T> node) {
	assert (inTree(node)) : "Rebalancing about external node" ;
	Node<T> link;

	for (link = _head = node; goUp(); link = _head) {
	    int bal = balance(_head);
	    if (bal < -1 || bal > 1) {
	        if (relation(link) == -1) {
		    if (balance(link) < 0)
			rotate_left(link);
		    rotate_right(_head);
		} else {
		    if (balance(link) > 0)
			rotate_right(link);
		    rotate_left(_head);
		}
	    }
	}
    }

    /* protected void rotators:
     * Perform tree-rotations in order to rebalance an unbalanced branch of the
     * AVL-tree. Left-rotations and right-rotations are inversions of each
     * other. Below is an illustration of tree rotations applied to a tree:
     *                                                                 
     *        Q                           P                            
     *       / \     Right Rotation      / \                           
     *      P   C    -------------->    A   Q                          
     *     / \      <--------------        / \                         
     *    A   B      Left Rotation        B   C                        
     *                                                                 
     * 
     * The relative sizes of the nodes remains in accordance to the binary tree
     * sorting, as we can demonstrate by flattening each tree:
     * 
     *     A<P<B<Q<C   <---------> A<P<B<Q<C
     * 
     * The critical nodes in the transformation are the ones in a
     * left-right-left and right-left-right orientation, specifically Q, P, and
     * B. 
     *
     * Root refers to the local root (Q for right, P for left)
     * Pivot refers to the post-execution local root (P for right, Q for left)
     * Wedge refers to the node with the opposite relation as that of P.
     *
     * The steps in the algorithm are to splice the wedge into the position of
     * the pivot, splice the pivot into the position of the root, and splice
     * the root into the position of the wedge.
     */
    protected void rotate_right(Node<T> node) {
	Node<T> root, pivot, wedge;

	root = node;
	pivot = root.disown(-1);
	wedge = pivot.disown(1);

	root.adopt(wedge);

	root.getParent().swap(pivot);
	pivot.adopt(root);

    }
 
    protected void rotate_left(Node<T> node) {
	Node<T> root, pivot, wedge;

	root = node;
	pivot = root.disown(1);
	wedge = pivot.disown(-1);

	root.adopt(wedge);

	root.getParent().swap(pivot);
	pivot.adopt(root);
    }

}
