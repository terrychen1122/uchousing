package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Neighborhood extends Model {
	
	@Id
	public Long id;
	
	@Constraints.Required
	public String neigbor_type;
	
	public static Finder<Long, Neighborhood> find = new Finder<Long, Neighborhood>(Long.class, Neighborhood.class);
	
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
	
}
