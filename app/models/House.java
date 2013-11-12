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
	
	@ManyToMany
	public List<Transportation> transportations = new ArrayList<Transportation>();
	
	@ManyToMany
	public List<Neighborhood> neighbors = new ArrayList<Neighborhood>();
	
	public String requirements;
	
	public int rate;
	
	@Constraints.Required
	@Constraints.Min(0)
	public int availability;
	
	@Formats.DateTime(pattern="YYYY-MM-DD HH:MM:SS")
	public Date updatedTime;
	
	@ManyToMany
	public List<Area> areas = new ArrayList<Area>();
	
	@OneToOne
	public Service services;
	
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
	
	public static void addTransporation(Long id, String trans){
		List<Transportation> arrayT = Transportation.find.where().eq("line", trans).findList();
		if(!arrayT.isEmpty()){
			House house = find.ref(id);
			house.transportations.add(arrayT.get(0));
			house.saveManyToManyAssociations("transportations");
		}
	}
	
	public static void setService(Long id, Long serviceID){
		House house = find.ref(id);
		house.services = Service.find.ref(serviceID);
		house.update();
	}
	
	public static void addArea(Long id, String area){
		List<Area> arrayA = Area.find.where().eq("area", area).findList();
		if(!arrayA.isEmpty()){
			House house = find.ref(id);
			house.areas.add(arrayA.get(0));
			house.saveManyToManyAssociations("areas");
		}
	}
	
	public static void addNeighbor(Long id, String type){
		List<Neighborhood> arrayN = Neighborhood.find.where().eq("neigbor_type", type).findList();
		if(!arrayN.isEmpty()){
			House house = find.ref(id);
			house.neighbors.add(arrayN.get(0));
			house.saveManyToManyAssociations("neighbors");
		}
	}
}
