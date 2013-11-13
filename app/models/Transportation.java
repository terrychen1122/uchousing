package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Transportation extends Model {
	
	@Id
	public Long id;
	
	@Constraints.Required
	public String line;
	
	public static Finder<Long, Transportation> find = new Finder<Long, Transportation>(Long.class, Transportation.class);
	
	public static List<String> list() {
		List<String> all = new ArrayList<String>();
		all.add("Healthline");
		all.add("32");
		all.add("1");
		return all;
	}
	
	public static Transportation create(String trans){
		Transportation t = new Transportation();
		t.line = trans;
		t.save();
		return t;
	}
	
}
