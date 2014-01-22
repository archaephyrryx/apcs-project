//class Parser- interprets user queries to interface

public class Parser {

    private String [] _query;
    private Database _database;

    /*=====================================
      constructor
      sets _query to query 
      =====================================*/
    public Parser(Database data, String input) {
	_query = input.split(" ");
	_database = data;
    }
    
    //accessor method (for testing)
    public String [] getQuery() {
        return _query;
    }

   /*=====================================
      parseQuery() -- parses query and executes functions in Engine 
      =====================================*/
    public void parseQuery() {
	    if (! ((String)_query[0]).equals("retrieve")) {
		    System.out.println("invalid input: retrieve");
	    }
	
        String strCol = (String)_query[1];
        int retCol = -1;
        for (int i = 0; i < _database.dataTitles.length; i++) {
            if (strCol.equals(_database.dataTitles[i])) {
                retCol = i;
                break;
            }
        }
        
        if (!_query[2].equals("from")) {
            System.out.println("invalid input: from");
        }  
	    String databaseName = _query[3];
	    
	    
        
        if (!_query[4].equals("where") && !_query[4].equals(";")) {
            System.out.println("invalid input: where/;");
        }
        
        if (_query[4].equals(";")) {
            Engine.execute(_database, strCol, retCol, -1);
        }
       
       //to be implemented 
       /*else if (_query[4].equals("where")) { 
            String colName = _query[5];
            int searchCol = 0;
            for (int i = 0; i < _database.dataTitles.length; i++) {
                if (colName.equals(_database.dataTitles[i])) {
                    searchCol = i;
                    break;
                }
            }
            if (_database.dataTypes[searchCol].equals("int")) {EngineNum.execute(_database, strCol, retCol, searchCol);}
            else if (_database.dataTypes[searchCol].equals("String")) {EngineString.execute(_database, strCol, retCol, searchCol);}
            else if (_database.dataTypes[searchCol].equals("boolean")) {EngineBool.execute(_database, strCol, retCol, searchCol);}
        }
        */
    }
    
    
        // utility/helper fxn to display contents of an array of Objects
        
    //prints 1D array (for testing)    
    public static void printArray( Object[] arr ) {
	String output = "[ "; 

	for( Object o : arr )
	    output += o + ", ";

	output = output.substring( 0, output.length()-2 ) + " ]";

	System.out.println( output );
    }

} //end class
