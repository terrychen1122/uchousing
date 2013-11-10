package models;

import java.util.*;

public class Service  {
	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("water");
		all.add("gas");
		all.add("heat");
		all.add("internet");
		all.add("fitness");
		all.add("laundry");
		all.add("parking");
		return all;
	}
	
}
