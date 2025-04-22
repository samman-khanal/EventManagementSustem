package model;

import java.sql.Date;
import java.sql.Timestamp;

public class EventModel {

	private int id;
	private String title;
	private String description;
	private String location;
	private Date event_date;
	private int created_by;
	private Timestamp created_at;
	
	// Creating a constructor
	public EventModel() {
		super();
	}

	// Creating a constructor without ID
	public EventModel(String title, String description, String location, Date event_date, int created_by,
			Timestamp created_at) {
		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.event_date = event_date;
		this.created_by = created_by;
		this.created_at = created_at;
	}

	// Creatign a fully parameterized constructor
	public EventModel(int id, String title, String description, String location, Date event_date, int created_by,
			Timestamp created_at) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.event_date = event_date;
		this.created_by = created_by;
		this.created_at = created_at;
	}

	// Getter methods
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLocation() {
		return location;
	}
	
	public Date getEventDate() {
		return event_date;
	}
	
	public int getCreatedBy() {
		return created_by;
	}
	
	public Timestamp getCreatedAt() {
		return created_at;
	}
	
	// Setter Methods
	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setEventDate(Date event_date) {
		this.event_date = event_date;
	}

	public void setCreatedBy(int created_by) {
		this.created_by = created_by;
	}

	public void setCreatedAt(Timestamp created_at) {
		this.created_at = created_at;
	}
	
}
