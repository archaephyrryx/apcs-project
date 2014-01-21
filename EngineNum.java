import java.util.ArrayList;

public abstract class EngineNum implements Engine, Comparable {

    public int compareTo() {return 0; //placeholder
    }

    public ArrayList<Comparable> retrieveAll(Database data) {
       ArrayList<Comparable> retList = new ArrayList<Comparable>();
       return retList;
    }

   public int retrieveCount(Database data) {
	   return 0;
   }

   public ArrayList<Comparable> retrieveType(Database data, String type) {
       ArrayList<Comparable> retList = new ArrayList<Comparable>();
       return retList;
   }
}