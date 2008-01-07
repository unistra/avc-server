/**
 * 
 */
package org.ulpmm.univrav.entities;

/**
 * @author laurent
 *
 */
public class Amphi {
	
	private int amphiid;
	private int buildingid;
	private String name;
	private String type;
	private String ipAddress;
	private boolean status;
	private String gmapurl;
	
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
			boolean status, String gmapurl) {
		super();
		this.amphiid = amphiid;
		this.buildingid = buildingid;
		this.name = name;
		this.type = type;
		this.ipAddress = ipAddress;
		this.status = status;
		this.gmapurl = gmapurl;
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
}
