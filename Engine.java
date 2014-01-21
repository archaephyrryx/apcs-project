//interface  Engine- runs queries from Parser

import java.util.ArrayList;

public interface Engine<T> {

    ArrayList<T> retrieveAll(Database data);

    int retrieveCount(Database data);

    ArrayList<T> retrieveType(Database data, String type);


} //end interface
