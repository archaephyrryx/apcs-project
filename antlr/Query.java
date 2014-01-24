import java.io.*;
import java.util.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Query {
    public static void query(CompoundSymbol datatype, String filename) {
	try {
	    ANTLRInputStream input = new ANTLRFileStream(filename);
	    SchemaLexer lexer = new SchemaLexer(input);
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    SchemaParser parser = new SchemaParser(tokens);

	    ParserRuleContext tree = parser.queryfile();
	    QueryVisitor visitor = new QueryVisitor(datatype);
	    visitor.visit(tree);
	} catch (java.io.IOException e) {
	    System.err.println(e);
	}
    }
}

class Constraint {
    public enum Comparison { Equals };
    public Attribute _attr;
    public Comparison _comp;
    public Entry _value;


    public Constraint( Attribute attr, Comparison comp, Entry value ) {
	Symbol.Type attrType = attr.getActualType();
	Entry.Type valueType = value.getType();

	if ((attrType == Symbol.Type.IntType && valueType == Entry.Type.Number) ||
	    (attrType == Symbol.Type.BoolType && valueType == Entry.Type.Bool) ||
	    (attrType == Symbol.Type.StrType && valueType == Entry.Type.String)) {

	    _attr = attr;
	    _comp = comp;
	    _value = value;
	}
	else {
	    // Type exception
	}
    }
}


class QNode {
    public Entry _value;
    public Constraint _constraint;
    public ArrayList<Constraint> _constraints;
    public Attribute _attr;
    public Constraint.Comparison _comp;

    public QNode(Entry value) { _value = value; }
    public QNode(Constraint constraint) { _constraint = constraint; }
    public QNode(ArrayList<Constraint> constraints) { _constraints = constraints; }
    public QNode(Attribute attr) { _attr = attr; }
    public QNode(Constraint.Comparison comp) { _comp = comp; }
}


class QueryVisitor extends SchemaBaseVisitor<QNode> {
    public CompoundSymbol _sym;

    public QueryVisitor( CompoundSymbol sym ) {
	_sym = sym;
    }

    @Override
    public QNode visitGetQuery(SchemaParser.GetQueryContext ctx) {
	ArrayList<Constraint> cons = visit(ctx.constraints())._constraints;
	ArrayList<ArrayList<Entry>> matches = initialize(cons);

	for (Constraint current : cons) {
	    matches = filter(matches, current);
	}

	printOut(matches);

	return null;
    }


    public ArrayList<ArrayList<Entry>> initialize(ArrayList<Constraint> cons) {
	ArrayList<ArrayList<Entry>> matches;
	for (Constraint current : cons) {
	    if (current._attr.getActualType() == Symbol.Type.StrType) {
		matches = findMatch(current);
		return matches;
	    }
	}
	for (Constraint current : cons) {
	    if (current._attr.getActualType() == Symbol.Type.IntType) {
		matches = findMatch(current);
		return matches;
	    }
	}
	for (Constraint current : cons) {
	    if (current._attr.getActualType() == Symbol.Type.BoolType) {
		matches = findMatch(current);
		return matches;
	    }
	}
	return null;
    }

    public void printOut(ArrayList<ArrayList<Entry>> matches) {

	System.out.printf("Successfully found %d %ss meeting criterion-set.\n\n", matches.size(), _sym._name);
	int i = 0;
	for (ArrayList<Entry> a : matches) {
	    System.out.printf("Match #%d:\n", (++i));
	    int s = a.size();
	    for (int j = 0; j < s; ++j) {
		System.out.printf("\t%s: %s\n", _sym._attrs.get(j)._name, a.get(j).toString());
	    }
	    System.out.println();
	}
    }

    public ArrayList<ArrayList<Entry>> findMatch(Constraint con) {
	Attribute attr = con._attr;
	Entry val = con._value;
	Constraint.Comparison comp = con._comp;
	if (con._attr.getActualType() == Symbol.Type.BoolType) {
	    return getAll();
	} else {
	    AVLTree tree = _sym._trees.get(_sym._atIndex.get(attr._name).intValue());

	    if (comp == Constraint.Comparison.Equals) {
		return tree.getObjects(val);
	    } 
	}
	return null;
    }

    public ArrayList<ArrayList<Entry>> getAll() {
	int numAttrs = _sym._attrs.size();

	for (int i = 0; i < numAttrs; ++i) {
	    Attribute attr = _sym._attrs.get(i);
	    if (attr.getActualType() != Symbol.Type.BoolType) {
		return _sym._trees.get(i).getAll();
	    }
	}
	return null;
    }

    public ArrayList<ArrayList<Entry>> filter(ArrayList<ArrayList<Entry>> pool, Constraint con) {
	ArrayList<ArrayList<Entry>> newpool = new ArrayList<ArrayList<Entry>>();

	for (ArrayList<Entry> obj : pool) {
	   if (testConstraint(obj, con)) {
	       newpool.add(obj);
	   }
	}

	return newpool;
    }

    public boolean testConstraint(ArrayList<Entry> obj, Constraint con) {
	Attribute attr = con._attr;
	Constraint.Comparison comp = con._comp;
	int index = _sym._atIndex.get(attr._name).intValue();
	Entry val = con._value;
	Entry objval = obj.get(index);

	if (comp == Constraint.Comparison.Equals) {
	    return (objval.equals(val));
	}

	return true;
    }


    @Override
	public QNode visitEvalConstraint(SchemaParser.EvalConstraintContext ctx) { 
	    String attrname = (ctx.ID()).getText();
	    Attribute attr = _sym._attrs.get(_sym._atIndex.get(attrname));
	    Constraint.Comparison comp = visit(ctx.comp())._comp;
	    Entry val = visit(ctx.value())._value;
	    return new QNode(new Constraint(attr, comp, val));
	}

    @Override
	public QNode visitEqualTo(SchemaParser.EqualToContext ctx) {
	    return new QNode(Constraint.Comparison.Equals);
	}

    @Override
	public QNode visitGetFirstConstraint(SchemaParser.GetFirstConstraintContext ctx) {
	    Constraint c = visit(ctx.constraint())._constraint;
	    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	    constraints.add(c);
	    return new QNode(constraints);
	}

    @Override
	public QNode visitGetNextConstraint(SchemaParser.GetNextConstraintContext ctx) {
	    Constraint c = visit(ctx.constraint())._constraint;
	    ArrayList<Constraint> constraints = visit(ctx.constraints())._constraints;
	    constraints.add(c);
	    return new QNode(constraints);
	}

    @Override
	public QNode visitEvalNumberValue(SchemaParser.EvalNumberValueContext ctx) {
	    int number = new Integer((ctx.NUMBER()).getText()).intValue();
	    NumberEntry val = new NumberEntry(number);
	    return new QNode(val);
	}

    @Override
	public QNode visitEvalQStringValue(SchemaParser.EvalQStringValueContext ctx) {
	    String qstr = (ctx.QSTRING()).getText();
	    StringEntry val = new StringEntry(QString.unQuote(qstr));
	    return new QNode(val);
	}

    @Override 
	public QNode visitEvalBoolValue(SchemaParser.EvalBoolValueContext ctx) { 
	    boolean bool = new Boolean((ctx.v).getText()).booleanValue();
	    BoolEntry val = new BoolEntry(bool);
	    return new QNode(val);
	}
}
