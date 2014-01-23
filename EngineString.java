//class EngineString

import java.util.ArrayList;

public class EngineString extends Engine<String> {

   public static void execute(Database data, String retType, int retCol, int searchCol, String operator, Object searchObj) {
        if (retType.equals("all")) {retrieveAll(data, searchCol, operator, searchObj);}
        else if (retType.equals("count")) {retrieveCount(data, searchCol, operator, searchObj);}
        else {retrieveType(data, retCol, searchCol, operator, searchObj);}
    }

   public static ArrayList<String> retrieveAll(Database data, int searchCol, String operator, Object searchObj) {
       ArrayList<String> retList = new ArrayList<String>();
       for (int i = 2; i < data.getData().length; i++) {
           if (data.getData()[i][searchCol].equals((String)searchObj)) {
	       retList.add(retRow(data.getData(), i));
	       }
       }
       System.out.println(retList);
       return retList;
    }

   public static int retrieveCount(Database data, int searchCol, String operator, Object searchObj) {
	int count = 0;
	for (int i = 2; i < data.getData().length; i++) {
	    if (data.getData()[i][searchCol].equals((String)searchObj)) {count ++;}
	}
	System.out.println(count);
	return count;
    }

   public static ArrayList<String> retrieveType(Database data, int retCol, int searchCol, String operator, Object searchObj) {
       ArrayList<String> retList = new ArrayList<String>();
       for (int i = 2; i < data.getData().length; i++) {
           if (data.getData()[i][searchCol].equals((String)searchObj)) {
	       retList.add((String)data.getData()[i][retCol]);
	       }
       }
       System.out.println(searchObj);
       System.out.println(retList);
       return retList;
    }
}
