//class EngineNum

import java.util.ArrayList;

public abstract class EngineNum implements Comparable {

    public static void execute(Database data, String retType, int retCol, int searchCol, String operator, Object searchObj) {
        if (retType.equals("all")) {retrieveAll(data, searchCol, operator, searchObj);}
        else if (retType.equals("count")) {retrieveCount(data, searchCol, operator, searchObj);}
        else {retrieveType(data, retCol, searchCol, operator, searchObj);}
    }
    
    public int compareTo() {    
        return 0; //placeholder
    }

    public static ArrayList<String> retrieveAll(Database data, int searchCol, String operator, Object searchObj) {
       ArrayList<String> retList = new ArrayList<String>();
       return retList;
    }

   public static int retrieveCount(Database data, int searchCol, String operator, Object searchObj) {
	   return 0;
   }

   public static ArrayList<Comparable> retrieveType(Database data, int retCol, int searchCol, String operator, Object searchObj) {
       ArrayList<Comparable> retList = new ArrayList<Comparable>();
       return retList;
   }
}
