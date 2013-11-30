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
	
	@ManyToMany
	@JoinTable(name="follows", joinColumns=@JoinColumn(name="users_email"), inverseJoinColumns=@JoinColumn(name="follows_email"))
	public List<Users> followTo = new ArrayList<Users>();
	
	@ManyToMany
	@JoinTable(name="follows", joinColumns=@JoinColumn(name="follows_email"), inverseJoinColumns=@JoinColumn(name="users_email"))
	public List<Users> followBy = new ArrayList<Users>();
	
	@ManyToMany
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
    
    public static void edit(String email, String name, String passwd, String phone, String type, String neighbor) {
    	Users user = find.ref(email);
    	user.name = name;
    	user.passwd = passwd;
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
    
    public static void subscribeToHouse(String email, Long id){
    	Users user = find.ref(email);
    	user.subscribeTo.add(House.find.ref(id));
    	user.saveManyToManyAssociations("subscribeTo");
    }
    
    public static void unsubscribeToHouse(String email, Long id){
    	Users user = find.ref(email);
    	int index = user.subscribeTo.indexOf(House.find.ref(id));
    	if(index>=0){
    		user.subscribeTo.remove(index);
    		user.saveManyToManyAssociations("subscribeTo");
    	}
    }
    
    public static void followToUser(String email, String followingEmail){
    	Users user = find.ref(email);
    	user.followTo.add(Users.find.ref(followingEmail));
    	user.saveManyToManyAssociations("followTo");
    }
    
    public static void unfollowToUser(String email, String followingEmail){
    	Users user = find.ref(email);
    	Users follow = find.ref(followingEmail);
    	user.followTo.remove(follow);
    	user.saveManyToManyAssociations("followTo");
    }
}
