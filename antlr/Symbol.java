// package symbol;
import java.util.*;

class Symbol {
    public enum Type { IntType, StrType, BoolType, AliasType, CompoundType };
    public String _name;
    public Type _type;
    public Symbol(String name, Type type) { _name = name; _type = type; }
    public Type getType() { return this._type; }
}

class AtomSymbol extends Symbol {
    public AtomSymbol(String name, Type type) { super(name, type); }
    public Symbol.Type actualType() { return this.getType(); }
}

class PrimitiveSymbol extends AtomSymbol {
    public PrimitiveSymbol(String name, Type type) { super(name, type); }
}

class AliasSymbol extends AtomSymbol {
    public PrimitiveSymbol targetType;
    public AliasSymbol(String name, PrimitiveSymbol target) {
	super(name, Symbol.Type.AliasType);
	this.targetType = target;
    }
    @Override
    public Symbol.Type actualType() { return this.targetType.actualType(); }
}

class CompoundSymbol extends Symbol {
    public ArrayList<Attribute> _attrs;
    public ArrayList<AVLTree> _trees;
    public HashMap<String, Integer> _atIndex;

    public CompoundSymbol(String name, ArrayList<Attribute> attrs) {
	super(name, Symbol.Type.CompoundType);
	this._attrs = attrs;
	this._trees = new ArrayList<AVLTree>();
	this._atIndex = new HashMap<String, Integer>();
	int i = 0;
	for (Attribute attr : _attrs) {
	    if (attr._type.actualType() != Symbol.Type.BoolType) {
		_trees.add(new AVLTree());
	    } else {
		_trees.add(null);
	    }
	    _atIndex.put(attr._name, i++);
	}
    }
}

class Attribute {
    public String _name;
    public AtomSymbol _type;
    public Attribute(String name, AtomSymbol type) {
	this._name = name;
	this._type = type;
    }
    public Symbol.Type getType() {
	return this._type.getType();
    }
    public Symbol.Type getActualType() {
	return this._type.actualType();
    }
}
