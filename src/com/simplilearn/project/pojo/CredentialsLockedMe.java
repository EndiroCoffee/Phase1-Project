package com.simplilearn.project.pojo;

public class CredentialsLockedMe{

	public String username;
	public String password;
	
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
	
	public CredentialsLockedMe(String username, String password) {
		this.username = username;
		this.password = password;
	}
	@Override
	public String toString() {
		return username + " | " + password;
	}
	
}
