//subclass EngineString

import java.util.ArrayList;

public class EngineString extends Engine<String> {

   public static ArrayList<String> retrieveAll(Database data, int searchCol) {
       ArrayList<String> retList = new ArrayList<String>();
       return retList;
    }

    public static int retrieveCount(Database data, int searchCol) {
	    return 0;
    }

   public static ArrayList<String> retrieveType(Database data, int retCol, int searchCol) {
       ArrayList<String> retList = new ArrayList<String>();
       return retList;
    }
}
