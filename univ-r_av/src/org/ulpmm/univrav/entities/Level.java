package org.ulpmm.univrav.entities;


/**
 * Class for the Level entity.
 * 
 * @author morgan
 *
 */
public class Level {
	
	/** the level id */
	private int levelid;
	
	/** The code of the level */
	private String code;
	
	/** The name of the level */
	private String name;
	
	
	/**
	 * Contructor of Level class
	 * @param levelid the level id
	 * @param code the level code
	 * @param name the name code
	 */
	public Level(int levelid, String code, String name) {
		this.levelid = levelid;
		this.code = code;
		this.name = name;	
	}
	
	
	/**
	 * Gets the level id
	 * @return the level id
	 */
	public int getLevelid() {
		return levelid;
	}


	/**
	 * Sets the levelid
	 * @param levelid the level id
	 */
	public void setLevelid(int levelid) {
		this.levelid = levelid;
	}

	/**
	 * Gets level code
	 * @return level code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets level code
	 * @param code level code
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * Gets level name
	 * @return level name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets level name
	 * @param name the level name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
