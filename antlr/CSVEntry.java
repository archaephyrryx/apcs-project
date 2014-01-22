import java.util.*;

class CSVEntry {
    public enum EntryType { Number, Bool, String };
    public EntryType _type;
    public int _numberEntry;
    public boolean _boolEntry;
    public String _strEntry;

    public int compareTo(CSVEntry other) {
	if (_type == CSVEntry.EntryType.Number)
	    return (new Integer(this._numberEntry).compareTo(new Integer(other._numberEntry)));

	if (_type == CSVEntry.EntryType.Bool)
	    return (new Boolean(this._boolEntry).compareTo(new Boolean(other._boolEntry)));

	if (_type == CSVEntry.EntryType.String)
	    return (this._strEntry.compareTo(other._strEntry));

	return 0;
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
}

class CSVBool extends CSVEntry {
    public static final EntryType _type = CSVEntry.EntryType.Bool;
    public boolean _boolEntry;

    public CSVBool(boolean boolEntry) {
	super(_type, boolEntry);
	this._boolEntry = boolEntry;
    }
}

class CSVstr extends CSVEntry {
    public static final EntryType _type = CSVEntry.EntryType.String;
    public String _strEntry;

    public CSVstr(String strEntry) {
	super(_type, strEntry);
	this._strEntry = strEntry;
    }
}
