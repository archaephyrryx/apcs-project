/* 
 * class Node: Represents a single node of a binary tree, referenced and indexed
 * by a key, and storing a value. Nodes contain references to their parent, if
 * they are not a root node, and their left and right child, when the node has
 * either or both of them. 
 */

import java.util.*;

public class Node<T implements Comparable<T>> implements Comparable<Node<T>> {
    // Generically-typed comparable key
    protected T _key;

    // Object-typed value
    protected Object _value;

    // References to parent, left and right children.
    protected Node<T> _parent;
    protected Node<T> _left, _right;

    // A reference to the tree this node is planted in, if the node is planted.
    protected Tree<T> _tree;
    
    /* Boolean properties: 
     *     Does node have a left child node
     *     Does node have a right child node
     *     Does node have a parent node
     */
    protected boolean _hasLeft, _hasRight, _hasParent;

    // Constructor: creates root node with initial key
    public Node( T key ) {
	this(null, key, null);
	_hasParent = false;
    }

    // Constructor: creates root node with initial key, value
    public Node( T key, Object value ) {
	this(null, key, value);
	_hasParent = false;
    }

    /* Constructor: initializes empty node, specified as having a particular
     * parent node. This method should be implemented as only one part of a
     * grafting, as it is necessary to test whether such an addition is valid,
     * and, if it is, assign new values to various variables of both the child
     * and parent. */
    public Node( Node<T> parent, T key ) {
	this(parent, key, null);
    }

    /* See note for null parental constructor */
    public Node( Node<T> parent, T key, Object value ) {
	_key = key;
	_value = value;
	_parent = parent;
	_hasLeft = false;
	_hasRight = false;
	_hasParent = true;
    }


    // Implementation of Comparable's compareTo method: compares 
    public int compareTo(Node<T> other) {
	return (_key).compareTo(other._key);
    }

    // Accessor for protected T _key
    public T getKey() { return _key; }

    // Accessor for protected Oject _value
    public Object getValue() { return _value; }

    /* Accessor for connected nodes
     * Precondition: for each method, the corresponding variable must have been initialized.
     */
    public Node<T> getLeft() { return _left; } 
    public Node<T> getRight() { return _right; }
    public Node<T> getParent() { return _parent; }

    /* Accessor for presence of connected nodes
     * Returns a boolean indicating the initialization state of the implicitly
     * indicated reference variable.
     */
    public boolean hasLeft() { return _hasLeft; }
    public boolean hasRight() { return _hasRight; }
    public boolean hasParent() { return !(_hasParent); }

    /* Tree-Context state accessor
     * Returns a boolean representing whether the node is a leaf (no children),
     * or a root (no parent)
     */
    public boolean isRoot() { return !(_hasParent); }
    public boolean isLeaf() { return !( _hasLeft || _hasRight ); } 

    /* Relationship accessor:
     * Returns an integer value indicating the node's relative value with
     * respect to its parent, i.e. whether it is the left child or the right
     * child of its parent. A value of 0 is returned for root nodes. The
     * comparison is made between the node and its parent in that order, so a
     * value of -1 indicates that it is a left child, and 1, a right.
     */ 
    public int relation() {
	return (_hasParent) ? (self.compareTo(_parent)) : 0;
    }

    /* Tree state accessor
     * Returns true if the node has been rooted into a tree; i.e., if it is
     * itself the root of a tree, or if it is descended from a node that is
     * itself the root of a tree
     */
    public boolean isRooted() { return (tree != null); }

    /* protected boolean root(Tree<T> tree)
     *     Tree-reference mutator
     * Preconditions: cannot currently have a _tree reference
     * Arguments: Tree<T> tree is a tree of the same type as the current node,
     * which is the tree of its ancestral root.
     *
     * Sets the value of the _tree variable to be equal to Tree<T> tree.
     */
    protected boolean root(Tree<T> tree) {
	assert !(isRooted()) : "Already rooted.";
	_tree = tree;
    }

    /* public Tree<T> getTree():
     *     Tree-reference accessor
     * Returns the tree that the current node is rooted in.
     */
    public void getTree() {
	return _tree;
    }

    /* public Object setValue(Object value)
     *     Initializer-mutator for value
     * preconditions: _value must not be set, value arg must not be null
     * return: true if success, false if preconditions not both met
     */
    public boolean setValue(Object value) {
	if (_value != null || value == null) return false;

	_value = value;
	return true;
    }

    /* protected void setLeft(Node<T> left): Mutator for left child
     * protected void setRight(Node<T> right): Mutator for right child
     *
     * Preconditions: child arg belongs in indicated slot of this node, in the
     *                context of the tree
     * Enforced: Taking the parent of the current node as the root of a
     *           sub-tree, arg child must belong in the correct slot of the
     *           current node; i.e., taking A as parent, B as current, and C as
     *           arg, the following configurations must satisfy the
     *           following conditions:
     * 
     *                  A  |*|   A
     *  *********      /   |*|    \   *********
     *  * A>B>C *     B    |*|     B  * B>C>A * 
     *  *********    /     |*|    /   ********* 
     *              C      |*|   C    
     *
     * Pathological cases: Due to the enforcement of only the local conditions,
     * this method should not be called without complete recursive testing. For
     * this reason, it is protected, and should be called by a Root after
     * recursive testing for propriety of this particular insertion.
     */
    protected void setLeft(Node<T> left) {
	assert ( left.compareTo(parent) == this.compareTo(parent) && 
	         left.compareTo(this) == -1 );
	_left = left;
    }

    // Overwrite right child
    protected void setRight(Node<T> right) {
	assert (this.compareTo(newright) == -1) : "new right " + newleft._key + " < " + "node " + this._key;
	_right = right;
    }



    /* public boolean add( Node<T> child ):
     *     Adds a child node to the current node.
     * Preconditions: the current node must be the correct parent for the child
     * in the context of the overarching Tree structure, and the corresponding
     * child slot for the new child must be unoccupied. These conditions are
     * enforced 

     */
    public boolean add( Node<T> child ) {
	    /*
	    case 1:
		if (_hasLeft)
		    return _left.addChild(child);
		else
		    setLeft(child);
		return;
	    case -1:
		if (_hasRight)
		    return _right.addChild(child);
		else
		    setRight(child);

*/

}
    /* visibility return method( args ) :
       preconditions: (asserted)
       arguments:
           > type name: 
               - What it means
	       - Preconditions
	       - Modifications method makes
	       - Special cases
	       - Pathological cases
       function: (what it does)
       process: (how it does it)
           > Any proofs or explanations here

       modifications: (what changes)
       return value: what it returns, what that represents
       special/pathological cases: (if any)
       usage: (standalone, or as multi-step operation)
     */



