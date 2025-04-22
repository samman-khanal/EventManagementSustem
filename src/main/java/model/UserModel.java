package model;

import java.sql.Date;

public class UserModel {
	
	private int id;
	private String fullName;
	private String email;
	private String password;
	private byte[] profilePicture;
	private int role = 1;
	private Date created_date;
	
	// Creating a constructor
	public UserModel() {
		super();
	}

	// Creating a constructor without id
	public UserModel(String fullName, String email, String password, byte[] profilePicture, int role,
			Date created_date) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
		this.role = role;
		this.created_date = created_date;
	}

	// Creatign a fully parameterized constructor
	public UserModel(int id, String fullName, String email, String password, byte[] profilePicture, int role,
			Date created_date) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
		this.role = role;
		this.created_date = created_date;
	}

	// Getter methods
	public int getId() {
		return id;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public byte[] getProfilePicture() {
		return profilePicture;
	}
	
	public int getRole() {
		return role;
	}
	
	public Date getCreatedDate() {
		return created_date;
	}
	
	// Setter Methods
	public void setId(int id) {
		this.id = id;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public void setCreatedDate(Date created_date) {
		this.created_date = created_date;
	}
	
}
