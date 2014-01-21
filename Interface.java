//class Interface- interacts with user

import cs1.Keyboard;

public class Interface {

    //main method
    public static void main( String[] args ) {
    Database sample = new Database("sampleData.txt", 12, 4);
	System.out.println("What is your query?");
	String query = Keyboard.readString();
	Parser parseQuery = new Parser(sample, query);
    }

} //end class
