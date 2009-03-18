package org.ulpmm.univrav.entities;

/**
 * Class for the User entity
 * 
 * @author morgan
 *
 */
public class User {

	/** the user's id */
	private int userid;
	
	/** the user's login */
	private String login;
	
	/** the user's email address */
	private String email;
	
	/**
	 * Default constructor
	 */
	public User() {}
	
	/**
	 * User's constructor
	 * 
	 * @param userid the user's id
	 * @param login the user's login
	 * @param email the user's email address
	 */
	public User(int userid,String login, String email) {
		this.userid=userid;
		this.login=login;
		this.email=email;
	}

	/**
	 * Gets the user's id
	 * @return the user's id
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * Sets the user's id
	 * @param userid the user's id
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * Gets the user's login
	 * @return the user's login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the user's login
	 * @param login the user's login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the user's email address
	 * @return the user's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email address
	 * @param email the user's email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
