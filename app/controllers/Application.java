package controllers;


import play.*;
import play.mvc.*;
import play.data.*;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.mvc.Http;
import models.*;
import views.html.*;
import static play.data.Form.*;
import java.util.*;



public class Application extends Controller {
  
	public static class Login {
        
		@Constraints.Required @Constraints.Email @Formats.NonEmpty 
		public String email;
        
		@Constraints.Required @Constraints.MinLength(6)
		public String password;
        
        public String validate() {
            if(Users.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }
	
	public static Result index() {
    	
    	if(session().isEmpty()){
    		return ok(index.render("Homepage", null, House.recentUpdated(4, 0)));
    	} else {
    		//TEMP
    		return ok(index.render("Your new application is ready.", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
    	}
    }
	
	public static Result register() {
		if(session().isEmpty()){
			return ok(register.render(form(Users.class)));
		} else {
			//TEMP
			return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
		}
	}
	
	public static Result processRegistration() {
		Form<Users> registerForm = form(Users.class).bindFromRequest();
		if(registerForm.hasErrors()) {
			return badRequest(register.render(registerForm));
		} else {
			registerForm.get().profileImage = "default_profile_pic.jpg";
			registerForm.get().save();
			//TEMP 
			return ok(index.render("", Users.find.byId(registerForm.get().email), House.recentUpdated(4, 0)));
		}
	}
	
	public static Result login() {
		if(session().isEmpty()){
			return ok(login.render(form(Login.class)));
		} else {
			//TEMP
			return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
		}
    }
    
	public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                routes.Application.index()
            );
        }
    }
	
	public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.index()
        );
    }
	
	public static Result profile(String email){
		return profile(email, "info");
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
			return ok(profileFrame.render(profileUser, logUser, isEditable, mainWindow));
		} else {
			//TEMP
			return ok(index.render("", Users.find.byId(request().username()), House.recentUpdated(4, 0)));
		}
	}
	
	
    
    
}
