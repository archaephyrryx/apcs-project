//class Parser- interprets user queries to interface

public class Parser {

    private String [] _query;
    private Database _database;

    /*=====================================
      constructor
      sets _query to query 
      =====================================*/
    public Parser(Database data, String input) {
	_query = input.split("//s");
	_database = data;
    }

   /*=====================================
      parseQuery() -- parses query and executes functions in Engine 
      =====================================*/
    public void parseQuery() {
	    if (!_query[0].equals("retrieve")) {
		    System.out.println("invalid input");
	    }
	
        String space = _query[1];
        if (!_query[2].equals("from")) {
            System.out.println("invalid input");
        }  
	    String type = _query[3];

        if (space.equals("all")) {Engine.retrieveAll(_database);}
	    else if (space.equals("count")) {Engine.retrieveCount(_database);}
        else {Engine.retrieveType(_database, type);}
        
        if (!_query[4].equals("where")) {
            System.out.println("invalid input");
        }
        
        String colName = _query[5];
        int colOf = 0;
        for (int i = 0; i < _database.dataTitles.length; i++) {
            if (colName.equals(_database.dataTitles[i])) {
                colOf = i;
                break;
            }
        }
        if (_database.dataTypes[colOf].equals("int")) {}
        else if (_database.dataTypes[colOf].equals("String")) {}
    }
} //end class
