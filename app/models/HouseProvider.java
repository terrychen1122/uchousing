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
	@Constraints.Required
	@Constraints.Email
	public String email;
	
	@Constraints.Required
	public String houseType;
	
	@Constraints.MaxLength(200)
	public String description;
	
	@Constraints.Required
	public String lname;
	
	@Constraints.Required
	public String fname;
	
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
	
	
}
