import java.util.*;
import java.io.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
// import symbol.*;

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
	    Attribute year = book._attrs.get(0);
	    AVLTree yearTree = book._trees.get(0);

	    ArrayList<ArrayList<CSVEntry>> mybooks = yearTree.getObjects(new CSVNumber(2013));

	    if (mybooks == null) {
		System.out.println("No " + book._name + "s found matching criterion.");
		return;
	    }

	    for (ArrayList<CSVEntry> a : mybooks) {
		int s = a.size();
		for (int i = 0; i < s; ++i) {
		    System.out.println(book._attrs.get(i)._name + ": " + a.get(i));
		}
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

class Node {
    public String _str;
    public Symbol _sym;
    public Attribute _attr;
    public ArrayList<Attribute> _attrs;

    public Node(String str) { _str = str; }
    public Node(Symbol sym) { _sym = sym; }
    public Node(Attribute attr) { _attr = attr; }
    public Node(ArrayList<Attribute> attrs) { _attrs = attrs; }
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
	Attribute a = visit(ctx.prop())._attr;
	ArrayList<Attribute> attrs = new ArrayList<Attribute>();
	attrs.add(a);
	return new Node(attrs);
    }

    @Override
    public Node visitAddNextProp(SchemaParser.AddNextPropContext ctx) {
	Attribute a = visit(ctx.prop())._attr;
	ArrayList<Attribute> attrs = visit(ctx.proplist())._attrs;
	attrs.add(a);
	return new Node(attrs);
    }

    @Override
    public Node visitDeclareProp(SchemaParser.DeclarePropContext ctx) {
	String name = ctx.ID().getText();
	AtomSymbol sym = (AtomSymbol) visit(ctx.ptype())._sym;
	Attribute attr = new Attribute(name, sym);
	return new Node(attr);
    }

    @Override
    public Node visitLetType(SchemaParser.LetTypeContext ctx) {
	String name = ctx.ID().getText();
	ArrayList<Attribute> attrs = visit(ctx.proplist())._attrs;
	_ctx.symtab.put(name, new CompoundSymbol(name, attrs));
	return null;
    }

    @Override
    public Node visitClarifyType(SchemaParser.ClarifyTypeContext ctx) {
	String name = ctx.ID().getText();
	PrimitiveSymbol sym = (PrimitiveSymbol) visit(ctx.ptype())._sym;
	AliasSymbol alias = new AliasSymbol(name, sym);
	_ctx.symtab.put(name, alias);
	return null;
    }

    @Override
    public Node visitLoad(SchemaParser.LoadContext ctx) {
	String type = ctx.ID().getText();
	CompoundSymbol sym = (CompoundSymbol) _ctx.symtab.get(type);
	String file = QString.unQuote(ctx.QSTRING().getText());
	loadData(file, sym);
	return null;
    }

    public void loadData(String filename, CompoundSymbol sym) {
	System.out.printf("Will load %s data from %s.\n", sym._name, filename);
	Load.load(sym, filename);
    }
}
