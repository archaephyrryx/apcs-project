import java.util.*;

public class Query {

}


class Constraint {
    public enum ValueType { Number, Bool, String };
    public ValueType _type;
    public int _numberValue;
    public boolean _boolValue;
    public String _strValue;

    public Constraint(ValueType type, int numberValue) {
	_type = type;
	_numberValue = numberValue;
    }

    public Constraint(ValueType type, boolean boolValue) {
	_type = type;
	_boolValue = boolValue;
    }

    public Constraint(ValueType type, String strValue) {
	_type = type;
	_strValue = strValue;
    }
}

class NumberConstraint extends Constraint {
    public static final ValueType _type = Constraint.ValueType.Number;
    public int _numberValue;

    public NumberConstraint(int numberValue) {
	super(_type, numberValue);
	this._numberValue = numberValue;
    }
}

class BoolConstraint extends Constraint {
    public static final ValueType _type = Constraint.ValueType.Bool;
    public boolean _boolValue;

    public BoolConstraint(boolean boolValue) {
	super(_type, boolValue);
	this._boolValue = boolValue;
    }
}

class StringConstraint extends Constraint {
    public static final ValueType _type = Constraint.ValueType.String;
    public String _strValue;

    public StringConstraint(String strValue) {
	super(_type, strValue);
	this._strValue = strValue;
    }
}

class ConstraintNode {
    public Constraint _constraint;
    public ArrayList<Constraint> _constraints;
    public AtomSymbol _atomsym;

    public ConstraintNode(Constraint constraint) { _constraint = constraint; }
    public ConstraintNode(ArrayList<Constraint> constraints) { _constraints = constraints; }
    public ConstraintNode(AtomSymbol atomsym) { _atomsym = atomsym; }
}


class QueryVisitor extends SchemaBaseVisitor<ConstraintNode> {
    public CompoundSymbol _sym;

    public QueryVisitor( CompoundSymbol sym ) {
	_sym = sym;
    }

    @Override
	public ConstraintNode visitEvalMatch(SchemaParser.EvalMatchContext ctx) { 
	    String attrname = (ctx.ID()).getText();
	    int index = _sym._atIndex.get(attrname).intValue();
	    Attribute attr = _sym._attrs.get(index);
	    Constraint con = visit(ctx.ID()).getText();
	}

    @Override
	public ConstraintNode visitPerformQuery(SchemaParser.PerformQueryContext ctx) {
	}

    @Override
	public ConstraintNode visitGetsym(SchemaParser.GetsymContext ctx) {
	}

    @Override
	public ConstraintNode visitEqualTo(SchemaParser.EqualToContext ctx) {
	}

    @Override
	public ConstraintNode visitGetFirstMatch(SchemaParser.GetFirstMatchContext ctx) {
	    Constraint c = visit(ctx.match())._constraint;
	    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	    constraints.add(c);
	    return new ConstraintNode(constraints);
	}

    @Override
	public ConstraintNode visitGetNextMatch(SchemaParser.GetNextMatchContext ctx) {
	    Constraint c = visit(ctx.match())._constraint;
	    ArrayList<Constraint> constraints = visit(ctx.matchlist())._constraints;
	    constraints.add(c);
	    return new ConstraintNode(constraints);
	}

    @Override
	public ConstraintNode visitEvalNumberValue(SchemaParser.EvalNumerValueContext ctx) {
	    int number = new Integer((ctx.NUMBER()).getText()).intValue();
	    NumberConstraint constraint = new NumberConstraint(number);
	    return new ConstraintNode(constraint);
	}

    @Override
	public ConstraintNode visitEvalQStringValue(SchemaParser.EvalQStringValueContext ctx) {
	    String qstr = (ctx.QSTRING()).getText();
	    StringConstraint constraint = new StringConstraint(QString.unQuote(qstr));
	    return new ConstraintNode(constraint);

	}

    @Override 
	public ConstraintNode visitEvalBoolValue(SchemaParser.EvalBoolValueContext ctx) { 
	    boolean bool = new Boolean((ctx.v).getText()).booleanValue();
	    BoolConstraint constraint = new BoolConstraint(bool);
	    return new ConstraintNode(constraint);
	}
}
