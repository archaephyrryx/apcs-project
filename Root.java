/* 
 * class Root: Represents a root node of a binary tree, referenced and indexed
 * by a key, and storing a value. Stores a left and right child.
 */

import java.util.*;

public class Root<T extends Comparable<T>> extends Node<T> {
    // Same instance vars as Node

    // Constructor: creates root node with initial key, value
    public Root( T key, Object value ) {
	_key = key;
	_value = value;
	_hasLeft = false;
	_hasRight = false;
    }

    public Root( T key ) {
	this(key, null);
    }


    
    // Overwritten Node methodds
    public Node<T> getParent() { return null; }
    public boolean hasParent() { return false; }
    public boolean isRoot() { return true; }
    public int relation() { return 0; }

    /* Tree state accessor
     * Returns true if the root has been planted in a tree
     */
    public boolean isRooted() { return (_tree != null); }

    // Plants root in a tree, testing whether the tree has any root, if so
    // whether that is a different root, and otherwise that the tree is not
    // null and that the _tree var is null.
    public void root(Tree<T> tree) {
	assert (tree != null && _tree == null && (!(tree.hasRoot()) || tree.getRoot() == this));
	_tree = tree;
    }

    /* public boolean add( Node<T> child ): */
    public boolean add( Node<T> node ) {
	Node<T> parent = this;
	int compvalue;

	while (true) {
	    compvalue = node.compareTo(parent);
	    if (compvalue == 0) 
		return false;
	    if (compvalue == 1) {
		if (parent.goRight()) continue;
		else break;
	    } else {
		if (parent.goLeft()) continue;
		else break;
	    }
	}

	if (parent == root) {
	    parent.super.add(child);
	} else {
	    parent.add(child);
	}
	return true;
    }

    
    /* public boolean hasDescendant(Node<T> node) :
           returns a boolean indicating whether a node is a descendant of the
	   root. */
    public boolean hasDescendant(Node<T> node) {
	return (node == this ||
		(hasLeft() && _left.hasDescendant(node)) ||
		(hasRight() && _right.hasDescendant(node)));
    }
}
