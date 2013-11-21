package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.mvc.Http;
import models.*;
import views.html.*;
import views.html.search.*;

public class SearchEngine extends Controller {
	
	public static Result searchPage(){
		Users logUser = Users.find.byId(session().get("email"));
		
		return ok();
	}
	
	public static Result fetchSearch(){
		
		return ok();
	}
	
	public static Result processSearch(){
		
		return ok();
	}
}
