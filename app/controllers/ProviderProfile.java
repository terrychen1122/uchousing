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
import views.html.pprofile.*;
import static play.data.Form.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProviderProfile extends Controller{
	
	public static Result profile(String email){
		HouseProvider profileUser = HouseProvider.find.byId(email);
		int isEditable = 0;
		if(!session().isEmpty()){
    		isEditable = (email.equals(session().get("email"))) ? 1 : 0;
    	}
		List<House> listing = House.findListing(email);
		return ok(providerprofile.render(profileUser, listing, isEditable, form(House.class)));
	}
	
	public static Result addHouse(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			HouseProvider profileUser = HouseProvider.find.byId(email);
			List<House> listing = House.findListing(email);
			Form<House> houseForm = form(House.class).bindFromRequest();
			if(houseForm.hasErrors()) {
				return badRequest(providerprofile.render(profileUser, listing, 1, form(House.class)));
			}else{
				Map<String, String[]> formData = request().body().asFormUrlEncoded();
				filterServiceInput(formData, houseForm);
				filterTransportationInput(formData, houseForm);
				houseForm.get().owner = profileUser;
				Date date = new Date();
				houseForm.get().updatedTime = date;
				houseForm.get().save();
				return profile(email);
			}
		}
		return redirect(routes.Application.index());
	}
	
	public static Result editHouse(Long id){
		House house = null;
		house = House.find.byId(id);
		if(house == null || session().isEmpty() || !session().get("email").equals(house.owner.email)){
			return redirect(routes.Application.index());
		}
		Map<String, String[]> formData = request().body().asFormUrlEncoded();
		house.updateWithForm(formData);
		
		List<House> listing = House.findListing(session().get("email"));
		HouseProvider profileUser = HouseProvider.find.byId(session().get("email"));
		return profile(session().get("email"));
	}
	
	public static Result setHouse(Long id){
		House house = null;
		house = House.find.byId(id);
		if(house == null || session().isEmpty() || !session().get("email").equals(house.owner.email)){
			return redirect(routes.Application.index());
		}
		return ok(houseSetting.render(house));
	}
	
	public static Result deleteHouse(){
		Map<String, String[]> queryParams = request().body().asFormUrlEncoded();
		Long id = Long.parseLong(queryParams.get("houseID")[0], 10);
		House house = null;
		house = House.find.byId(id);
		if(house == null || session().isEmpty() || !session().get("email").equals(house.owner.email)){
			return ok();
		}
		house.delete();
		
		HouseProvider profileUser = HouseProvider.find.byId(session().get("email"));
		List<House> listing = House.findListing(session().get("email"));
		return profile(session().get("email"));
	}
	
	public static Result editProfile(String email){
		if(!session().isEmpty()&&session().get("email").equals(email)){
			Map<String, String[]> formData = request().body().asFormUrlEncoded();
			HouseProvider profileUser = HouseProvider.find.byId(email);
			profileUser.updateWithForm(formData);
			
			return profile(email);
		}
		return redirect(routes.Application.index());
	}
	
	
	
	
	/*
	 * 
	 */
	private static void filterServiceInput(Map<String, String[]> data, Form<House> house){
		boolean water = (data.get("water")==null)? false: true;
		boolean gas = (data.get("gas")==null)? false: true;
		boolean heat = (data.get("heat")==null)? false: true;
		boolean internet = (data.get("internet")==null)? false: true;
		boolean fitness = (data.get("fitness")==null)? false: true;
		boolean laundry = (data.get("laundry")==null)? false: true;
		boolean parking = (data.get("parking")==null)? false: true;
		house.get().services = Service.create(water, gas, heat, internet, fitness, laundry, parking);
	}
	
	private static void filterTransportationInput(Map<String, String[]> data, Form<House> house){
		String tranString = data.get("trans")[0];
		List<String> transportationArray = Arrays.asList(tranString.split(" "));
		for(String line : transportationArray){
			if(!line.equals("")){
				List<Transportation> arrayT = Transportation.find.where().eq("line", line).findList();
				if(!arrayT.isEmpty()){
					house.get().transportations.add(arrayT.get(0));
				}else{
					house.get().transportations.add(Transportation.create(line));
				}
			}
		}
	}
	
}
