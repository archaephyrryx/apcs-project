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
    public static void load(CompoundSymbol datatype, String filename) {
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

class CSVNode {
    public CSVEntry _entry;
    public ArrayList<CSVEntry> _entries;

    public CSVNode(CSVEntry entry) { _entry = entry; }
    public CSVNode(ArrayList<CSVEntry> entries) { _entries = entries; }
}


class CSVisitor extends SchemaBaseVisitor<CSVNode> {
    public CompoundSymbol _sym;

    public CSVisitor( CompoundSymbol sym ) {
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
	int numAttrs = _sym._attrs.size();
	int lengthEntry = csvline.size();
	if (numAttrs != lengthEntry) {
	    // This should get mad
	    // Exception, etc.
	} else {
	    for (int i = 0; i < numAttrs; ++i) {
		Attribute a = _sym._attrs.get(i);
		AVLTree t = _sym._trees.get(_sym._atIndex.get(a._name).intValue());
		if (t != null) {
		    t.addCSVEntry(csvline.get(i), csvline);
		}
	    }
	}
	return null;
    }

    @Override
    public CSVNode visitGetCSVQstr(SchemaParser.GetCSVQstrContext ctx) {
	String qstr = (ctx.QSTRING()).getText();
	CSVstr entry = new CSVstr(QString.unQuote(qstr));
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
