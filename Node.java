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
     *                  A  |||   A              
     *  *********      /   |||    \   ********* 
     *  * A>B>C *     B    |||     B  * B>C>A *  
     *  *********    /     |||    /   *********  
     *              C      |||   C                   
     *                     |||                       
     *  -------------------+++----------------                       
     *  -------------------+++---------------- 
     *                     |||                       
     *              A      |||     A          
     *  *********    \     |||    /   ********* 
     *  * A<B<C *     B    |||   B    * B<C<A *  
     *  *********      \   |||    \   *********  
     *                  C  |||     C               
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
	_left.parent = this;
    }
    protected void setRight(Node<T> right) {
	assert ( right.compareTo(parent) == this.compareTo(parent) && 
	         right.compareTo(this) == 1 );
	_right = right;
	right._parent = this;
    }
    
    // protected void add:
    // Calls setright or setleft, based on comparison.
    protected void add(Node<T> child) {

	assert (child.compareTo(this) != 0);

	if (child.compareTo(this) == -1)
	    setLeft(child);
	else
	    setRight(child);
    }
  
    // public Node<T> disown:
    // Node clears its corresponding child slot, child's parent slot; returns
    // child
    public Node<T> disown(int relation) {
	Node<T> orphan;
	switch relation {
	    -1:
		orphan = _left;
		orphan._parent = null;
		_left = null;
		return orphan;
	    1:
		orphan = _right;
		orphan._parent = null;
		_left = null;
		return orphan;
	}
    }


    // public Node<T> changeling:
    // Node swaps its child for the changeling, reassigns parenthood, and
    // returns former child.
    public Node<T> swap(Node<T> changeling) {
	Node<T> child = disown(changeling.compareTo(this));
	adopt(changeling);
	return child;
    }

    // protected Node<T> adopt:
    // Sets corresponding child slot, orphans's parent slot;
    protected void adopt(Node<T> orphan) {
	switch (orphan.compareTo(this)) {
	    -1:
		_left = orphan;
		orphan._parent = this;
	    1:
		_right = orphan;
		orphan._parent = this;
	}
    }

    
    /* protected boolean hasDescendant(Node<T> node) :
           returns a boolean indicating whether a node is a descendant of the
	   current node. */
    protected boolean hasDescendant(Node<T> node) {
	return (node == this ||
		(hasLeft() && _left.hasDescendant(node)) ||
		(hasRight() && _right.hasDescendant(node)));
    }

}