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
		
}
