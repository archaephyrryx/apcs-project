//class EngineNum- runs queries from Parser involving comparison of numbers

import java.util.ArrayList;

public class EngineNum {

    public static void execute(Database data, String retType, int retCol, int searchCol, String operator, Object searchObj) {
        Double searchNum = new Double((String)searchObj);
        if (retType.equals("all")) {retrieveAll(data, searchCol, operator, searchNum);}
        else if (retType.equals("count")) {retrieveCount(data, searchCol, operator, searchNum);}
        else {retrieveType(data, retCol, searchCol, operator, searchNum);}
    }

    //retRow: returns row of array as String
    public static String retRow( Object [][] a, int r) {
        String retStr = (String)a[r][0];
        for (int c=1; c < a[0].length; c++) {
         retStr = retStr + ", " + (String)a[r][c];
        }
        return retStr;
    } 
    
    public static boolean op(String s, Double first, Double second) {
        if (s.equals("=")) {return (first == second);}
        else if (s.equals("<")) {return (first < second);}
        else if (s.equals(">")) {return (first > second);}
        else if (s.equals("<=")) {return (first <= second);}
        else if (s.equals(">=")) {return (first >= second);}
        else {return (first != second);} //if (s.equals("!="))
    }   

    public static ArrayList<String> retrieveAll(Database data, int searchCol, String operator, Double searchNum) {
       ArrayList<String> retList = new ArrayList<String>();
       for (int i=2; i < data.getData().length; i++) {
	       if (op(operator, new Double((String)data.getData()[i][searchCol]), searchNum)) {
               retList.add(retRow(data.getData(), i));
           }
       }
       System.out.println(retList);
       return retList;
    }

   public static int retrieveCount(Database data, int searchCol, String operator, Double searchNum) {
       int count = 0;
	   for (int i=2; i < data.getData().length; i++) {
	       if (op(operator, new Double((String)data.getData()[i][searchCol]), searchNum)) {
		   count ++;
	       }
	
       }
       System.out.println(count);
       return count;
   }

   public static ArrayList<Comparable> retrieveType(Database data, int retCol, int searchCol, String operator, Double searchNum) {
       ArrayList<Comparable> retList = new ArrayList<Comparable>();
	   for (int i=2; i < data.getData().length; i++) {
	       if (op(operator, new Double((String)data.getData()[i][searchCol]), searchNum)) {
               retList.add((String)data.getData()[i][retCol]);
           }
       }
       System.out.println(retList);
       return retList;
   }
} //end class
