package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity
public class House extends Model{

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	public String description;
	
	@Constraints.Required
	@ManyToOne
	public HouseProvider owner;
	
	@Constraints.Required
	public String houseType;
	
	@Constraints.Required
	public float size;
	
	@Constraints.Required
	public String addressLine1;
	
	public String addressLine2;
	
	@Constraints.Required
	public String city;
	
	@Constraints.Required
	public String state;
	
	@Constraints.Required
	public String zipCode;
	
	@Constraints.Required
	public float price;
	
	@Constraints.Required
	public String leasingType;
	
	@Constraints.Required
	public List<String> transportations = new ArrayList<String>();
	
	@Constraints.Required
	public List<String> neighbors = new ArrayList<String>();
	
	public String requirements;
	
	@Constraints.Required
	@Constraints.Min(0)
	public int availability;
	
	@Constraints.Required
	@Formats.DateTime(pattern="YYYY-MM-DD HH:MM:SS")
	public Date updatedTime;
	
	@Constraints.Required
	public List<String> areas = new ArrayList<String>();
	
	@Constraints.Required
	public List<String> services = new ArrayList<String>();
	
	/*
	 * Generic query helper for entity HouseProvider with id Long
	 */
	public static Finder<Long, House> find = new Finder<Long, House>(Long.class, House.class);
	
	public static Page<House> recentUpdated(int pageSize, int page){
		return find.
				where().
				orderBy("updatedTime" + " " + "asc").
				findPagingList(pageSize).
				getPage(page);
		
	}
}
