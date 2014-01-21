import java.util.*;
import java.io.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {
    public static void main(String[] args) {
	try {
	    ANTLRInputStream input = new ANTLRFileStream(args[0]);
	    SchemaLexer lexer = new SchemaLexer(input);
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    SchemaParser parser = new SchemaParser(tokens);

	    parser.setBuildParseTree(true);  // tell ANTLR to build a parse tree
	    ParserRuleContext tree = parser.schema();

	    Context ctx = new Context();
	    FirstSchemaVisitor visitor = new FirstSchemaVisitor(ctx);
	    visitor.visit(tree);

	    CompoundSymbol book = (CompoundSymbol) ctx.symtab.get("Book");
	    for (Attribute a : book.attrs) {
		System.out.println(a.name + ": " + a.type.name);
	    }
	} catch (java.io.IOException e) {
	    System.err.println(e);
	}
    }
}

class Context {
    public HashMap<String, Symbol> symtab = new HashMap<String, Symbol>();

    public Context() {
	symtab.put("int", new PrimitiveSymbol("int", Symbol.Type.IntType));
	symtab.put("bool", new PrimitiveSymbol("bool", Symbol.Type.BoolType));
	symtab.put("string", new PrimitiveSymbol("string", Symbol.Type.StrType));
    }
}

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

class QString {
    public String _qstring;

    public QString(String qstring) {
	_qstring = qstring;
    }

    public String unQuote() {
	String unq = _qstring;
	unq = unq.replaceAll("\\\\(.)", "\\1");
	unq = unq.replaceAll("^\\\"", "");
	unq = unq.replaceAll("\\\"$", "");
	return unq;
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
    public EntryType _type = Number;
    public int _numberEntry;


    public CSVNumber(int numberEntry) {
	super(_type, numberEntry);
	this._numberEntry = numberEntry;
    }
}

class CSVBool extends CSVEntry {
    public EntryType _type = Bool;
    public boolean _boolEntry;

    public CSVBool(boolean boolEntry) {
	super(_type, boolEntry);
	this._boolEntry = boolEntry;
    }
}

class CSVQstr extends CSVEntry {
    public EntryType _type = Str;
    public String _strEntry;

    public CSVQstr(QString QstrEntry) { this(QstrEntry.unQuote()); }

    public CSVQstr(String strEntry) {
	super(_type, strEntry);
	this._strEntry = strEntry;
    }
}

class Node {
    public String _str;
    public Symbol _sym;
    public Attribute _attr;
    public ArrayList<Attribute> _attrs;
    public CSVEntry _entry;
    public ArrayList<CSVEntry> _entries;

    public Node(String str) { _str = str; }
    public Node(Symbol sym) { _sym = sym; }
    public Node(Attribute attr) { _attr = attr; }
    public Node(ArrayList<Attribute> attrs) { _attrs = attrs; }
    public Node(CSVEntry entry) { _entry = entry; }
    public Node(ArrayList<CSVEntry> entries) { _entries = entries; }
}


class FirstSchemaVisitor extends SchemaBaseVisitor<Node> {
    private Context _ctx = null;

    public FirstSchemaVisitor( Context ctx ) {
	_ctx = ctx;
    }

    @Override
    public Node visitGetsym(SchemaParser.GetsymContext ctx) {
	String tok = ctx.t.getText();
	// XXX: Handle not found, handle non-Atom
	AtomSymbol sym = (AtomSymbol) _ctx.symtab.get(tok);
	return new Node(sym);
    }

    @Override
    public Node visitAddFirstProp(SchemaParser.AddFirstPropContext ctx) {
	Attribute a = visit(ctx.prop()).attr;
	ArrayList<Attribute> attrs = new ArrayList<Attribute>();
	attrs.add(a);
	return new Node(attrs);
    }

    @Override
    public Node visitAddNextProp(SchemaParser.AddNextPropContext ctx) {
	Attribute a = visit(ctx.prop()).attr;
	ArrayList<Attribute> attrs = visit(ctx.proplist()).attrs;
	attrs.add(a);
	return new Node(attrs);
    }

    @Override
    public Node visitDeclareProp(SchemaParser.DeclarePropContext ctx) {
	String name = ctx.ID().getText();
	AtomSymbol sym = (AtomSymbol) visit(ctx.ptype()).sym;
	Attribute attr = new Attribute(name, sym);
	return new Node(attr);
    }

    @Override
    public Node visitLetType(SchemaParser.LetTypeContext ctx) {
	String name = ctx.ID().getText();
	ArrayList<Attribute> attrs = visit(ctx.proplist()).attrs;
	_ctx.symtab.put(name, new CompoundSymbol(name, attrs));
	return null;
    }

    @Override
    public Node visitClarifyType(SchemaParser.ClarifyTypeContext ctx) {
	String name = ctx.ID().getText();
	PrimitiveSymbol sym = (PrimitiveSymbol) visit(ctx.ptype()).sym;
	AliasSymbol alias = new AliasSymbol(name, sym);
	_ctx.symtab.put(name, alias);
	return null;
    }

    @Override
    public Node visitLoad(SchemaParser.LoadContext ctx) {
	String type = ctx.ID().getText();
	CompoundSymbol sym = (CompoundSymbol) _ctx.symtab.get(type);
	String qfile = ctx.QSTRING().getText();
     /* QString qfile = new Qstring(ctx.QSTRING().getText());
	String file = qfile.unQuote();
        /*
      */
	String file = qfile.replaceAll("\\\\(.)", "\\1");
	file = file.replaceAll("^\\\"", "");
	file = file.replaceAll("\\\"$", "");
   // */
	loadData(file, sym);
	return null;
    }

    @Override
    public void loadData(String filename, CompoundSymbol sym) {
	System.out.printf("Will load %s data from %s.\n", sym.name, filename);
	try {
	    BufferedReader read = new BufferedReader(new FileReader(filename));
	} catch (java.io.IOException e) {
	    System.err.println(e);
	    System.exit(1);
	}
    }

    @Override
    public Node visitGetFirstCSVEntry(SchemaParser.GetFirstCSVEntryContext ctx) {
	CSVEntry e = visit(ctx.csventry());
	ArrayList<CSVEntry> entries = new ArrayList<CSVEntry>();
	entries.add(e);
	return new Node(entries);
    }

    @Override
    public Node visitGetNextCSVEntry(SchemaParser.GetNextCSVEntryContext ctx){
	CSVEntry e = visit(ctx.csventry());
	ArrayList<CSVEntry> entries = visit(ctx.csvrecord()).entries;
	entries.add(e);
	return new Node(entries);
    }

    @Override
    public Node visitGetCSVRecord(SchemaParser.GetCSVRecordContext ctx) {
	ArrayList<CSVEntry> csvline = visit(ctx.csvrecord()).entries;
	return new Node(csvline);
    }

    @Override
    public Node visitGetCSVQstr(SchemaParser.GetCSVQstrContext ctx) {
	QString qstr = (ctx.QSTRING()).getText();
	CSVQstr entry = new CSVQstr(qstr);
	return new Node(entry);
    }

    @Override
    public Node visitGetCSVNumber(SchemaParser.GetCSVNumberContext ctx) {
	int number = Integer((ctx.NUMBER()).getText).intValue();
	CSVNumber entry = new CSVNumber(number);
	return new Node(entry);
    }

    @Override
    public Node visitGetCSVBool(SchemaParser.GetCSVBoolContext ctx) {
	boolean bool = Boolean((ctx.NUMBER()).getText).booleanValue();
	CSVBool entry = new CSVBool(bool);
	return new Node(entry);
    }
}
