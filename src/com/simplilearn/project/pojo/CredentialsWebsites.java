package com.simplilearn.project.pojo;

import java.io.Serializable;

public class CredentialsWebsites extends CredentialsLockedMe implements Serializable{
	
	private String username;
	private String website;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public CredentialsWebsites(String username, String password, String username2, String website, String password2) {
		super(username, password);
		username = username2;
		this.website = website;
		password = password2;
	}

	
	
	
}
