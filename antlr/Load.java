import java.util.*;
import java.io.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Load {
    public static void load(Symbol datatype, String filename) {
	try {
	    ANTLRInputStream input = new ANTLRFileStream(filename);
	    SchemaLexer lexer = new SchemaLexer(input);
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    SchemaParser parser = new SchemaParser(tokens);

	    ParserRuleContext tree = parser.csvfile();

	    CSVisitor visitor = new CSVisitor(datatype);
	    visitor.visit(tree);
	} catch (java.io.IOException e) {
	    System.err.println(e);
	}
    }
}

class CSVEntry {
    public enum EntryType { Number, Bool, String };
    public EntryType _type;
    public int _numberEntry;
    public boolean _boolEntry;
    public String _strEntry;

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

class CSVQstr extends CSVEntry {
    public static final EntryType _type = CSVEntry.EntryType.String;
    public String _strEntry;

    public CSVQstr(QString QstrEntry) { this(QstrEntry.unQuote()); }

    public CSVQstr(String strEntry) {
	super(_type, strEntry);
	this._strEntry = strEntry;
    }
}

class CSVNode {
    public CSVEntry _entry;
    public ArrayList<CSVEntry> _entries;

    public CSVNode(CSVEntry entry) { _entry = entry; }
    public CSVNode(ArrayList<CSVEntry> entries) { _entries = entries; }
}


class CSVisitor extends SchemaBaseVisitor<CSVNode> {
    public Symbol _sym;


    public CSVisitor( Symbol sym ) {
	_sym = sym;
    }

    @Override
    public CSVNode visitGetFirstCSVEntry(SchemaParser.GetFirstCSVEntryContext ctx) {
	CSVEntry e = visit(ctx.csventry())._entry;
	ArrayList<CSVEntry> entries = new ArrayList<CSVEntry>();
	entries.add(e);
	return new CSVNode(entries);
    }

    @Override
    public CSVNode visitGetNextCSVEntry(SchemaParser.GetNextCSVEntryContext ctx){
	CSVEntry e = visit(ctx.csventry())._entry;
	ArrayList<CSVEntry> entries = visit(ctx.csvrecord())._entries;
	entries.add(e);
	return new CSVNode(entries);
    }

    @Override
    public CSVNode visitGetCSVRecord(SchemaParser.GetCSVRecordContext ctx) {
	ArrayList<CSVEntry> csvline = visit(ctx.csvrecord())._entries;
	// Load and store data
	return null;
    }


    @Override
    public CSVNode visitGetCSVQstr(SchemaParser.GetCSVQstrContext ctx) {
	String qstr = (ctx.QSTRING()).getText();
	CSVQstr entry = new CSVQstr(qstr);
	return new CSVNode(entry);
    }

    @Override
    public CSVNode visitGetCSVNumber(SchemaParser.GetCSVNumberContext ctx) {
	int number = new Integer((ctx.NUMBER()).getText()).intValue();
	CSVNumber entry = new CSVNumber(number);
	return new CSVNode(entry);
    }

    @Override
    public CSVNode visitGetCSVBool(SchemaParser.GetCSVBoolContext ctx) {
	boolean bool = new Boolean((ctx.v).getText()).booleanValue();
	CSVBool entry = new CSVBool(bool);
	return new CSVNode(entry);
    }
}
