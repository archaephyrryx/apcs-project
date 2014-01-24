public class Entry<T extends Comparable<T>> {
    public enum Type { Number, Bool, String };
    public Type _type;
    public int _numberValue;
    public boolean _boolValue;
    public String _strValue;

    public Entry(Type type, int numberValue) {
	_type = type;
	_numberValue = numberValue;
    }

    public Entry(Type type, boolean boolValue) {
	_type = type;
	_boolValue = boolValue;
    }

    public Entry(Type type, String strValue) {
	_type = type;
	_strValue = strValue;
    }

    public Type getType() {
	return _type;
    }
    
    public T getValue() {
	//overwritten
	return (T) null;
    }

    public int compareTo(Entry<T> other) {
	if (this._type == other._type)
	    return this.getValue().compareTo(other.getValue());

	else {
	    // Error
	    System.err.println("Cannot compare different types of Entry");
	    return 0;
	}
    }

    public boolean equals(Entry<T> other) {
	if (this._type == other._type)
	    return this.getValue().equals(other.getValue());

	else {
	    // Error
	    System.err.println("Cannot compare different types of Entry");
	    return false;
	}
    }




    public String toString() {
	//Overwritten
	return "OVERWRITTEN";
    }
}

class NumberEntry extends Entry<Integer> {
    public static final Type _type = Entry.Type.Number;
    public int _numberValue;

    public NumberEntry(int numberValue) {
	super(_type, numberValue);
	this._numberValue = numberValue;
    }

    @Override
    public Integer getValue() {
	return Integer.valueOf(_numberValue);
    }

    @Override
    public String toString() {
	return String.valueOf(_numberValue);
    }
}

class BoolEntry extends Entry<Boolean> {
    public static final Type _type = Entry.Type.Bool;
    public boolean _boolValue;

    public BoolEntry(boolean boolValue) {
	super(_type, boolValue);
	this._boolValue = boolValue;
    }

    @Override
    public Boolean getValue() {
	return Boolean.valueOf(_boolValue);
    }

    @Override
    public String toString() {
	return String.valueOf(_boolValue);
    }

}

class StringEntry extends Entry<String> {
    public static final Type _type = Entry.Type.String;
    public String _strValue;

    public StringEntry(String strValue) {
	super(_type, strValue);
	this._strValue = strValue;
    }
 
    @Override
    public String getValue() {
	return _strValue;
    }

    @Override
    public String toString() {
	return _strValue;
    }
}
