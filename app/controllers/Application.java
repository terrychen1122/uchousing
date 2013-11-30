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

import java.io.File;
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
    		return ok(index.render("Homepage", House.recentUpdated(4, 0)));
    }
	
	public static Result register() {
		if(session().isEmpty()){
			return ok(register.render(form(Users.class)));
		} else {
			return index();
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
			File f = new File("../../public/images/profile_pic/" + registerForm.get().email);
			try {
				f.mkdirs();
			} catch (Exception e){
				e.printStackTrace();
			}
			session("email", registerForm.get().email);
			return redirect(routes.UserProfile.profile(session().get("email")));
		}
	}
	
	public static Result login() {
		if(session().isEmpty()){
			return ok(login.render(form(Login.class)));
		} else {
			//TEMP
			return index();
		}
    }
    
	public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                routes.UserProfile.profile(session().get("email"))
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
	
	public static Result contact() {
		return ok(contact.render());
	}
    
}
