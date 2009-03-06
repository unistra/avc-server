/**
 * 
 */
package org.ulpmm.univrav.entities;

/**
 * @author laurent
 *
 */
public class Amphi implements Comparable<Amphi>{
	
	private int amphiid;
	private int buildingid;
	private String name;
	private String type;
	private String ipAddress;
	private boolean status;
	private String gmapurl;
	private int number; // number of courses for this amphi
	private String version;
	
	public Amphi(){
	}
	
	/**
	 * @param buildingid
	 * @param name
	 * @param type
	 * @param ipAddress
	 * @param status
	 * @param gmapurl
	 */
	public Amphi(int amphiid, int buildingid, String name, String type, String ipAddress,
			boolean status, String gmapurl,String version) {
		super();
		this.amphiid = amphiid;
		this.buildingid = buildingid;
		this.name = name;
		this.type = type;
		this.ipAddress = ipAddress;
		this.status = status;
		this.gmapurl = gmapurl;
		this.version = version;
	}
	
	/**
	 * @return the amphiid
	 */
	public int getAmphiid() {
		return amphiid;
	}

	/**
	 * @return the buildingid
	 */
	public int getBuildingid() {
		return buildingid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	
	/**
	 * @return the gmapurl
	 */
	public String getGmapurl() {
		return gmapurl;
	}
	
	/**
	 * returns a String representation of this object
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * To sort amphi by name
	 * @param amphi 
	 */
	public int compareTo(Amphi o) {
		return this.name.compareTo(o.name);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
