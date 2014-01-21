import java.util.ArrayList;

public class EngineString implements Engine<String> {

   public ArrayList<String> retrieveAll(Database data) {
       ArrayList<String> retList = new ArrayList<String>();
       return retList;
    }

   public int retrieveCount(Database data) {
	return 0;
    }

   public ArrayList<String> retrieveType(Database data, String type) {
       ArrayList<String> retList = new ArrayList<String>();
       return retList;
    }
}