package models;

import java.util.*;

public class HouseType  {
	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("Studio");
		all.add("One Bedroom");
		all.add("Two Bedroom");
		all.add("Share Bedroom");
		return all;
	}
	
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(String c: HouseType.list()) {
            options.put(c, c);
        }
        return options;
    }
	
}
