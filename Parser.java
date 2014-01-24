//class Parser- interprets user queries from interface

public class Parser {

    private String [] _query;
    private Database _database;
    Database Book = new Database("bookData.txt", 12, 4);
    Database Song = new Database("songData.txt", 12, 5);

    //constructor: stores user query in String array
    public Parser(String input) {
    
        //split user's input at spaces, except when in quotes
        _query = input.split("[ ]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); 
        
        //remove quotes from any elements of the array containing them
        for (int i = 0; i < _query.length; i++) { 
            int len = _query[i].length();
            if (_query[i].substring(0,1).equals("\"") && _query[i].substring(len-1, len).equals("\"")) {
                _query[i] = _query[i].substring(1, len-1);
            }
        }
        
        //set _database to third element of _query
        if (_query[3].equals("Book")) {_database = Book;}
        else if (_query[3].equals("Song")) {_database = Song;}
    }
    
    
    //getQuery(): accessor method (for testing)
    public String [] getQuery() {
        return _query;
    }


    //parseQuery(): parses query and executes functions in Engine
    public void parseQuery() {
	    boolean parseBool = true;
	    //continue parsing until end of query, denoted by ";"
	    while (parseBool) {
	
	        //zeroeth element must be "retrieve"
            if (! ((String)_query[0]).equals("retrieve")) {
                System.out.println("invalid query");
		    break;
            }
            
            //first element indicates what user wants returned: "all", "count", or <columnTitle>
            String strCol = (String)_query[1];
            
            //if user inputs column title, find associated column number
            int retCol = -1;
            if (!strCol.equals("all") && !strCol.equals("count")) {
                for (int i = 0; i < _database._dataTitles.length; i++) {
                    if (strCol.equals(_database._dataTitles[i])) {
                        retCol = i;
                        break;
                    }
                }
            }
            
            //second element must be "from"
            if (!_query[2].equals("from")) {
                System.out.println("invalid query");
	            break;
            }
            
            //third element indicates database name (taken care of in constructor)
            //fourth element must be "where" or ";"
            if (!_query[4].equals("where") && !_query[4].equals(";")) {
                System.out.println("invalid query");
	            break;
            }
            
            //if fourth element is ";", end query
            if (_query[4].equals(";")) {
	            EngineGen.execute(_database, strCol, retCol);
	            break;
            }
           
           //if fourth element is "where", continue query
           else if (_query[4].equals("where")) {
                //set searchCol to the column number associated with the column title in the user's query
                String colName = _query[5];
                int searchCol = 0;
                for (int i = 0; i < _database._dataTitles.length; i++) {
                    if (colName.equals(_database._dataTitles[i])) {
                        searchCol = i;
                        break;
                    }
                }
                
                //String operator is "=", "!=", "<", ">", "<=", or ">="
                String operator = _query[6];
                
                //searchObj is the string/number/etc. the user is interested in
                Object searchObj = _query[7];

                //if the query deals with numbers(ints, doubles, etc.), use EngineNum
                if (_database._dataTypes[searchCol].equals("Num") && _query[8].equals(";")) {
		            EngineNum.execute(_database, strCol, retCol, searchCol, operator, searchObj);
		            break;
                }
                
                //if the query deals with Strings, use EngineString
                else if (_database._dataTypes[searchCol].equals("String") && _query[8].equals(";")) {
		            EngineString.execute(_database, strCol, retCol, searchCol, operator, searchObj);
		            break;
		        }
            }
            //break while loop in case of errors
            break;
        } //end while loop       
    } //end parseQuery()
    
    
    //printArray(): prints 1D array (for testing)
    public static void printArray( Object[] arr ) {
        String output = "[ ";
        for( Object o : arr ) {output += o + ", ";}
        output = output.substring( 0, output.length()-2 ) + " ]";
        System.out.println( output );
    }

} //end class
