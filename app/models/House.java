package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Expr;

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
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Transportation> transportations = new ArrayList<Transportation>();
	
	public String neighbor;
	
	public String requirements;
	
	public double rate;
	
	public int totoalRates;
	
	@Constraints.Required
	@Constraints.Min(0)
	public int availability;
	
	@Formats.DateTime(pattern="YYYY-MM-DD HH:MM:SS")
	public Date updatedTime;
	
	public String area;
	
	@OneToOne(cascade=CascadeType.ALL)
	public Service services;
	
	/*
	 * Generic query helper for entity HouseProvider with id Long
	 */
	public static Finder<Long, House> find = new Finder<Long, House>(Long.class, House.class);
	
	public static List<House> recentUpdated(int pageSize, int page){
		return find.
				where().
				orderBy("updatedTime" + " " + "desc").
				findPagingList(pageSize).
				getPage(page).
				getList();
		
	}
	
	public static Page<House> fetch(int page, int pageSize, String sortBy, String filter, String size, String price, String htype, String ltype, int rate){
		String order;
		if(sortBy.equals("updatedTime")||sortBy.equals("priceHtoL")||sortBy.equals("sizeLtoS")||sortBy.equals("rate")){
			order = "desc";
		}else{
			order = "asc";
		}
		if(sortBy.equals("priceHtoL")||sortBy.equals("priceLtoH")){
			sortBy = "price";
		}else if(sortBy.equals("sizeStoL")||sortBy.equals("sizeLtoS")){
			sortBy = "size";
		}
		
		int size_lower = 0;
		int size_upper = 300;
		if(size.equals("300to600")){
			size_lower = 300;
			size_upper = 600;
		}else if(size.equals("600to1000")){
			size_lower = 600;
			size_upper = 1000;
		}else if(size.equals("gt1000")){
			size_lower = 1000;
			size_upper = 9999;
		}else if(size.equals("")){
			size_upper = 9999;
		}
		int price_lower = -1;
		int price_upper = 500;
		if(price.equals("500to1000")){
			price_lower = 500;
			price_upper = 1000;
		}else if(price.equals("1000to1500")){
			price_lower = 1000;
			price_upper = 1500;
		}else if(price.equals("gt1500")){
			price_lower = 1500;
			price_upper = 9999;
		}else if(price.equals("")){
			price_upper = 9999;
		}
		
		Page<House> results = find.where()
					.disjunction()
					.ilike("name", "%" + filter + "%")
					.ilike("owner.email", "%" + filter + "%")
					.ilike("addressLine1", "%" + filter + "%")
					.ilike("addressLine2", "%" + filter + "%")
					.ilike("description", "%" + filter + "%").endJunction()
					.conjunction().ilike("houseType", "%"+htype_filter(htype)+"%")
					.ilike("leasingType", "%"+ltype_filter(ltype)+"%")
					.between("price", price_lower, price_upper).
					between("size", size_lower, size_upper).
					ge("rate", rate)
					.endJunction()
					.orderBy(sortBy + " " + order).
					findPagingList(pageSize)
					.getPage(page);
		return results;
	}
	
	public static String htype_filter(String htype){
		if(htype.equals("one-bedroom")){
			return "one bedroom";
		}else if(htype.equals("two-bedroom")){
			return "two bedroom";
		}else if(htype.equals("three-bedroom")){
			return "three bedroom";
		}else {
			return htype;
		}
	}
	
	public static String ltype_filter(String ltype){
		if(ltype.equals("one-year")){
			return "one year";
		}else{
			return ltype;
		}
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
		this.neighbor = data.get("neighbor")[0];
		this.area = data.get("area")[0];
		this.requirements = data.get("requirements")[0];
		this.description = data.get("description")[0];
		Date date = new Date();
		this.updatedTime = date;
		this.editService(data);
		
		String tranString = data.get("trans")[0];
		List<String> transportationArray = Arrays.asList(tranString.split(" "));
		this.transportations.clear();
		for(String line : transportationArray){
			if(!line.equals("")){
				addTransportation(this.id, line);
			}
		}
		this.update();
	}
	
	public static void addTransportation(Long id, String trans){
		List<Transportation> arrayT = Transportation.find.where().eq("line", trans).findList();
		House house = find.ref(id);
		if(!arrayT.isEmpty()){
			if(!house.transportations.contains(arrayT.get(0))){
				house.transportations.add(arrayT.get(0));
			}
		}else{
			house.transportations.add(Transportation.create(trans));
		}
	}
	
	public static void setService(Long id, Long serviceID){
		House house = find.ref(id);
		house.services = Service.find.ref(serviceID);
		house.update();
	}
	
	public void editService(Map<String,String[]> data){
		
		Service service = Service.find.ref(this.services.id);
		service.water = (data.get("water")==null)? 0: 1;
		service.gas = (data.get("gas")==null)? 0: 1;
		service.heat = (data.get("heat")==null)? 0: 1;
		service.internet = (data.get("internet")==null)? 0: 1;
		service.fitness = (data.get("fitness")==null)? 0: 1;
		service.laundry = (data.get("laundry")==null)? 0: 1;
		service.parking = (data.get("parking")==null)? 0: 1;
		service.save();
		this.services = service;
	}
	
	
}
