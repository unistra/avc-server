package org.ulpmm.univrav.entities;

/**
 * 
 * @author morgan
 *
 */
public class User {

	private int userid;
	private String login;
	private String email;
	
	public User() {}
	
	public User(int userid,String login, String email) {
		this.userid=userid;
		this.login=login;
		this.email=email;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
