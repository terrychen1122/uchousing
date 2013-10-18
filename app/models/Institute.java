package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Institute extends Model {

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String addressLine1;
	
	public String addressLine2;
	
	@Constraints.Required
	public String city;
	
	@Constraints.Required
	public String state;
	
	@Constraints.Required
	public String zipCode;
	
	/*
	 * Generic query helper for entity Institute with id Long
	 */
	public static Finder<Long, Institute> find = new Finder<Long, Institute>(Long.class, Institute.class);
}
