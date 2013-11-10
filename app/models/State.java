package models;

import java.util.*;

public class State  {
	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("AL");
		all.add("AK");
		all.add("AZ");
		all.add("AR");
		all.add("CA");
		all.add("CO");
		all.add("CT");
		all.add("DE");
		all.add("DC");
		all.add("FL");
		all.add("GA");
		all.add("HI");
		all.add("ID");
		all.add("IL");
		all.add("IN");
		all.add("IA");
		all.add("KS");
		all.add("KY");
		all.add("LA");
		all.add("ME");
		all.add("MD");
		all.add("MA");
		all.add("MI");
		all.add("MN");
		all.add("MS");
		all.add("MO");
		all.add("MT");
		all.add("NE");
		all.add("NV");
		all.add("NH");
		all.add("NJ");
		all.add("NM");
		all.add("NY");
		all.add("NC");
		all.add("ND");
		all.add("OH");
		all.add("OK");
		all.add("OR");
		all.add("PA");
		all.add("RI");
		all.add("SC");
		all.add("SD");
		all.add("TN");
		all.add("TX");
		all.add("UT");
		all.add("VT");
		all.add("VA");
		all.add("WA");
		all.add("WV");
		all.add("WI");
		all.add("WY");
		return all;
	}
	
	public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(String c: State.list()) {
            options.put(c, c);
        }
        return options;
    }
	
	public static List<String> optionList(){
		return State.list();
	}
}
