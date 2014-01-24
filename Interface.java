//class Interface- interacts with user

import cs1.Keyboard;

public class Interface {
             
    /*sample queries to run:
    (to run all at once, enter in command line: cat queries.txt | java Interface)
    Using EngineGen:
    retrieve all from Book ;
    retrieve count from Book ;
    retrieve Album from Song ;
    
    Using EngineString:
    retrieve all from Book where Genre = "Short Story" ;
    retrieve count from Book where Author != "Jose Luis Borges" ;
    retrieve Artist from Song where Genre = Pop ;

    Using EngineNum:
    retrieve all from Song where Time >= 4.12 ;
    retrieve count from Book where Year = 1941 ;
    retrieve Title from Song where Time < 3.5 ;
    */

    //main method: interacts with user
    public static void main( String[] args ) {
        
        boolean newQuery = true;
        //loop as long as user wants to make queries
        while (newQuery) {
        
            //user enters query
            System.out.println("What is your query? (Available databases: Book, Song. Enter q to quit.)");
            String query = Keyboard.readString();
            if (query.equals("q")) {break;}
            else {
                
                //parse and execute query
                Parser userQuery = new Parser(query);
                
                //test Parser
                //sampleQuery.printArray(sampleQuery.getQuery());
                //System.out.println(sampleQuery.getQuery()[4]);
                        
                userQuery.parseQuery();
            }
        }
    } //end main method



} //end class
