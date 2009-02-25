package org.ulpmm.univrav.entities;

public class Slide {
	
	private int courseid;
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
	public Slide(int courseid, int slidetime) {
		this.courseid = courseid;
		this.slidetime = slidetime;
	}
	
	/**
	 * Returns a string representation of the object
	 */
	public String toString() {
		return Integer.toString(this.slidetime);
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
}
