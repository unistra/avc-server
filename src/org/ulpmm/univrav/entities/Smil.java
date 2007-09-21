package org.ulpmm.univrav.entities;

public class Smil {

	private int courseid;
	private String smilpath;
	
	/**
	 * Default constructor
	 */
	public Smil() {
	}
	
	/**
	 * @param courseid
	 * @param smilpath
	 */
	public Smil(int courseid, String smilpath) {
		this.courseid = courseid;
		this.smilpath = smilpath;
	}

	/**
	 * Returns a string representation of the object
	 */
	public String toString() {
		return "[" + courseid + "," + smilpath + "]";
	}
	
	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * @return the smilpath
	 */
	public String getSmilpath() {
		return smilpath;
	}
	
	
	
	
}
