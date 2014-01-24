//class EngineGen- runs general queries from Parser (i.e. no "where" clause)

import java.util.ArrayList;

public class EngineGen {
    
    //execute(): chooses method to carry out given what user wants returned
    public static void execute(Database data, String retType, int retCol) {
        if (retType.equals("all")) {retrieveAll(data);}
        else if (retType.equals("count")) {retrieveCount(data);}
        else {retrieveType(data, retCol);} 
    }

    //retRow(): returns row of array as comma-separated String
    public static String retRow( Object [][] a, int r) {
        String retStr = (String)a[r][0];
        for (int c=1; c < a[0].length; c++) {
         retStr = retStr + ", " + (String)a[r][c];
        }
        return retStr;
    }

    //retrieveAll(): returns all information from each row in the database
    public static ArrayList<String> retrieveAll(Database data) {
        ArrayList<String> retList = new ArrayList<String>();
        for (int i = 2; i < data._data.length; i++) {
            retList.add(retRow(data._data, i));
        }
       for (String s : retList) {System.out.println(s);}
        return retList;
    }

    //retrieveCount(): retrieves the number of rows in the database
    //(excludes the first two rows containing column titles & types)
    public static int retrieveCount(Database data) {
        System.out.println(data._data.length-2);
         return data._data.length;
    }

    //retrieveType(): retrieves all rows under a given column title in the database
    public static ArrayList<String> retrieveType(Database data, int retCol) {
        ArrayList<String> retList = new ArrayList<String>();
        for (int i = 2; i < data._data.length; i++) {
            retList.add((String)data._data[i][retCol]);
        }
       for (String s : retList) {System.out.println(s);}
        return retList;
    }

} //end class
