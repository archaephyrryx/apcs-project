import java.util.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {

    public static void main(String[] args) {
        String inputText = "let Book int Year, bool Inprint, string Title;\n";

        ANTLRInputStream input = new ANTLRInputStream(inputText);

        SchemaLexer lexer = new SchemaLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        SchemaParser parser = new SchemaParser(tokens);

        parser.setBuildParseTree(true);  // tell ANTLR to build a parse tree

        ParserRuleContext tree = parser.s();

	Context ctx = new Context();

        FirstSchemaVisitor visitor = new FirstSchemaVisitor(ctx);

        visitor.visit(tree);
    }
}

class Property {
    public String atype;
    public String aname;
}

class Type {
    public String otype;
    public String oname;
    public HashMap<Property> pmap = new HashMap<Property>();
}

class Context {
    public HashMap<Type> tmap = new HashMap<Type>();
    public HashMap<Property> pmap = new HashMap<Property>();
    public Type declared = null;
    public String ptype;

    public Context() { }
}


class FirstSchemaVisitor extends SchemaBaseVisitor<Context> {
    
    private Context _ctx = null;

    public FirstSchemaVisitor( Context ctx ) {
	_ctx = ctx;
    }

    @Override
    public Context visitDeclareProp(SchemaParser.DeclarePropContext ctx) {
        return visit(ctx.e(0))*visit(ctx.e(1));
    }
    
    @Override
    public Double visitDiv(SchemaParser.DivContext ctx) {
        
        System.out.println("DIV: " + ctx.getText());
        
//        return visitChildren(ctx); 
        
        return visit(ctx.e(0))/visit(ctx.e(1));
    }
    
    @Override
    public Double visitAdd(SchemaParser.AddContext ctx) {
        
        System.out.println("ADD: " + ctx.getText());
        
//        return visitChildren(ctx); 
        
        return visit(ctx.e(0))+visit(ctx.e(1));
    }
    
    @Override
    public Double visitSub(SchemaParser.SubContext ctx) {
        
        System.out.println("SUB: " + ctx.getText());
        
//        return visitChildren(ctx); 
        
        return visit(ctx.e(0))-visit(ctx.e(1));
    }
    
    @Override
    public Double visitNumber(SchemaParser.NumberContext ctx) {
        
        System.out.println("NUMBER: " + ctx.getText());
        
//        return visitChildren(ctx); 
        
        return Double.valueOf(ctx.getText());
    }
    
    @Override
    public Double visitInt(SchemaParser.IntContext ctx) {
        
        System.out.println("INT: " + ctx.getText());
        
//        return visitChildren(ctx); 
        
        return Double.valueOf(ctx.getText());
    }
    
    @Override
    public Double visitPar(SchemaParser.ParContext ctx) {
        
        System.out.println("PAR: " + ctx.getText());
        
        return visit(ctx.e());

    }
    
    @Override
    public Double visitVar(SchemaParser.VarContext ctx) {
        System.out.println("VAR: " + ctx.getText());
        
        if (!vars.containsKey(ctx.getText())) {
            System.err.println(
                    "ERROR: variable " + ctx.getText() + " is not defined!");
            System.exit(1);
        }
        
        return vars.get(ctx.getText());
    }
    
    @Override
    public Double visitAssign(SchemaParser.AssignContext ctx) {
        System.out.println("VAR: " + ctx.getText());
        
        Double value = visit(ctx.e());
        
        vars.put(ctx.VAR().getText(), value);
        
        System.out.println(" --> " + ctx.VAR().getText() + " = " + value);
        
        return value;
    }

}
