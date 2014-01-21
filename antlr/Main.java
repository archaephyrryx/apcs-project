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
    public String name;
    public Type type;
    public Symbol(String _name, Type _type) { name = _name; type = _type; }
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
    public ArrayList<Attribute> attrs;
    public CompoundSymbol(String name, ArrayList<Attribute> _attrs) {
	super(name, Symbol.Type.CompoundType);
	this.attrs = _attrs;
    }
}
class Attribute {
    public String name;
    public AtomSymbol type;
    public Attribute(String _name, AtomSymbol _type) {
	this.name = _name;
	this.type = _type;
    }
}

class Node {
    public String str;
    public Symbol sym;
    public Attribute attr;
    public ArrayList<Attribute> attrs;

    public Node(String _str) { str = _str; }
    public Node(Symbol _sym) { sym = _sym; }
    public Node(Attribute _attr) { attr = _attr; }
    public Node(ArrayList<Attribute> _attrs) { attrs = _attrs; }
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
	String file = qfile.replaceAll("\\\\(.)", "\\1");
	file = file.replaceAll("^\\\"", "");
	file = file.replaceAll("\\\"$", "");
	loadData(file, sym);
	return null;
    }

    public void loadData(String filename, CompoundSymbol sym) {
	System.out.printf("Will load %s data from %s.\n", sym.name, filename);
	try {
	    BufferedReader read = new BufferedReader(new FileReader(filename));
	} catch (java.io.IOException e) {
	    System.err.println(e);
	    System.exit(1);
	}
    }
}
