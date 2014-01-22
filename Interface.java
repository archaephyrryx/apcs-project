//class Interface- interacts with user

import cs1.Keyboard;

public class Interface {

    //main method
    public static void main( String[] args ) {
    Database sample = new Database("sampleData.txt", 12, 4);
	System.out.println("What is your query?");
	String query = Keyboard.readString();
	
	Parser sampleQuery = new Parser(sample, query);	
	sampleQuery.printArray(sampleQuery.getQuery());
	System.out.println(sampleQuery.getQuery()[4]);
	sampleQuery.parseQuery();
	
	/*sample queries to run:
	retrieve count from sample ;
	retrieve Title from sample ;
	retrieve Year from sample ;
	*/
    }

} //end class
