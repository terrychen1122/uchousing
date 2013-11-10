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
	
	public static class Profile {
		
		public String name;
        
		public String phone;
        
		public String neighbor;
		
		public String preferredType;
    }

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
		// ---------- TEMP --------------
//			Users profileUser = new Users();
//			profileUser.email = "testing@case.edu";
//			profileUser.passwd = "000000";
//			profileUser.isHouseProvider = 0;
//			profileUser.name = "testing_user";
//			profileUser.neighbor1 = "Queit";
//			profileUser.phone = "111-111-1111";
//			profileUser.preferredType = "Studio";
//			profileUser.profileImage = "default_profile_pic.jpg";
//			profileUser.followTo = null;
//			profileUser.subscribeTo = null;
		// ---------- TESTING OBJECT -------------
		if(profileUser != null) {
			return ok(profileFrame.render(profileUser, logUser, isEditable, mainWindow, form(Profile.class), form(HouseProvider.class)));
		} else {
			//TEMP
			return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
		}
	}
	
	/**************** POST REQUEST ************************/
	
	public static Result processEdit(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			Form<Profile> editForm = form(Profile.class).bindFromRequest();
			if(editForm.hasErrors()) {
				Users user = Users.find.byId(email);
				return badRequest(profileFrame.render(user, user, 1, "edit", editForm, form(HouseProvider.class)));
			}else{
				Users.edit(session().get("email"), 
						editForm.bindFromRequest().field("name").value(), 
						editForm.field("phone").value(), 
						editForm.field("preferredType").value(), 
						editForm.field("neighbor").value());
				return profile(session().get("email"));
			}
		}
		return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));  
	}
	
	public static Result processApplication(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			Form<HouseProvider> providerForm = form(HouseProvider.class).bindFromRequest();
			if(providerForm.hasErrors()) {
				Users user = Users.find.byId(email);
				return badRequest(profileFrame.render(user, user, 1, "info", form(Profile.class), providerForm));
			}else{
				providerForm.get().email = email;
				providerForm.get().save();
				Users.setHouseProvider(email);
				return profile(session().get("email"));
			}
		}
		return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
	}
	
	public static Result profileImageUpload(String email){
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null){
			String fileName = picture.getFilename();
			String contentType = picture.getContentType();
			File file = picture.getFile();
			try {
				FileUtils.moveFile(file, new File("../../public/images/profile_pic/"+email, fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return profile(email);
	}
}
