package org.ulpmm.univrav.entities;

public class Smil {

	private int smilid;
	private int courseid;
	private String smilpath;
	
	/**
	 * @param smilid
	 * @param courseid
	 * @param smilpath
	 */
	public Smil(int smilid, int courseid, String smilpath) {
		this.smilid = smilid;
		this.courseid = courseid;
		this.smilpath = smilpath;
	}

	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * @return the smilid
	 */
	public int getSmilid() {
		return smilid;
	}

	/**
	 * @return the smilpath
	 */
	public String getSmilpath() {
		return smilpath;
	}
	
	
	
	
}
