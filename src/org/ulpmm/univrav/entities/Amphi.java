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

	/**
	 * set amphi id
	 * @param amphiid the amphi id
	 */
	public void setAmphiid(int amphiid) {
		this.amphiid = amphiid;
	}

	/**
	 * set building id
	 * @param buildingid the building id
	 */
	public void setBuildingid(int buildingid) {
		this.buildingid = buildingid;
	}

	/**
	 * set the name
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * set the ip adress
	 * @param ipAddress the ip adress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * set the status
	 * @param status the status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * set the gmap url
	 * @param gmapurl the gmap url
	 */
	public void setGmapurl(String gmapurl) {
		this.gmapurl = gmapurl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amphiid;
		result = prime * result + buildingid;
		result = prime * result + ((gmapurl == null) ? 0 : gmapurl.hashCode());
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
		result = prime * result + (restrictionuds ? 1231 : 1237);
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		Amphi other = (Amphi) obj;
		if (amphiid != other.amphiid)
			return false;
		if (buildingid != other.buildingid)
			return false;
		if (gmapurl == null) {
			if (other.gmapurl != null)
				return false;
		} else if (!gmapurl.equals(other.gmapurl))
			return false;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		if (restrictionuds != other.restrictionuds)
			return false;
		if (status != other.status)
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	
}
