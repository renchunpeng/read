package com.soecode.lyf.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class User{
	private String id;

	@NotEmpty(message = "用户名不能为空")
	private String name;

	@NotEmpty(message = "密码不能为空")
	private String password;

	private String remindupdate;

	@NotEmpty(message = "联系电话不能为空")
	private String mobile;

	@NotEmpty(message = "邮箱不能为空")
	private String email;

	@NotEmpty(message = "归属地不能为空")
	private String qu;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQu() {
		return qu;
	}

	public void setQu(String qu) {
		this.qu = qu;
	}

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