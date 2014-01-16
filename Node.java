/* Class Node: represents a node on a binary tree. Knows its parent, its
 * immediate children, and how many descendents it has on either branch.
 * Each node contains a key and a value. */

import java.util.*;

public class Node<T implements Comparable<T>> implements Comparable<Node<T>> {
    // Instance vars for key and value
    protected T _key;
    protected Object _value;

    protected Node<T> _parent;
    protected Node<T> _left, _right;

    protected boolean _hasLeft, _hasRight, _hasParent, _tree;

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


    // Accessors for key, value, left, right
    public T getKey() { return _key; }
    public Object getValue() { return _value; }
    public Node<T> getLeft() { return _left; } 
    public Node<T> getRight() { return _right; }
    public Node<T> getParent() { return _parent; }

    // Boolean methods for node status
    public boolean hasLeft() { return _hasLeft; }
    public boolean hasRight() { return _hasRight; }

    public boolean isRoot() { return !(_hasParent); }
    public boolean isLeaf() { return !( _hasLeft || _hasRight ); } 


    // Rooting in tree
    public boolean isRooted() { return (tree != null); }

    public void root(Tree<T> tree) {
	assert !(isRooted()) : "Already rooted.";
	_tree = tree;
    }
    public void getTree() {
	assert (isRooted()) : "Node not rooted.";
	return _tree;
    }

    // Mutator for value
    public Object setValue(Object value) {
	Object oldValue = _value;
	_value = value;
	return oldValue;
    }


    // Implementation of Comparable's compareTo method: compares 
    public int compareTo(Node<T> other) {
	return (this.getKey()).compareTo(other.geKey());
    }

    /* insertion method putLeft */
    protected void putLeft(Node<T> newleft) {
	assert (this.compareTo(newleft) == 1) : "new left " + newleft._key + " > " + "node " + this._key; 
	if (_hasLeft) {
	    Node<T> grandChild = _left;
	    _left = newleft;

	    newleft._parent = this;
	    newleft.addChild(grandChild);
	} else
	    setLeft(newleft);

	return true;
    }


    /* insertion method putRight: */
    protected void putRight(Node<T> newright) {
	assert (this.compareTo(newright) == -1) : "new right " + newleft._key + " < " + "node " + this._key;
	if (_hasRight) {
	    Node<T> grandChild = _right;
	    _right = newright;

	    newright._parent = this;
	    newright.addChild(grandChild);
	} else
	    setRight(newright);
    }

    // Overwrite left child
    protected void setLeft(Node<T> left) {
	assert (this.compareTo(newleft) == 1) : "new left " + newleft._key + " > " + "node " + this._key; 
	_left = left;
	return true;
    }

    // Overwrite right child
    protected void setRight(Node<T> right) {
	assert (this.compareTo(newright) == -1) : "new right " + newleft._key + " < " + "node " + this._key;
	_right = right;
    }

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
