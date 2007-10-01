/**
 * 
 */
package org.ulpmm.univrav.entities;

/**
 * @author laurent
 *
 */
public class Amphi {
	
	private int buildingid;
	private String name;
	private String type;
	private String ipAddress;
	private boolean status;
	
	public Amphi(){
	}
	
	/**
	 * @param buildingid
	 * @param name
	 * @param type
	 * @param ipAddress
	 * @param status
	 */
	public Amphi(int buildingid, String name, String type, String ipAddress,
			boolean status) {
		super();
		this.buildingid = buildingid;
		this.name = name;
		this.type = type;
		this.ipAddress = ipAddress;
		this.status = status;
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
	
	
	
}
