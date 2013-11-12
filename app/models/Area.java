package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Area extends Model {
//	public static List<String> list() {
//		List<String> all = new ArrayList<String>();
//		all.add("Cleveland Heights");
//		return all;
//	}
	
	@Id
	@Constraints.Required
	public Long id;
	
	@Constraints.Required
	public String area;
	
	public static Finder<Long, Area> find = new Finder<Long, Area>(Long.class, Area.class);
	
	public static List<String> list(){
		List<String> all = new ArrayList<String>();
		all.add("Cleveland Heights");
		return all;
	}
}
