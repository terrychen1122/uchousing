package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Users extends Model {
	
	@Id
	@Constraints.Required
	@Constraints.Email
	@Formats.NonEmpty
	public String email;
	
	@Constraints.Required
	public String name;
	
	@Constraints.Required
	@Constraints.MinLength(6)
	public String passwd;
	
	@Constraints.Required
	public String phone;
	
	@Constraints.Required
	public int isHouseProvider;
	
	public String neighbor;
	
	public String profileImage;
	
	public List<Users> followTo = new ArrayList<Users>();
	
	public List<House> subscribeTo = new ArrayList<House>();
	
	@Constraints.Required
	public String preferredType;
	
	/**
     * Generic query helper for entity User with id Long
     */
	public static Finder<String,Users> find = new Finder<String,Users>(String.class, Users.class); 

	/**
     * Authenticate a User.
     */
    public static Users authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("passwd", password)
            .findUnique();
    }
    
    public String validate() {
    	if(find.where().eq("email", email).findUnique() != null) {
    		return "Email: " + email + " is already existed";
    	}
        return null;
    }
    
    public static void edit(String email, String name, String phone, String type, String neighbor) {
    	Users user = find.ref(email);
    	user.name = name;
    	user.phone = phone;
    	user.preferredType = type;
    	user.neighbor = neighbor;
    	user.update();
    }
    
    public static void setHouseProvider(String email) {
    	Users user = find.ref(email);
    	user.isHouseProvider = 1;
    	user.update();
    }
    
    public static void setProfileImge(String email, String url){
    	Users user = find.ref(email);
    	user.profileImage = url;
    	user.update();
    }
}
