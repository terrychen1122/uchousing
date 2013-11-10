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
	
	public List<String> transportations = new ArrayList<String>();
	
	public List<String> neighbors = new ArrayList<String>();
	
	public String requirements;
	
	public int rate;
	
	@Constraints.Required
	@Constraints.Min(0)
	public int availability;
	
	@Formats.DateTime(pattern="YYYY-MM-DD HH:MM:SS")
	public Date updatedTime;
	
	public List<String> areas = new ArrayList<String>();
	
	public List<String> services = new ArrayList<String>();
	
	/*
	 * Generic query helper for entity HouseProvider with id Long
	 */
	public static Finder<Long, House> find = new Finder<Long, House>(Long.class, House.class);
	
	public static List<House> recentUpdated(int pageSize, int page){
		return find.
				where().
				orderBy("updatedTime" + " " + "asc").
				findPagingList(pageSize).
				getPage(page).
				getList();
		
	}
	
	public static List<House> findListing(String email){
		return find.where()
		                .eq("owner.email", email)
		           .findList();
	}
	
	public void updateWithForm(Map<String,String[]> data){
		this.name = data.get("name")[0];
		this.houseType = data.get("houseType")[0];
		this.size = Float.parseFloat(data.get("size")[0]);
		this.leasingType = data.get("leasingType")[0];
		this.price = Float.parseFloat(data.get("price")[0]);
		this.availability = Integer.parseInt(data.get("availability")[0]);
		this.addressLine1 = data.get("addressLine1")[0];
		this.addressLine2 = data.get("addressLine2")[0];
		this.city = data.get("city")[0];
		this.state = data.get("state")[0];
		this.zipCode = data.get("zipCode")[0];
		this.requirements = data.get("requirements")[0];
		this.description = data.get("description")[0];
		Date date = new Date();
		this.updatedTime = date;
		this.update();
	}
}
