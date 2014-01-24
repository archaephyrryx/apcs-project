//class Database- stores database from txt file in 2D array

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Database {
protected Object [] [] _data; //store database in 2D array
protected Object [] _dataTitles;  //store column titles (e.g. Title, Year)
protected Object [] _dataTypes;  //store column types (e.g. String, Num)

    /*constructor
    /precondition: row matches the number of rows in the text file,
                    col matches the number of columns in the text file
    */
    public Database (String fileName, int row, int col) {
        _data = new Object [row][col];
        //read in comma-separated txt file
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line;
            int n = 0;
            while ((line = in.readLine()) != null) {
                	Object [] dataArr = line.split(",");
                	for (int i = 0; i < dataArr.length; i++) {
                	    _data [n][i] = dataArr[i];           	    
                	}
            n++;            
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        _dataTitles = new Object [col];
	    _dataTypes = new Object [col];
        for (int i =0; i < col; i++) {
            _dataTitles[i] = _data [0][i]; //column titles in first row of database
            _dataTypes[i] = _data [1][i]; //column types in second row of database
        }
    } 
    
    //printArr(): prints each row of 2D array a on its own line (for testing)
    public static void printArr( Object[][] a ) {
	    for (int r=0; r < a.length; r++) {
	        for (int c = 0; c < a[r].length; c++ ) {
		        System.out.print(a[r][c] + " ");
		    }
		    System.out.println();
	    }
    }
      
    //main method for testing
    public static void main (String [] args) {
        Database sample = new Database("sampleData.txt", 12, 4);
        printArr(sample._data);
    }

} //end class
