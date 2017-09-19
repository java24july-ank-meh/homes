package com.revature.model;

public class User {
	private String username;
	private String password;
	private String slackId;
	private String name;
	private String email;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String username, String password, String slackId, String name, String email) {
		super();
		this.username = username;
		this.password = password;
		this.slackId = slackId;
		this.name = name;
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSlackId() {
		return slackId;
	}
	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", slackId=" + slackId + ", name=" + name
				+ ", email=" + email + "]";
	}
	
	
	
}
