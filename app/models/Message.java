package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
//import play.db.ebean.Model.Finder;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Message extends Model {

	@Id
	public Long id;
	
	@Constraints.MaxLength(50)
	public String title;
	
	@Constraints.Required
	@Constraints.MaxLength(300)
	public String content;
	
	@Constraints.Required
	@Formats.DateTime(pattern="YYYY-MM-DD HH:MM:SS")
	public Date createTime;
	
	@Constraints.Required
	@ManyToOne
	public Users sender;
	
	@Constraints.Required
	@ManyToOne
	public Users receiver;
	
	@Constraints.Required
	public int senderStatus; //0: normal 1: delete
	
	@Constraints.Required
	public int receiverStatus; //0: normal 1: delete
	
	@Constraints.Required
	public int readStatus; //0: read 1: unread
	
	/*
	 * Generic query helper for entity Message with id Long
	 */
	public static Finder<Long, Message> find = new Finder<Long, Message>(Long.class, Message.class);
}
