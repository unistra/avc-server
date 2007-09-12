package org.ulpmm.univrav.entities;

public class Slide {
	
	private int slideid;
	private int courseid;
	private String slideuri;
	private int slidetime;
	
	/**
	 * @param slideid
	 * @param courseid
	 * @param slideuri
	 * @param slidetime
	 */
	public Slide(int slideid, int courseid, String slideuri, int slidetime) {
		this.slideid = slideid;
		this.courseid = courseid;
		this.slideuri = slideuri;
		this.slidetime = slidetime;
	}

	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * @return the slideid
	 */
	public int getSlideid() {
		return slideid;
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
