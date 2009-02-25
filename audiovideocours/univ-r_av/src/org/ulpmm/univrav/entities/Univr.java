/**
 * 
 */
package org.ulpmm.univrav.entities;

/**
 * @author laurent
 *
 */
public class Univr {
	
	private int courseid;
	private int uid;
	private int groupCode;
	private String establishment;
	
	/**
	 * @param courseid
	 * @param uid
	 * @param groupCode
	 */
	public Univr(int courseid, int uid, int groupCode, String establishment) {
		super();
		this.courseid = courseid;
		this.uid = uid;
		this.groupCode = groupCode;
		this.establishment = establishment;
	}
	
	/**
	 * @return the establishment
	 */
	public String getEstablishment() {
		return establishment;
	}

	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @return the groupCode
	 */
	public int getGroupCode() {
		return groupCode;
	}
	
	
}
