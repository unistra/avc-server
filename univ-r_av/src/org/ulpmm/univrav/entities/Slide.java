package org.ulpmm.univrav.entities;

public class Slide {
	
	private int courseid;
	private String slideuri;
	private int slidetime;
	
	/**
	 * Default constructor
	 */
	public Slide() {
	}
	
	/**
	 * @param courseid
	 * @param slideuri
	 * @param slidetime
	 */
	public Slide(int courseid, String slideuri, int slidetime) {
		this.courseid = courseid;
		this.slideuri = slideuri;
		this.slidetime = slidetime;
	}
	
	/**
	 * Returns a string representation of the object
	 */
	public String toString() {
		return "[" + this.courseid + "," + this.slideuri + "," + this.slidetime + "]";
	}

	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * @return the slidetime
	 */
	public int getSlidetime() {
		return slidetime;
	}

	/**
	 * @return the slideuri
	 */
	public String getSlideuri() {
		return slideuri;
	}
}
