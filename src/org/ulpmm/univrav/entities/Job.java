package org.ulpmm.univrav.entities;

/**
 * Class for job entity
 * 
 * @author morgan
 *
 */
public class Job {
	
	/** the job id*/
	private int jobid;
	
	/** the course id */
	private int courseid;
	
	/** the status (waiting,processing,done) */
	private String status;
	
	/** the course mediatype after encoding */
	private int mediatype;
	
	/** the course  type (CA,CV,MUA,MUV) */
	private String coursetype;
	
	/** the course path */
	private String mediapath;
	
	/** the extension of the file (input) */
	private String extension;
	
	
	/**
	 * Default constructor
	 */
	public Job() {}
	
	/**
	 * constructor : create job
	 * @param jobid the job id
	 * @param courseid the course id
	 * @param status the status
	 * @param mediatype the mediatype
	 * @param coursetype the coursetype
	 * @param mediapath the mediatype
	 * @param extension the extension of the file
	 */
	public Job(int jobid, int courseid, String status, int mediatype, String coursetype, String mediapath, String extension) {
		this.jobid = jobid;
		this.courseid = courseid;
		this.status = status;
		this.mediatype = mediatype;
		this.coursetype = coursetype;
		this.mediapath = mediapath;
		this.extension = extension;
	}

	/**
	 * Gets job id
	 * @return the job id
	 */
	public int getJobid() {
		return jobid;
	}

	/**
	 * Sets job id
	 * @param jobid the job id
	 */
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	/**
	 * Gets course id
	 * @return the course id
	 */
	public int getCourseid() {
		return courseid;
	}
	
	/**
	 * Sets course id
	 * @param courseid the course id
	 */
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	/**
	 * Gets the status
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status
	 * @param status the status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the mediatype
	 * @return the mediatype
	 */
	public int getMediatype() {
		return mediatype;
	}

	/**
	 * Sets the mediatype
	 * @param mediatype the mediatype
	 */
	public void setMediatype(int mediatype) {
		this.mediatype = mediatype;
	}

	/**
	 * Gets course type
	 * @return the course type
	 */
	public String getCoursetype() {
		return coursetype;
	}

	/**
	 * Sets course type
	 * @param coursetype the course type
	 */
	public void setCoursetype(String coursetype) {
		this.coursetype = coursetype;
	}

	/**
	 * Gets mediapath
	 * @return the mediapath
	 */
	public String getMediapath() {
		return mediapath;
	}

	/**
	 * Sets mediatype
	 * @param mediapath the mediatype
	 */
	public void setMediapath(String mediapath) {
		this.mediapath = mediapath;
	}

	/**
	 * Gets the extension
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the extension
	 * @param extension the extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseid;
		result = prime * result
				+ ((coursetype == null) ? 0 : coursetype.hashCode());
		result = prime * result
				+ ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + jobid;
		result = prime * result
				+ ((mediapath == null) ? 0 : mediapath.hashCode());
		result = prime * result + mediatype;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Job other = (Job) obj;
		if (courseid != other.courseid)
			return false;
		if (coursetype == null) {
			if (other.coursetype != null)
				return false;
		} else if (!coursetype.equals(other.coursetype))
			return false;
		if (extension == null) {
			if (other.extension != null)
				return false;
		} else if (!extension.equals(other.extension))
			return false;
		if (jobid != other.jobid)
			return false;
		if (mediapath == null) {
			if (other.mediapath != null)
				return false;
		} else if (!mediapath.equals(other.mediapath))
			return false;
		if (mediatype != other.mediatype)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
	

}
