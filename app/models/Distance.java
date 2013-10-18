package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Distance extends Model{

	@Id
	public Long id;
	
	@Constraints.Required
	@ManyToMany
	public House house;
	
	@Constraints.Required
	@ManyToMany
	public Institute institute;
	
	@Constraints.Required
	@Constraints.Min(0)
	public float walkDuration;
	
	@Constraints.Required
	@Constraints.Min(0)
	public float driveDuration;
	
	/*
	 *  Generic query help for entity Distance with id Long
	 */
	public static Finder<Long, Distance> find = new Finder<Long, Distance>(Long.class, Distance.class);
}
