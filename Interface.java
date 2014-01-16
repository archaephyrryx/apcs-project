//class Interface- interacts with user

public class Interface {

    //main method
    public static void main( String[] args ) {

	/*=========================================
	  =========================================*/
	System.out.println("What is your query?");
	String query = keyboard.readString();
	parseQuery = new Parser(query);
    }

} //end class