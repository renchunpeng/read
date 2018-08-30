package com.soecode.lyf.entity;

public class User{
	private String id;
	
	private String name;
	
	private String password;

	private String remindupdate;

	private String email;

	public String getRemindupdate() {
		return remindupdate;
	}

	public void setRemindupdate(String remindupdate) {
		this.remindupdate = remindupdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}