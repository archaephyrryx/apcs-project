//class Parser- interprets user queries to interface

public class Parser {

    private String [] _query;
    private Database _database;

    /*=====================================
constructor
sets _query to query
=====================================*/
    public Parser(Database data, String input) {
    //input = input.replace("\"","");
        _query = input.split("[ ]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //split user's input at spaces, except when in quotes
        //for (String s : _query) {System.out.println(s);}
        for (int i = 0; i < _query.length; i++) { //remove quotes from any elements of the array containing them
         int len = _query[i].length();
        if (_query[i].substring(0,1).equals("\"") && _query[i].substring(len-1, len).equals("\"")) {
            _query[i] = _query[i].substring(1, len-1);
        }
        }
    //for (String s : _query) {System.out.println(s);}
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
	boolean parseBool = true;
	while (parseBool) {
         if (! ((String)_query[0]).equals("retrieve")) {
                 System.out.println("invalid input: retrieve");
		 break;
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
	    break;
        }
         String databaseName = _query[3];
        
        
        
        if (!_query[4].equals("where") && !_query[4].equals(";")) {
            System.out.println("invalid input: where/;");
	    break;
        }
        
        if (_query[4].equals(";")) {
	    EngineGen.execute(_database, strCol, retCol);
	    break;
        }
       
       else if (_query[4].equals("where")) {
            String colName = _query[5];
            int searchCol = 0;
            for (int i = 0; i < _database.dataTitles.length; i++) {
                if (colName.equals(_database.dataTitles[i])) {
                    searchCol = i;
                    break;
                }
            }
            String operator = _query[6];
            Object searchObj = _query[7];

            if (_database.dataTypes[searchCol].equals("Num")) {
		EngineNum.execute(_database, strCol, retCol, searchCol, operator, searchObj);
		break;
}
            else if (_database.dataTypes[searchCol].equals("String")) {
		EngineString.execute(_database, strCol, retCol, searchCol, operator, searchObj);
		break;
}
        }
        }
        
    }
    
    //prints 1D array (for testing)
    public static void printArray( Object[] arr ) {
        String output = "[ ";

        for( Object o : arr )
         output += o + ", ";

        output = output.substring( 0, output.length()-2 ) + " ]";

        System.out.println( output );
    }

} //end class
