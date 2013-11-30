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
import org.codehaus.jackson.JsonNode;



public class UserProfile extends Controller{

	public static Result profile(String email){
		return profile(email, "info");
	}
	
	public static Result editProfile(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			return profile(email, "edit");
		}
		return redirect(routes.Application.index());
	}
	
	public static Result getSubscribe(String email){
		return profile(email, "subscribe");
	}
	
	public static Result getFollow(String email){
		return profile(email, "follow");
	}
	
	public static Result profile(String email, String mainWindow) {
		int isEditable = 0;
		if(!session().isEmpty()){
    		isEditable = (email.equals(session().get("email"))) ? 1 : 0;
    	}
		Users profileUser = Users.find.byId(email);
		if(profileUser != null) {
			return ok(profileFrame.render(profileUser, isEditable, mainWindow, form(HouseProvider.class)));
		} else {
			//TEMP
			return redirect(routes.Application.index());
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
		return redirect(routes.Application.index()); 
	}
	
	public static Result processApplication(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			Form<HouseProvider> providerForm = form(HouseProvider.class).bindFromRequest();
			if(providerForm.hasErrors()) {
				Users user = Users.find.byId(email);
				return badRequest(profileFrame.render(user, 1, "info",providerForm));
			}else{
				providerForm.get().email = email;
				providerForm.get().save();
				Users.setHouseProvider(email);
				return profile(session().get("email"));
			}
		}
		return redirect(routes.Application.index());
	}
	
	public static Result profileImageChange(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			Map<String, String[]> queryParams = request().body().asFormUrlEncoded();
			String url = queryParams.get("pic")[0];
			Users.setProfileImge(email, url);
			return ok();
		}
		return redirect(routes.Application.index());
	}
	
	public static Result followUser(){
		Map<String, String[]> queryParams = request().body().asFormUrlEncoded();
		String email = queryParams.get("email")[0];
		if(!session().isEmpty()&&!session().get("email").equals(email)){
			Users.followToUser(session().get("email"), email);
		}
		return ok();
	}
	
	public static Result unfollowUser(){
		Map<String, String[]> queryParams = request().body().asFormUrlEncoded();
		String email = queryParams.get("email")[0];
		if(!session().isEmpty()&&!session().get("email").equals(email)){
			Users.unfollowToUser(session().get("email"), email);
		}
		return profile(email);
	}
	
	public static Result subscribeHouse(){
		Map<String, String[]> queryParams = request().body().asFormUrlEncoded();
		Long id = Long.parseLong(queryParams.get("houseID")[0], 10);
		if(!session().isEmpty()){
			System.out.println("id:"+id);
			Users.subscribeToHouse(session().get("email"), id);
		}
		return ok();
	}
	
	public static Result unsubscribeHouse(){
		Map<String, String[]> queryParams = request().body().asFormUrlEncoded();
		Long id = Long.parseLong(queryParams.get("houseID")[0], 10);
		if(!session().isEmpty()){
			Users.unsubscribeToHouse(session().get("email"), id);
		}
		return redirect(routes.Houses.profile(id));
	}
}
