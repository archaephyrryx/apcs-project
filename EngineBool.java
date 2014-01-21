import java.util.ArrayList;

public class EngineBool implements Engine<Boolean> {

    public ArrayList<Boolean> retrieveAll(Database data) {
       ArrayList<Boolean> retList = new ArrayList<Boolean>();
       return retList;
    }

   public int retrieveCount(Database data) {
	   return 0;
   }

   public ArrayList<Boolean> retrieveType(Database data, String type) {
       ArrayList<Boolean> retList = new ArrayList<Boolean>();
       return retList;
   }
}