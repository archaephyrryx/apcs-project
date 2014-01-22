//subclass EngineNum

import java.util.ArrayList;

public abstract class EngineNum extends Engine implements Comparable {

    public int compareTo() {    
        return 0; //placeholder
    }

    public static ArrayList<Comparable> retrieveAll(Database data, int searchCol) {
       ArrayList<Comparable> retList = new ArrayList<Comparable>();
       return retList;
    }

   public static int retrieveCount(Database data, int searchCol) {
	   return 0;
   }

   public static ArrayList<Comparable> retrieveType(Database data, int retCol, int searchCol) {
       ArrayList<Comparable> retList = new ArrayList<Comparable>();
       return retList;
   }
}
