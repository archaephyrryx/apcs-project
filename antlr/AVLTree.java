import java.util.*;

public class AVLTree {
    public AVLNode _root;
    public AVLNode _head;

    public AVLTree() { 
	_root = null;
    }

    public void addEntry( Entry csv, ArrayList<Entry> obj  ) {
	if (_root == null) {
	    _root = new AVLNode(csv, obj);
	    return;
	}

	_head = _root;
	int compvalue;

	while (_head != null) {
	    compvalue = csv.compareTo(_head._csv);

	    if (compvalue == 0) {
		_head.addObject( obj );
		return;
	    }

	    if (compvalue > 0) {
		if (_head.rightChild == null) {
		    _head.rightChild = new AVLNode(csv, obj);
		    _head.rightChild.parent = _head;
//		    this.rebalance(_head.rightChild);
		    return;
		}
		_head = _head.rightChild;
		continue;
	    }

	    if (compvalue < 0)  {
		if (_head.leftChild == null) {
		    _head.leftChild = new AVLNode(csv, obj);
		    _head.leftChild.parent = _head;
//		    this.rebalance(_head.leftChild);
		    return;
		}
		_head = _head.leftChild;
		continue;
	    }
	}
    }

    public ArrayList<ArrayList<Entry>> getObjects(Entry val) {
	int compvalue;
	_head = _root;

	while ( _head != null ) {
	    compvalue = val.compareTo(_head._csv);

	    if (compvalue < 0)
		_head = _head.leftChild;
	    if (compvalue == 0)
		return _head._objs;
	    if (compvalue > 0)
		_head = _head.rightChild;
	}
	return null;
    }

    public ArrayList<ArrayList<Entry>> getAll() {
	ArrayList<ArrayList<Entry>> pool = new ArrayList<ArrayList<Entry>>();
	_root.getAll(pool);
	return pool;
    }

    protected int height(AVLNode head) {
	int l = l_height(head);
	int r = r_height(head);
	return (1 + ((l > r) ? l : r));
    }

    protected int l_height(AVLNode head) {
	return (head.leftChild != null) ? height(head.leftChild) : 0;
    } 

    protected int r_height(AVLNode head) {
	return (head.rightChild != null) ? height(head.rightChild) : 0;
    }

    protected int balance(AVLNode head) {
	return (l_height(head) - r_height(head));
    }

    protected void rebalance(AVLNode node) {
	AVLNode link;

	for (link = _head = node; _head != null ; link = _head, _head = _head.parent) {
	    int bal = balance(_head);
	    if (bal < -1 || bal > 1) {
	        if (link.relation() == -1) {
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

    protected void rotate_right(AVLNode node) {
	AVLNode root, pivot, wedge;

	root = node;
	if (root.leftChild != null){ 
	    pivot = root.disownLeft();
	    if (pivot.rightChild != null) {
		wedge = pivot.disownRight();
		root.adoptLeft(wedge);
		if (root.parent != null) {
		    if (root.relation() == 1)
			root.parent.swapRight(pivot);
		    else
			root.parent.swapLeft(pivot);
		} else 
		    _root = pivot;
		pivot.adoptRight(root);
	    }
	}

    }
 
    protected void rotate_left(AVLNode node) {
	AVLNode root, pivot, wedge;

	root = node;
	if (root.rightChild != null) { 
	    pivot = root.disownRight();
	    if (pivot.leftChild != null) {
		wedge = pivot.disownLeft();
		root.adoptRight(wedge);
		if (root.parent != null) {
		    if (root.relation() == 1)
			root.parent.swapRight(pivot);
		    else
			root.parent.swapLeft(pivot);
		} else 
		    _root = pivot;
		pivot.adoptLeft(root);
	    }
	}
    }

}

class AVLNode {
    public AVLNode parent = null;
    public AVLNode leftChild = null;
    public AVLNode rightChild = null;

    public Entry _csv;
    public ArrayList<ArrayList<Entry>> _objs;

    public AVLNode(Entry csv, ArrayList<Entry> obj ) {
	_csv = csv;
	_objs = new ArrayList<ArrayList<Entry>>();
	_objs.add(obj);
    }
 
    public void getAll(ArrayList<ArrayList<Entry>> pool) {
	if (leftChild != null) {
	    leftChild.getAll(pool);
	}
	if (rightChild != null) {
	    rightChild.getAll(pool);
	}

	pool.addAll(this._objs);
    }


    public int compareTo(AVLNode other) {
	return _csv.compareTo(other._csv);
    }
    public int relation() {
	return (parent != null) ? (this.compareTo(parent)) : 0;
    }

    public void addObject(ArrayList<Entry> obj) {
	_objs.add(obj);
    }

    public AVLNode disownLeft() {
	AVLNode orphan = leftChild;
	orphan.parent = null;
	leftChild = null;
	return orphan;
    }
    public AVLNode disownRight() {
	AVLNode orphan = rightChild;
	orphan.parent = null;
	rightChild = null;
	return orphan;
    }

    public AVLNode swapLeft(AVLNode newLeftChild) {
	AVLNode oldLeftChild = this.disownLeft();
	this.adoptLeft(newLeftChild);
	return oldLeftChild;
    }

    public AVLNode swapRight(AVLNode newRightChild) {
	AVLNode oldRightChild = this.disownRight();
	this.adoptRight(newRightChild);
	return oldRightChild;
    }

    public void adoptLeft(AVLNode orphan) {
	leftChild = orphan;
	orphan.parent = this;
    }

    public void adoptRight(AVLNode orphan) {
	rightChild = orphan;
	orphan.parent = this;
    }
}
