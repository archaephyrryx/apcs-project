// package symbol;
import java.util.*;

class Symbol {
    public enum Type { IntType, StrType, BoolType, AliasType, CompoundType };
    public String _name;
    public Type _type;
    public Symbol(String name, Type type) { _name = name; _type = type; }
}

class AtomSymbol extends Symbol {
    public AtomSymbol(String name, Type type) { super(name, type); }
}

class PrimitiveSymbol extends AtomSymbol {
    public PrimitiveSymbol(String name, Type type) { super(name, type); }
}

class AliasSymbol extends AtomSymbol {
    public PrimitiveSymbol actualType;
    public AliasSymbol(String name, PrimitiveSymbol target) {
	super(name, Symbol.Type.AliasType);
	this.actualType = target;
    }
}

class CompoundSymbol extends Symbol {
    public ArrayList<Attribute> _attrs;
    public CompoundSymbol(String name, ArrayList<Attribute> attrs) {
	super(name, Symbol.Type.CompoundType);
	this._attrs = attrs;
    }
}

class Attribute {
    public String _name;
    public AtomSymbol _type;
    public Attribute(String name, AtomSymbol type) {
	this._name = name;
	this._type = type;
    }
}
