//superclass Engine- runs queries from Parser

import java.util.ArrayList;

public class Engine<T> {
    
    public static void execute(Database data, String retType, int retCol, int searchCol) {
        if (retType.equals("all")) {retrieveAll(data, searchCol);}
	    else if (retType.equals("count")) {retrieveCount(data, searchCol);}
        else {retrieveType(data, retCol, searchCol);}
    }


    public static ArrayList retrieveAll(Database data, int searchCol) {
        ArrayList retList = new ArrayList();
        for (int i = 2; i < data.getData().length; i++) {
            retList.add(data.getData()[i]);
        }
        return retList;
    }

    public static int retrieveCount(Database data, int searchCol) {
        System.out.println(data.getData().length-2);
	    return data.getData().length;
    }

    public static ArrayList retrieveType(Database data, int retCol, int searchCol) {
        ArrayList retList = new ArrayList();
        for (int i = 2; i < data.getData().length; i++) {
            retList.add((String)data.getData()[i][retCol]);
        }
        System.out.println(retList);
        return retList;
    }


} //end superclass
