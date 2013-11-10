package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class HPicture {
	// Entity of House Picture
	@Id
	public Long id;
	
	@Constraints.Required
	public String housePicURL;
	
	public String housePicTitle;
	
	@Constraints.Required
	@ManyToOne
	public House house;
	
	
	
	/*
	 * Generic query helper for entity HPicture with id Long
	 */
	public static Finder<Long, HPicture> find = new Finder<Long, HPicture>(Long.class, HPicture.class);
	
	public static List<HPicture> getImages(Long id){
		return find.where()
                .eq("house.id", id)
           .findList();
	}
}
