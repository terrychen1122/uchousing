package controllers;

import java.util.List;
import java.util.Map;

import com.avaje.ebean.Page;

import play.*;
import play.mvc.*;
import play.data.*;
import play.mvc.Http;
import models.*;
import views.html.*;
import views.html.search.*;

public class SearchEngine extends Controller {
	
	public static Result searchPage(){
		return redirect(
		        routes.SearchEngine.fetchSearch(0, "updatedTime", "", "", "", "", "", 0)
			    );
	}
 
	public static Result fetchSearch(int page, String sortBy, String filter, String size, String price, String htype, String ltype, int rate){
		return ok(
	            search.render(
	                House.fetch(page, 10, sortBy, filter, size, price, htype, ltype, rate), sortBy, filter, size, price, htype, ltype, rate
	            )
	        );
	}
	
	public static Result submitSearch(int page, String sortBy, String filterType, String filter){
		System.out.println("p:"+page + ", s:"+sortBy+", t:"+filterType+", f: "+filter);
		return redirect(
		        routes.SearchEngine.fetchSearch(page, sortBy, filter, "", "", "", "", 0)
			    );
	}
}
