package org.ulpmm.univrav.entities;

/**
 * 
 * @author morgan
 *
 */
public class Tag {
	
	private int tagid;
	private String tag;
	private int courseid;
	
	public Tag() {}
	
	public Tag (int tagid, String tag, int courseid) {
		this.tagid=tagid;
		this.tag=tag;
		this.courseid=courseid;
	}
	
	public int getTagid() {
		return tagid;
	}
	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

}
