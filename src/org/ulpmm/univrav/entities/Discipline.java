package org.ulpmm.univrav.entities;

/**
 * Class for discipline entity
 * 
 * @author morgan
 *
 */
public class Discipline {
	
	/** the discipline id */
	private int disciplineid;
	
	/** code of the component */
	private String codecomp;
	
	/** name of the component */
	private String namecomp;
	
	/** code of the domain */
	private String codedom;
	
	/** name of the domain */
	private String namedom;
	
	/**
	 * Default constructor
	 */
	public Discipline() {}
	
	/**
	 * constructor : create discipline
	 * @param disciplineid the discipline id
	 * @param codecomp code of the component
	 * @param namecomp name of the component
	 * @param codedom code of the domain
	 * @param namedom name of the domain
	 */
	public Discipline(int disciplineid, String codecomp, String namecomp, String codedom, String namedom) {
		this.disciplineid=disciplineid;
		this.codecomp=codecomp;
		this.namecomp=namecomp;
		this.codedom=codedom;
		this.namedom=namedom;
	}

	/**
	 * Gets the discipline id
	 * @return the discipline id
	 */
	public int getDisciplineid() {
		return disciplineid;
	}
	
	/**
	 * Sets the discipline id
	 * @param disciplineid the id
	 */
	public void setDisciplineid(int disciplineid) {
		this.disciplineid = disciplineid;
	}

	/**
	 * Gets the component code
	 * @return the component code
	 */
	public String getCodecomp() {
		return codecomp;
	}

	/**
	 * Sets the component code
	 * @param codecomp the component code
	 */
	public void setCodecomp(String codecomp) {
		this.codecomp = codecomp;
	}

	/**
	 * Gets the component name
	 * @return the component name
	 */
	public String getNamecomp() {
		return namecomp;
	}

	/**
	 * Sets the component name
	 * @param namecomp the component name
	 */
	public void setNamecomp(String namecomp) {
		this.namecomp = namecomp;
	}

	/**
	 * Gets the domain code
	 * @return the domain code
	 */
	public String getCodedom() {
		return codedom;
	}

	/**
	 * Sets the domain code
	 * @param codedom the domain code
	 */
	public void setCodedom(String codedom) {
		this.codedom = codedom;
	}

	/**
	 * Gets the domain name
	 * @return the domain name
	 */
	public String getNamedom() {
		return namedom;
	}

	/**
	 * Sets the domain name
	 * @param namedom the domain name
	 */
	public void setNamedom(String namedom) {
		this.namedom = namedom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codecomp == null) ? 0 : codecomp.hashCode());
		result = prime * result + ((codedom == null) ? 0 : codedom.hashCode());
		result = prime * result + disciplineid;
		result = prime * result
				+ ((namecomp == null) ? 0 : namecomp.hashCode());
		result = prime * result + ((namedom == null) ? 0 : namedom.hashCode());
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
		Discipline other = (Discipline) obj;
		if (codecomp == null) {
			if (other.codecomp != null)
				return false;
		} else if (!codecomp.equals(other.codecomp))
			return false;
		if (codedom == null) {
			if (other.codedom != null)
				return false;
		} else if (!codedom.equals(other.codedom))
			return false;
		if (disciplineid != other.disciplineid)
			return false;
		if (namecomp == null) {
			if (other.namecomp != null)
				return false;
		} else if (!namecomp.equals(other.namecomp))
			return false;
		if (namedom == null) {
			if (other.namedom != null)
				return false;
		} else if (!namedom.equals(other.namedom))
			return false;
		return true;
	}
	
}
