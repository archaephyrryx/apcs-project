//class EngineString- runs queries from Parser involving exact matches of Strings 

import java.util.ArrayList;

public class EngineString {


   public static void execute(Database data, String retType, int retCol, int searchCol, String operator, Object searchObj) {
        String searchStr = (String)searchObj;
        if (retType.equals("all")) {retrieveAll(data, searchCol, operator, searchStr);}
        else if (retType.equals("count")) {retrieveCount(data, searchCol, operator, searchStr);}
        else {retrieveType(data, retCol, searchCol, operator, searchStr);}
    }

    //retRow: returns row of array as comma-separated String
    public static String retRow( Object [][] a, int r) {
        String retStr = (String)a[r][0];
        for (int c=1; c < a[0].length; c++) {
         retStr = retStr + ", " + (String)a[r][c];
        }
        return retStr;
    }
    
    public static boolean op(String s, String first, String second) {
        if (s.equals("=")) {return (first.equals(second));}
        else {return (!first.equals(second));} //if (s.equals("!="))
    }   

   public static ArrayList<String> retrieveAll(Database data, int searchCol, String operator, String searchStr) {
       ArrayList<String> retList = new ArrayList<String>();
	   for (int i = 2; i < data.getData().length; i++) {
	       if (op(operator, (String)data.getData()[i][searchCol], searchStr)) {
		    retList.add(retRow(data.getData(), i));
	       }
       }
       System.out.println(retList);
       return retList;
    }

   public static int retrieveCount(Database data, int searchCol, String operator, String searchStr) {
        int count = 0;
       	for (int i = 2; i < data.getData().length; i++) {
	       if (op(operator, (String)data.getData()[i][searchCol], searchStr)) {count ++;}
        }
        System.out.println(count);
        return count;
    }

   public static ArrayList<String> retrieveType(Database data, int retCol, int searchCol, String operator, String searchStr) {
       ArrayList<String> retList = new ArrayList<String>();
       for (int i = 2; i < data.getData().length; i++) {
           if (op(operator, (String)data.getData()[i][searchCol], searchStr)) {
         retList.add((String)data.getData()[i][retCol]);
         }
       }
       System.out.println(retList);
       return retList;
    }
} //end class
