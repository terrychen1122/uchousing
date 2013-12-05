package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Comment extends Model{

	@Id
	public Long id;
	
	@Constraints.Required
	@ManyToOne
	public Users user;
	
	@Constraints.Required
	@Formats.DateTime(pattern="YYYY-MM-DD HH:MM:SS")
	public Date createTime;
	
	@Constraints.Required
	@Constraints.MaxLength(200)
	public String content;
	
	@ManyToOne
	public House commentTo;
	
	/*
	 * Generic query helper for entity Comment with id Long
	 */
	public static Finder<Long, Comment> find = new Finder<Long, Comment>(Long.class, Comment.class);
	
	/**
	 * 		Return all comments of a house
	 */
	public static List<Comment> getCommentsByHouse(Long id){
		return find.where()
                .eq("commentTo.id", id)
           .findList();
	}
}
