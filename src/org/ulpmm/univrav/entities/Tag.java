package org.ulpmm.univrav.entities;

/**
 * Class for the Tag entity
 * 
 * @author morgan
 *
 */
public class Tag {
	
	/** the tag's id */
	private int tagid;
	
	/** the tag (the word) */
	private String tag;
	
	/** the course's id of the tag */
	private int courseid;
	
	/**
	 * Default constructor
	 */
	public Tag() {}
	
	/**
	 * Tag's constructor
	 * 
	 * @param tagid the tag's id
	 * @param tag the tag (the word)
	 * @param courseid the course's id of the tag
	 */
	public Tag (int tagid, String tag, int courseid) {
		this.tagid=tagid;
		this.tag=tag;
		this.courseid=courseid;
	}
	
	/**
	 * Gets the tag's id 
	 * @return the tag's id
	 */
	public int getTagid() {
		return tagid;
	}
	
	/**
	 * Sets the tag's id
	 * @param tagid the tag's id
	 */
	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
	
	/**
	 * Gets the tag (the word)
	 * @return the tag 
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Sets the tag (the word)
	 * @param tag the tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	/**
	 * Gets the course's id
	 * @return the course's id
	 */
	public int getCourseid() {
		return courseid;
	}
	
	/**
	 * Sets the course's id
	 * @param courseid the course's id
	 */
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseid;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + tagid;
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
		Tag other = (Tag) obj;
		if (courseid != other.courseid)
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		if (tagid != other.tagid)
			return false;
		return true;
	}

}
