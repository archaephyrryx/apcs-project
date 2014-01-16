//class Parser- interprets user queries to interface

public class Parser {

    private String [] _input;

    /*=====================================
      constructor
      sets _query to query 
      =====================================*/
    public Parser(String input) {
	_input = input;
    }

   /*=====================================
      parseQuery() -- parses query and executes functions in Engine 
      =====================================*/
    public static void parseQuery() {
	String [] _query = _input.split("//s");
	if (!_query[0].equals("query:")) {
		System.out.println("invalid input");
	}
        String space = _query[2];
   	space = space.substring(1, space.length()-1);

	String type = _query[3];

      	if (space.equals("all")) {Engine.retrieveAll(type);}
	else if (space.equals("any")) {Engine.retrieveAny(type);}
        else if (space.equals("just")) {Engine.retrieveJust(type);}
       	else if (space.equals("only")) {Engine.retrieveOnly(type);}
    }

} //end class