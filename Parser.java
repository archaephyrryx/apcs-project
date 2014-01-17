//class Parser- interprets user queries to interface

public class Parser {

    private String [] _query;

    /*=====================================
      constructor
      sets _query to query 
      =====================================*/
    public Parser(String input) {
	_query = input.split("//s");
    }

   /*=====================================
      parseQuery() -- parses query and executes functions in Engine 
      =====================================*/
    public void parseQuery() {
	if (!_query[0].equals("query:")) {
		System.out.println("invalid input");
	}
        String space = _query[2];
   	space = space.substring(1, space.length()-1);

	String type = _query[3];

      	if (space.equals("all")) {Engine.retrieveAll(type);}
	else if (space.equals("any")) {Engine.retrieveAny(type);}
        else if (space.equals("just")) {Engine.retrieveJust(type, Integer.parseInt( _query[4]));}
       	else if (space.equals("only")) {Engine.retrieveOnly(type, Integer.parseInt(_query[4]));}
    }

} //end class