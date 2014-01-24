//class EngineNum- runs queries from Parser involving comparison of numbers

import java.util.ArrayList;

public class EngineNum {

    //execute(): chooses method to carry out given what user wants returned
    public static void execute(Database data, String retType, int retCol, int searchCol, String operator, Object searchObj) {
        Double searchNum = new Double((String)searchObj);
        if (retType.equals("all")) {retrieveAll(data, searchCol, operator, searchNum);}
        else if (retType.equals("count")) {retrieveCount(data, searchCol, operator, searchNum);}
        else {retrieveType(data, retCol, searchCol, operator, searchNum);}
    }

    //retRow: returns row of array as comma-separated String
    public static String retRow( Object [][] a, int r) {
        String retStr = (String)a[r][0];
        for (int c=1; c < a[0].length; c++) {
         retStr = retStr + ", " + (String)a[r][c];
        }
        return retStr;
    } 
    
    //op(): parses comparison operators
    public static boolean op(String s, Double first, Double second) {
        if (s.equals("=")) {return (first.equals(second));} //use equals method to avoid problems with equality of Doubles
        else if (s.equals("<")) {return (first < second);}
        else if (s.equals(">")) {return (first > second);}
        else if (s.equals("<=")) {return (first <= second);}
        else if (s.equals(">=")) {return (first >= second);}
        else {return (!first.equals(second));} //if (s.equals("!=")) 
    } 
      
    //retrieveAll(): returns all information from each row in the database that meets the specified condition
    public static ArrayList<String> retrieveAll(Database data, int searchCol, String operator, Double searchNum) {
       ArrayList<String> retList = new ArrayList<String>();
       for (int i=2; i < data._data.length; i++) {
	       if (op(operator, new Double((String)data._data[i][searchCol]), searchNum)) {
               retList.add(retRow(data._data, i));
           }
       }
       for (String s : retList) {System.out.println(s);}
       return retList;
    }
    
   //retrieveCount(): retrieves the number of rows in the database that meet the specified condition
   public static int retrieveCount(Database data, int searchCol, String operator, Double searchNum) {
       int count = 0;
	   for (int i=2; i < data._data.length; i++) {
	       if (op(operator, new Double((String)data._data[i][searchCol]), searchNum)) {
		   count ++;
	       }
	
       }
       System.out.println(count);
       return count;
   }

   //retrieveType(): retrieves all rows under a given column title in the database that meet the specified condition
   public static ArrayList<String> retrieveType(Database data, int retCol, int searchCol, String operator, Double searchNum) {
       ArrayList<String> retList = new ArrayList<String>();
	   for (int i=2; i < data._data.length; i++) {
	       if (op(operator, new Double((String)data._data[i][searchCol]), searchNum)) {
               retList.add((String)data._data[i][retCol]);
           }
       }
       for (String s : retList) {System.out.println(s);}
       return retList;
   }
} //end class
