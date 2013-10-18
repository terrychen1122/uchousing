package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

/*
 *  Admin Entity managed by Ebean
 */
@Entity
public class Admin extends Model {
	
	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	@Constraints.MinLength(6)
	public String passwd;
	
	@Constraints.Email
	public String email;
	
	/**
     * Generic query helper for entity Admin with id Long
     */
	public static Finder<Long,Admin> find = new Finder<Long,Admin>(Long.class, Admin.class); 
}
