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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + levelid;
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
		Level other = (Level) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (levelid != other.levelid)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
