//class Database- stores database from txt file in 2D array

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Database {
public Object [] [] _database;
public Object [] dataTitles;
public Object [] dataTypes;

    /*constructor
    /precondition: row matches the number of rows in the text file,
                    col matches the number of columns in the text file
    */
    public Database (String fileName, int row, int col) {
        _database = new Object [row][col];
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line;
            int n = 0;
            while ((line = in.readLine()) != null) {
                	Object [] dataArr = line.split(",");
                	for (int i = 0; i < dataArr.length; i++) {
                	    _database [n][i] = dataArr[i];           	    
                	}
            n++;            
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        dataTitles = new Object [col];
	dataTypes = new Object [col];
        for (int i =0; i < col; i++) {
            dataTitles[i] = _database [0][i];
            dataTypes[i] = _database [1][i];
        }
    }
    
    //postcond: prints each row of 2D integer array a on its own line
    public static void printArr( Object[][] a ) {
	    for (int r=0; r < a.length; r++) {
	        for (int c = 0; c < a[r].length; c++ ) {
		        System.out.print(a[r][c] + " ");
		    }
		    System.out.println();
	    }
    }
    
    public static void main (String [] args) {
        Database sample = new Database("sampleData.txt", 12, 4);
        printArr(sample._database);
    }

}
