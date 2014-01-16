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
    protected Node<T> _root;
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
/* Write a description below */

/* 
 * class : 
 * [description]
 */


/* Use or modify a template header below */

// public class  {
// public abstract class  {
// public interface  {
// public class  implements  {
// public class  extends  {


/* Use these for vars, methods */

    // private  ;
    // public  ;
    // protected  ;

    // private static  ;
    // public static  ;
    // protected static  ;


    // private ( ) {}
    // public  ( ) {}
    // protected ( ) {}

    // private static ( ) {}
    // public static ( ) {}
    // protected static ( ) {}

   


// }


/* Generic Descriptions of methods, variables */

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
       



