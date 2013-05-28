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
	/** the user's firstame */
	private String firstname;
	/** the user's lastname */
	private String lastname;
	/** the user's profile */
	private String profile;
	/** the user's establishment */
	private String establishment;
	/** the user's type */
	private String type;
	/** account activation (for local user) */
	private boolean activate;
	/** etp code for students **/
	private String etp;
	
	private static final String TYPELDAP = "ldap";
	private static final String TYPELOCAL = "local";
	
	public static String getTYPELDAP() {
		return TYPELDAP;
	}

	public static String getTYPELOCAL() {
		return TYPELOCAL;
	}
	
	/**
	 * Default constructor
	 */
	public User() {}
	
	/**
	 * User's constructor
	 * 
	 * @param userid the user id
	 * @param login the user login
	 * @param email the user email
	 * @param firstname the user firstname
	 * @param lastname the user lastname
	 * @param profile the user profile
	 * @param establishment the user establisment
	 * @param type the user type
	 * @param activate account activation
	 * @param etp etp code for student
	 */
	public User(int userid,String login, String email,String firstname, String lastname,String profile,
			String establishment,String type,boolean activate,String etp) {
		this.userid=userid;
		this.login=login;
		this.email=email;
		this.firstname=firstname;
		this.lastname=lastname;
		this.profile=profile;
		this.establishment=establishment;
		this.type=type;
		this.activate=activate;
		this.etp=etp;
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

	/**
	 * Gets the user's firstname
	 * @return the user's firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the user's firstname
	 * @param firstname the user's firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the user's lastname
	 * @return the user's lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the user's lastname
	 * @param lastname the user's lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the user's profile
	 * @return the user's profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * Sets the user's profile
	 * @param profile the user's profile
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * Gets the user's establishment
	 * @return the user's establishment
	 */
	public String getEstablishment() {
		return establishment;
	}

	/**
	 * Sets the user's establishment
	 * @param establishment the user's establishment
	 */
	public void setEstablishment(String establishment) {
		this.establishment = establishment;
	}

	/**
	 * Gets the user's type
	 * @return the user's type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the user's type
	 * @param type the user's type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the account activation
	 * @return true if the account is activate
	 */
	public boolean isActivate() {
		return activate;
	}

	/**
	 * Sets the account activation
	 * @param activate true or false
	 */
	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	/**
	 * Gets the etp code
	 * @return etp code
	 */
	public String getEtp() {
		return etp;
	}

	/**
	 * Sets the etp code
	 * @param etp etp code
	 */
	public void setEtp(String etp) {
		this.etp = etp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activate ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((establishment == null) ? 0 : establishment.hashCode());
		result = prime * result + ((etp == null) ? 0 : etp.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + userid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (activate != other.activate)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (establishment == null) {
			if (other.establishment != null)
				return false;
		} else if (!establishment.equals(other.establishment))
			return false;
		if (etp == null) {
			if (other.etp != null)
				return false;
		} else if (!etp.equals(other.etp))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userid != other.userid)
			return false;
		return true;
	}	
	
	
}
