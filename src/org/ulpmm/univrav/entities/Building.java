package org.ulpmm.univrav.entities;

import java.util.Collections;
import java.util.List;

/**
 * Class for the Building entity
 * 
 * @author morgan
 *
 */
public class Building {
	
	/** the building's id */
	private int buildingid;
	 
	/** the building's name */
	private String name;
	
	/** the name of the image file */
	private String imageFile;
	
	/** Amphis of the building */
	private List<Amphi> amphis =  null;
	
	/**
	 * Building's constructor
	 * 
	 * @param buildingid the building's id
	 * @param name the building's name
	 * @param imageFile the name of the image file
	 */
	public Building(int buildingid, String name, String imageFile) {
		super();
		this.buildingid = buildingid;
		this.name = name;
		this.imageFile = imageFile;
	}

	/**
	 * Gets the building's id
	 * @return the buildingid
	 */
	public int getBuildingid() {
		return buildingid;
	}

	/**
	 * Gets the building's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the name of the image file
	 * @return the imageFile
	 */
	public String getImageFile() {
		return imageFile;
	}

	/**
	 * Gets amphis of the building
	 * @return the amphis sort by name
	 */
	public List<Amphi> getAmphis() {
		Collections.sort(amphis);
		return amphis;
	}

	/**
	 * Sets the list of amphis
	 * @param amphis the amphis to set
	 */
	public void setAmphis(List<Amphi> amphis) {
		this.amphis = amphis;
	}
	
	/**
	 * Gets a String representation of this object
	 * returns a String representation of this object
	 */
	public String toString() {
		return this.name;
	}
	
	/**
	 * set the building id
	 * @param buildingid the building id
	 */
	public void setBuildingid(int buildingid) {
		this.buildingid = buildingid;
	}

	/**
	 * set the name
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * set the image file
	 * @param imageFile the image file
	 */
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + buildingid;
		result = prime * result
				+ ((imageFile == null) ? 0 : imageFile.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Building other = (Building) obj;
		if (buildingid != other.buildingid)
			return false;
		if (imageFile == null) {
			if (other.imageFile != null)
				return false;
		} else if (!imageFile.equals(other.imageFile))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
		
}
