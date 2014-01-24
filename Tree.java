/* 
 * Class Tree<T>: 
 * A class representing a binary-search tree, composed of Nodes, which are all
 * Node<T>, and which are specified in Node.java
 * 
 * This tree is non-rebalancing; however, it can be extended to an AVLTree,
 * which re-balances itself with every operation. 
 *
 * This class consists of references to a root node (_root) and a head node
 * (_head), which can descend or ascend the tree in order to perform searches,
 * additions, grafts, splices, etc.
 * 
 * Method description can be found above each method's header.
 */

import java.util.*;

public class Tree<T implements Comparable<T>> {
    protected Root<T> _root;
    protected Node<T> _head;

    /* public Tree<T>( Node<T> root ) :
           A constructor.

       preconditions: Node and Tree have the same generic type
       arguments:
           > Node<T> root: 
               - Arg: The root about which the tree is to be constructed
	       - Pre: Not yet rooted (_isRooted is false)
	       - Mod: _isRooted is turned on
       function: roots a tree at the argument node.
       return: the Tree thus created
       usage: Called after a root node is constructed, with that node as the root argument
     */
    public Tree(Node<T> root) { 
	assert (!root.isRooted() || this = root.getTree()) : "RootNodeError: Node already rooted."
	_root = root;
	_head = _root;
	_root.root(this);
    }

    /* public T getRootKey() :
           An accessor.
       return: the key of the root node
     */
    public T getRootKey() { return _root.getKey(); }

    /* public T getHeadKey() :
           An accessor.
       return: the key of the head node.
     */
    public T getHeadKey() { return _head.getKey(); }

    /* protected boolean goLeft() :
           A mutator.
       initial test: head node has left child
       success: head is moved to its left child, returns true
       failure: return false
     */
    protected boolean goLeft() {
	if !(_head.hasLeft()) return false;
	_head = _head.getLeft();
	return true;
    }

    /* protected boolean goRight() :
           A mutator.
       initial test: head node has right child
       success: head is moved to its right child, returns true
       failure: return false
     */
   protected boolean goRight() {
	if !(_head.hasRight()) return false;
	_head = _head.getRight();
	return true;
    }

    /* protected T goUp() :
           A mutator/accessor.
       preconditions: head node has parent (i.e. is not root)
       modifications: head is moved to the parent of the previous head
       return: the key of the new head-node (parent of old head-node).
     */
    protected boolean goUp() {
	if (_head == _root) return false;
	_head = _head.getParent();
	return true;
    }

    /* protected T rootHead() :
           A mutator/accessor
       modifications: returns head to root
       return: the key at new head-node (root)
     */
    protected T rootHead() {
	if (_head != _root) {
	    _head = _root;
	}
	return _head.getKey();
    }

    /* public boolean add( Node<T> node ) :
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
     */
    public boolean add(Node<T> node) {
	return _root.add(node);
    }


    /* public bolean inTree(Node<T> node) :
	    tests whether a particular node is in the tree.  */
    public boolean inTree(Node<T> node) {
	return _root.hasDescendant(node);
    }


}