package org.ulpmm.univrav.entities;

/**
 * Class for the Selection entity
 * 
 * @author morgan
 *
 */
public class Selection {

	/** the position of the selection */
	private int position;
	
	/** the course's id for the list "Selection" of the home page */
	private Integer idcourseselection;
	
	/** the name of the formation for the list "Collection" of the home page" (when position=0) */
	private String formationcollection;
	
	/**
	 * Default constructor
	 */
	public Selection() {}
	
	/**
	 * Selection's constructor 
	 * 
	 * @param position the position of the selection
	 * @param idcourseselection the course's id for the list "Selection" of the home page
	 * @param formationcollection the name of the formation for the list "Collection" of the home page" (when position=0)
	 */
	public Selection(int position,Integer idcourseselection, String formationcollection) {
		this.position=position;
		this.idcourseselection=idcourseselection;
		this.formationcollection=formationcollection;
	}

	/**
	 * Gets the position
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the position
	 * @param position the position of the selection
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Gets the course's id of the selection
	 * @return the course's id of the selection
	 */
	public Integer getIdcourseselection() {
		return idcourseselection;
	}

	/**
	 * Sets the course's id of the selection
	 * @param idcourseselection the course's id of the selection
	 */
	public void setIdcourseselection(Integer idcourseselection) {
		this.idcourseselection = idcourseselection;
	}

	/**
	 * Gets the formation's name of the collection
	 * @return the formation's name of the collection
	 */
	public String getFormationcollection() {
		return formationcollection;
	}

	/**
	 * Sets the formation's name of the collection
	 * @param formationcollection the formation's name of the collection
	 */
	public void setFormationcollection(String formationcollection) {
		this.formationcollection = formationcollection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((formationcollection == null) ? 0 : formationcollection
						.hashCode());
		result = prime
				* result
				+ ((idcourseselection == null) ? 0 : idcourseselection
						.hashCode());
		result = prime * result + position;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Selection other = (Selection) obj;
		if (formationcollection == null) {
			if (other.formationcollection != null)
				return false;
		} else if (!formationcollection.equals(other.formationcollection))
			return false;
		if (idcourseselection == null) {
			if (other.idcourseselection != null)
				return false;
		} else if (!idcourseselection.equals(other.idcourseselection))
			return false;
		if (position != other.position)
			return false;
		return true;
	}

	
	
}
