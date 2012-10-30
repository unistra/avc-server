package org.ulpmm.univrav.entities;

/**
 * Class for the Amphi entity
 * 
 * @author morgan
 *
 */
public class Amphi implements Comparable<Amphi>{
	
	/** the amphi's id */
	private int amphiid;
	
	/** the building's id */
	private int buildingid;
	
	/** the name of the amphi */
	private String name;
	
	/** the id adress of the amphi */
	private String ipAddress;
	
	/** the live status */
	private boolean status;
	
	/** the url of google map */
	private String gmapurl;
	
	/** the number of courses for this amphi */
	private int number;
	
	/** the client version installed */
	private String version;
	
	/** to protect live access with UDS account **/
	private boolean restrictionuds;
	
	/**
	 * Default contructor
	 */
	public Amphi(){}
	
	/**
	 * Amphi's constructor
	 * 
	 * @param amphiid the amphi's id
	 * @param buildingid the building's id
	 * @param name the name of the amphi
	 * @param ipAddress the id adress of the amphi
	 * @param status the live status
	 * @param gmapurl the url of google map
	 * @param version the client version installed
	 * @param restrictionuds the restriction uds
	 */
	public Amphi(int amphiid, int buildingid, String name, String ipAddress,
			boolean status, String gmapurl,String version,boolean restrictionuds) {
		super();
		this.amphiid = amphiid;
		this.buildingid = buildingid;
		this.name = name;
		this.ipAddress = ipAddress;
		this.status = status;
		this.gmapurl = gmapurl;
		this.version = version;
		this.restrictionuds=restrictionuds;
	}
	
	/**
	 * Gets the amphi's id
	 * @return the amphiid
	 */
	public int getAmphiid() {
		return amphiid;
	}

	/**
	 * Gets the building's id
	 * @return the buildingid
	 */
	public int getBuildingid() {
		return buildingid;
	}

	/**
	 * Gets the name of the amphi
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Gets the ip address
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Gets the live status
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	
	/**
	 * Gets the google map url
	 * @return the gmapurl
	 */
	public String getGmapurl() {
		return gmapurl;
	}
	
	/**
	 * Gets a string representation of this object
	 * @return a string
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * Gets number of courses
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the number
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * To sort amphi by name
	 * @param o the amphi
	 */
	public int compareTo(Amphi o) {
		return this.name.compareTo(o.name);
	}

	/**
	 * Gets the client version of the amphi
	 * @return the client version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the client version
	 * @param version the client version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the restriction uds of the amphi
	 * @return restrictionuds the restriction uds
	 */
	public boolean isRestrictionuds() {
		return restrictionuds;
	}

	/**
	 * Sets the restriction uds of the amphi
	 * @param restrictionuds the restriction uds
	 */
	public void setRestrictionuds(boolean restrictionuds) {
		this.restrictionuds = restrictionuds;
	}

	
}
