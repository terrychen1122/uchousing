package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.*;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.mvc.Http;
import models.*;
import views.html.*;
import views.html.profile.*;
import static play.data.Form.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;



public class UserProfile extends Controller{

	public static Result profile(String email){
		return profile(email, "info");
	}
	
	public static Result editProfile(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			return profile(email, "edit");
		}
		return ok(index.render("", Users.find.byId(request().username()), House.recentUpdated(4, 0))); 
	}
	
	public static Result getSubscribe(String email){
		return profile(email, "subscribe");
	}
	
	public static Result getFollow(String email){
		return profile(email, "follow");
	}
	
	public static Result profile(String email, String mainWindow) {
		Users logUser = null;
		int isEditable = 0;
		if(!session().isEmpty()){
    		logUser = Users.find.byId(session().get("email"));
    		isEditable = (email.equals(session().get("email"))) ? 1 : 0;
    	}
		Users profileUser = Users.find.byId(email);
		if(profileUser != null) {
			return ok(profileFrame.render(profileUser, logUser, isEditable, mainWindow, form(HouseProvider.class)));
		} else {
			//TEMP
			return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
		}
	}
	
	/**************** POST REQUEST ************************/
	
	public static Result processEdit(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
				Map<String, String[]> formData = request().body().asFormUrlEncoded();
				Users.edit(session().get("email"), 
						formData.get("name")[0],
						formData.get("passwd1")[0], 
						formData.get("phone")[0], 
						formData.get("preferredType")[0], 
						formData.get("neighbor")[0]);
				return profile(session().get("email"));
			
		}
		return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));  
	}
	
	public static Result processApplication(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			Form<HouseProvider> providerForm = form(HouseProvider.class).bindFromRequest();
			if(providerForm.hasErrors()) {
				Users user = Users.find.byId(email);
				return badRequest(profileFrame.render(user, user, 1, "info",providerForm));
			}else{
				providerForm.get().email = email;
				providerForm.get().save();
				Users.setHouseProvider(email);
				return profile(session().get("email"));
			}
		}
		return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
	}
	
	public static Result profileImageChange(String url, String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			Users.setProfileImge(email, url);
			return profile(email);
		}
		return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
	}
	
	public static Result followUser(String email){
		if(!session().isEmpty()&&!session().get("email").equals(email)){
			Users.followToUser(session().get("email"), email);
		}
		return profile(email);
	}
	
	public static Result unfollowUser(String email){
		if(!session().isEmpty()&&!session().get("email").equals(email)){
			Users.unfollowToUser(session().get("email"), email);
		}
		return profile(email);
	}
	
	public static Result subscribeHouse(Long id){
		if(!session().isEmpty()){
			Users.subscribeToHouse(session().get("email"), id);
		}
		return ok();
	}
}
