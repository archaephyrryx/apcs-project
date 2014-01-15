/* Class Node: represents a node on a binary tree. Knows its parent, its
 * immediate children, and how many descendents it has on either branch.
 * Each node contains a key and a value. */

import java.util.*;

public class Node<T implements Comparable> {
    // Instance vars for key and value
    private T _key;
    private Object _value;

    // Is root, is leaf.
    private boolean _isRoot, _isLeaf;

    private boolean _hasLeft, _hasRight;

    // Parent, left child, right child
    private Node<T> _parent;
    private Node<T> _left, _right;


    // Constructor: creates root node
    public Node() {
	_isRoot = true;
	_isLeaf = true;
	_hasRight = false;
	_hasLeft = false;
    }

    // Constructor: creates root node with initial key
    public Node( T key ) {
	this();
	_key = key;
    }

    // Constructor: creates root node with initial key, value
    public Node( T key, Object value ) {
	this();
	_key = key;
	_value = value;
    }

    public Node( Node<T> parent ) {
	this();
	_isRoot = false;
	_parent = parent;
	if (_parent.hasLeft && this == _parent.left) || (_parent.hasLeft && this == _parent.right ) ; 

    }

    public Node( Node<T> parent, T key ) {}
    public Node( Node<T> parent, T key, Object value ) {}




}
