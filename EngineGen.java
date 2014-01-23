//class Engine- runs general queries from Parser

import java.util.ArrayList;

public class EngineGen {
    
    public static void execute(Database data, String retType, int retCol, int searchCol, String operator, Object searchObj) {
        if (retType.equals("all")) {retrieveAll(data, searchCol, operator, searchObj);}
        else if (retType.equals("count")) {retrieveCount(data, searchCol, operator, searchObj);}
        else {retrieveType(data, retCol, searchCol, operator, searchObj);}
    }

    //retRow: returns row of array as String
    public static String retRow( Object [][] a, int r) {
	String retStr = (String)a[r][0];
	for (int c=1; c < a[0].length; c++) {
	    retStr = retStr + ", " + (String)a[r][c];
	}
	return retStr;
    }


    public static ArrayList<String> retrieveAll(Database data, int searchCol, String operator, Object searchObj) {
        ArrayList<String> retList = new ArrayList<String>();
        for (int i = 2; i < data.getData().length; i++) {
            retList.add(retRow(data.getData(), i));
        }
        System.out.println(retList);
        return retList;
    }

    public static int retrieveCount(Database data, int searchCol, String operator, Object searchObj) {
        System.out.println(data.getData().length-2);
	    return data.getData().length;
    }

    public static ArrayList retrieveType(Database data, int retCol, int searchCol, String operator, Object searchObj) {
        ArrayList retList = new ArrayList();
        for (int i = 2; i < data.getData().length; i++) {
            retList.add((String)data.getData()[i][retCol]);
        }
        System.out.println(retList);
        return retList;
    }


} //end class
