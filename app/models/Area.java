package models;

import java.util.*;


public class Area  {
	
	public static List<String> list(){
		List<String> all = new ArrayList<String>();
		all.add("Cleveland Heights");
		all.add("East Cleveland");
		all.add("Downtown Cleveland");
		all.add("West Cleveland");
		all.add("Asian Town");
		all.add("University Circle");
		all.add("Beachwood area");
		all.add("Other");
		return all;
	}
	
	/*
	 * 	Get list of area for form option
	 */
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(String c: Area.list()) {
            options.put(c, c);
        }
        return options;
    }
	
	/*
	 * 	Get list of area for form option
	 */
	public static List<String> optionList(){
		return Area.list();
	}
}
