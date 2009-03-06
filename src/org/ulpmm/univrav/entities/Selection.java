package org.ulpmm.univrav.entities;

/**
 * 
 * @author morgan
 *
 */
public class Selection {

	private int position;
	private Integer idcourseselection;
	private String formationcollection;
	
	public Selection() {}
	
	public Selection(int position,Integer idcourseselection, String formationcollection) {
		this.position=position;
		this.idcourseselection=idcourseselection;
		this.formationcollection=formationcollection;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Integer getIdcourseselection() {
		return idcourseselection;
	}

	public void setIdcourseselection(Integer idcourseselection) {
		this.idcourseselection = idcourseselection;
	}

	public String getFormationcollection() {
		return formationcollection;
	}

	public void setFormationcollection(String formationcollection) {
		this.formationcollection = formationcollection;
	}

	
	
}
