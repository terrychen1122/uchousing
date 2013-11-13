package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Service extends Model{
//	public static List<String> list() {
//		List<String> all = new ArrayList<String>();
//		all.add("water");
//		all.add("gas");
//		all.add("heat");
//		all.add("internet");
//		all.add("fitness");
//		all.add("laundry");
//		all.add("parking");
//		return all;
//	}
	@Id
	@Constraints.Required
	public Long id;
	
	@Constraints.Required
	public int water;
	
	@Constraints.Required
	public int gas;
	
	@Constraints.Required
	public int heat;
	
	@Constraints.Required
	public int internet;
	
	@Constraints.Required
	public int fitness;
	
	@Constraints.Required
	public int laundry;
	
	@Constraints.Required
	public int parking;
	
	public static Finder<Long, Service> find = new Finder<Long, Service>(Long.class, Service.class);
	
	public static List<String> list(){
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
	
	public static Service create(Boolean water, Boolean gas, Boolean heat, Boolean internet, Boolean fitness, Boolean laundry, Boolean parking){
		Service s = new Service();
		s.water = water? 1: 0;
		s.gas = gas? 1:0;
		s.heat = heat? 1:0;
		s.internet = internet? 1:0;
		s.fitness = fitness? 1:0;
		s.laundry = laundry? 1:0;
		s.parking = parking? 1:0;
		s.save();
		return s;
	}
}
