/* Class Tree: A binary tree srtructure, with methods for addition of elements,
 * searches for elements, and rebalancing. */

import java.util.*;

public class Tree<T> {
    protected Node<T> _root;
    protected Node<T> _head;

    // Constructor establishing first value
    public Tree(T rootkey) { 
	_root = new Node<T>();
	_head = _root;
    }

    public Tree(Node<T> root) { 
	_root = root;
	_head = _root;
    }

    public T getRootKey() { return _root.getKey(); }
    public T getHeadKey() { return _head.getKey(); }

    protected T goLeft() {
	_head = _head.getLeft();
	return getHeadKey();
    }

    protected T goLeft() {
	_head = _head.getLeft();
	return getHeadKey();
    }

    protected T goUp() {
	_head = _head.getParent();
	return getHeadKey();
    }

    public Tree<T> getSubTree() {
	return (new Tree(this._head));
    }

}
