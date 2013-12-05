package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class HouseProvider extends Model {
	
	@Id
	@Constraints.Email
	public String email;
	
	@Constraints.MaxLength(200)
	public String description;
	
	@Constraints.Required
	public String lname;
	
	@Constraints.Required
	public String fname;
	
	@Constraints.Required
	public String providerType;
	
	@Constraints.Required
	public String occupation;
	
	public String verificationDoc;
	
	@Constraints.Required
	public String addressLine1;
	
	public String addressLine2;
	
	@Constraints.Required
	public String city;
	
	@Constraints.Required
	public String states;
	
	@Constraints.Required
	public String zipCode;
	
	//@OneToMany
	//public List<House> houses = new ArrayList<House>();
	
	/**
     * Generic query helper for entity HouseProvider with id Long
     */
	public static Finder<String,HouseProvider> find = new Finder<String,HouseProvider>(String.class, HouseProvider.class);
	
	/*
	 * 		Update data by passing form data
	 */
	public void updateWithForm(Map<String, String[]> data){
		this.occupation = data.get("occupation")[0];
		this.providerType = data.get("providerType")[0];
		this.description = data.get("description")[0];
		this.addressLine1 = data.get("addressLine1")[0];
		this.addressLine2 = data.get("addressLine2")[0];
		this.city = data.get("city")[0];
		this.states = data.get("states")[0];
		this.zipCode = data.get("zipCode")[0];
		this.update();
	}
}
