package controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	
	/*
	 * 		Render House profile page
	 */
	public static Result profile(Long id){
		House house = null;
		house = House.find.byId(id);
		// Check for the house id
		if(house == null){
			Logger.info("house not found.");
			return ok();
		}
		List<HPicture> images = HPicture.getImages(id);
		int edit = 0;
		if(session().get("email")!=null&&session().get("email").equals(house.owner.email)){
			edit = 1;
		}
		return ok(hprofile.render(house, images, edit));
	}
	
	/*
	 * 		Handle user rating action and update database
	 */
	public static Result rate(Long id, int r){
		House house = null;
		house = House.find.byId(id);
		if(house == null){
			Logger.info("house not found.");
			return ok();
		}
		
		//	average rate calculation
		double totalRates = house.rate * house.totoalRates;
		int totalRaters = ++house.totoalRates;
		house.rate = (totalRates + r)/totalRaters;
		house.update();
		return redirect(routes.Houses.profile(id));
	}
	
	/*
	 * 		Handle user comment action and update database
	 */
	public static Result commentPost(Long id){
		House house = null;
		house = House.find.byId(id);
		if(house == null){
			Logger.info("house not found.");
			return ok();
		}
		Map<String, String[]> formData = request().body().asFormUrlEncoded();
		String content = formData.get("comment")[0];
		Comment newComment = new Comment();
		newComment.commentTo = house;
		newComment.content = content;
		newComment.user = Users.find.byId(session().get("email"));
		newComment.createTime = new Date();
		newComment.save();
		return redirect(routes.Houses.profile(id));
	}
	
	/*
	 * 		Delete comment
	 */
	public static Result commentDelete(Long id){
		Comment comment = null;
		comment = Comment.find.byId(id);
		if(comment == null){
			Logger.info("comment not found.");
			return ok();
		}
		Long houseId = comment.commentTo.id;
		comment.delete();
		return redirect(routes.Houses.profile(houseId));
	}
}
