//class Interface- interacts with user

import cs1.Keyboard;

public class Interface {

    //main method
    public static void main( String[] args ) {

	/*=========================================
	  =========================================*/
	System.out.println("What is your query?");
	String query = Keyboard.readString();
	Parser parseQuery = new Parser(query);
    }

} //end class
