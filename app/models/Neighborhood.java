package models;

import java.util.*;

public class Neighborhood {
	
	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("Commercial");
		all.add("Quite");
		all.add("Party");
		return all;
	}
	
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(String c: Neighborhood.list()) {
            options.put(c, c);
        }
        return options;
    }
	
	public static List<String> optionList(){
		return Neighborhood.list();
	}
}
