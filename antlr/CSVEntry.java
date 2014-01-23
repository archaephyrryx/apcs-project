import java.util.*;

class CSVEntry {
    public enum EntryType { Number, Bool, String };
    public EntryType _type;
    public int _numberEntry;
    public boolean _boolEntry;
    public String _strEntry;

    public int compareTo(CSVEntry other) {
	return 0;
    }

    public String toString() {
	return "This Should Be Overwritten";
    }

    public CSVEntry(EntryType type, int numberEntry) {
	_type = type;
	_numberEntry = numberEntry;
    }

    public CSVEntry(EntryType type, boolean boolEntry) {
	_type = type;
	_boolEntry = boolEntry;
    }

    public CSVEntry(EntryType type, String strEntry) {
	_type = type;
	_strEntry = strEntry;
    }
}

class CSVNumber extends CSVEntry {
    public static final EntryType _type = CSVEntry.EntryType.Number;
    public int _numberEntry;

    public CSVNumber(int numberEntry) {
	super(_type, numberEntry);
	this._numberEntry = numberEntry;
    }
    
    @Override
    public int compareTo(CSVEntry other) {
	if (other instanceof CSVNumber) {
	    return (this._numberEntry - other._numberEntry);
	} else {
	    // Error
	    System.err.println("Cannot compare different types of CSVEntry");
	    return 0;
	}
    }

    @Override
    public String toString() {
	return String.valueOf(_numberEntry);
    }
}

class CSVBool extends CSVEntry {
    public static final EntryType _type = CSVEntry.EntryType.Bool;
    public boolean _boolEntry;

    public CSVBool(boolean boolEntry) {
	super(_type, boolEntry);
	this._boolEntry = boolEntry;
    }

    @Override
    public int compareTo(CSVEntry other) {
	if (other instanceof CSVBool) {
	    return ((this._boolEntry == other._boolEntry) ? 0 : (this._boolEntry) ? 1 : -1);
	} else {
	    // Error
	    System.err.println("Cannot compare different types of CSVEntry");
	    return 0;
	}
    }

    @Override
    public String toString() {
	return String.valueOf(_boolEntry);
    }
}

class CSVstr extends CSVEntry {
    public static final EntryType _type = CSVEntry.EntryType.String;
    public String _strEntry;

    public CSVstr(String strEntry) {
	super(_type, strEntry);
	this._strEntry = strEntry;
    }
 
    @Override
    public int compareTo(CSVEntry other) {
	if (other instanceof CSVstr) {
	    return this._strEntry.compareTo(other._strEntry);
	} else {
	    // Error
	    System.err.println("Cannot compare different types of CSVEntry");
	    return 0;
	}
    }

    @Override
    public String toString() {
	return _strEntry;
    }
}
