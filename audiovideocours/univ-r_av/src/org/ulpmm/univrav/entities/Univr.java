/**
 * 
 */
package org.ulpmm.univrav.entities;

/**
 * Class for the Univr entity
 * 
 * @author morgan
 *
 */
public class Univr {
	
	/** the course's id */
	private int courseid;
	
	/** the univr uid **/
	private int uid;
	
	/** the group code */
	private int groupCode;
	
	/** the establishment (umb,urs,ulp) */
	private String establishment;
	
	/**
	 * Univr's constructor
	 * 
	 * @param courseid the course's id
	 * @param uid the univr uid
	 * @param groupCode the group code
	 */
	public Univr(int courseid, int uid, int groupCode, String establishment) {
		super();
		this.courseid = courseid;
		this.uid = uid;
		this.groupCode = groupCode;
		this.establishment = establishment;
	}
	
	/**
	 * Gets the establishment
	 * @return the establishment
	 */
	public String getEstablishment() {
		return establishment;
	}

	/**
	 * Gets the course's id
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * Gets the uid
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * Gets the group code
	 * @return the groupCode
	 */
	public int getGroupCode() {
		return groupCode;
	}
	
	
}
