package controllers;

import java.util.List;

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
import views.html.houses.*;

public class Houses extends Controller{
	
	public static Result profile(Long id){
		House house = null;
		house = House.find.byId(id);
		if(house == null){
			Logger.info("house not found.");
			return ok();
//			return ok(index.render("", Users.find.byId(session().get("email")), House.recentUpdated(4, 0)));
		}
		List<HPicture> images = HPicture.getImages(id);
		int edit = 0;
		if(session().get("email").equals(house.owner.email)){
			edit = 1;
		}
		return ok(hprofile.render(house, images, edit, Users.find.byId(session().get("email"))));
	}
	
}
