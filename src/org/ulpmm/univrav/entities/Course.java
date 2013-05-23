package org.ulpmm.univrav.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Course entity
 * 
 * @author morgan
 *
 */
public class Course {

	/** the course's id */
	private int courseid;
	
	/** the course's date */
	private Timestamp date;
	
	/** the course's type (audio or video) */
	private String type;
	
	/** the course's title */
	private String title;
	
	/** the course's description */
	private String description;
	
	/** the course's formation (discipline) */
	private String formation;
	
	/** the name of the author */
	private String name;
	
	/** the firstname of the author */
	private String firstname;
	
	/** the ip address of the amphi */
	private String ipaddress;
	
	/** the course's duration */
	private int duration;
	
	/** the course's genre (access code) */
	private String genre;
	
	/** the course's visibility */
	private boolean visible;
	
	/** number of consultations */
	private int consultations;
	
	/** the course's timing */
	private String timing;
			
	/** the user id */
	private Integer userid;
	
	/** the additional document name **/
	private String adddocname;
	
	/** the download indicator **/
	private boolean download;
	
	/** to protect course access with UDS account **/
	private boolean restrictionuds;
	
	/** media type **/
	private int mediatype;
	
	/** media type available **/
	public static final int typeFlash = 1;
	public static final int typeMp3 = 2;
	public static final int typeOgg = 4;
	public static final int typePdf = 8;
	public static final int typeZip = 16;
	public static final int typeVideoslide = 32;
	public static final int typeAdddoc = 64;
	public static final int typeHq = 128;
	public static final int typeVideoslideIpod = 256;
	public static final int typeSubtitles = 512;
	public static final int typeAddVideo = 1024;
	public static final int typeWebm = 2048;
	
	/** volume (filesystem) */
	private short volume;
	
	/** the mediaFolder. It's not a database column. It's used because it's convenient */
	private String mediafolder;
	
	/** the course's record date */
	private Timestamp recorddate; 
	
	/** the slide offset of addvideo **/
	private Integer slidesoffset;
	
	/**
	 * Default constructor
	 */
	public Course() {}

	/**
	 * constructor : creates a course by providing all the information
	 * 
	 * @param courseid the course's id
	 * @param date the course's date
	 * @param type the course's type (audio or video)
	 * @param title the course's title
	 * @param description the course's description
	 * @param formation the course's formation (discipline)
	 * @param name the name of the author
	 * @param firstname the firstname of the author
	 * @param ipaddress the ip address of the amphi
	 * @param duration the course's duration
	 * @param genre the course's genre (access code)
	 * @param visible the course's visibility
	 * @param consultations number of consultations
	 * @param timing the course's timing
	 * @param userid the high quality indicator
	 * @param adddocname additional document name
	 * @param download the download parameter
	 * @param restrictionuds the restriction uds
	 * @param mediatype media type
	 * @param volume the volume disk
	 * @param recorddate the course's record date
	 * @param slidesoffset the slides offest
	 */
	public Course(int courseid, Timestamp date, String type, String title, String description, String formation, String name, String firstname, String ipaddress, int duration, String genre, boolean visible, int consultations, String timing, Integer userid, String adddocname, boolean download, boolean restrictionuds, int mediatype, short volume, Timestamp recorddate, Integer slidesoffset) {
		this.courseid = courseid;
		this.date = date;
		this.type = type;
		this.title = title;
		this.description = description;
		this.formation = formation;
		this.name = name;
		this.firstname = firstname;
		this.ipaddress = ipaddress;
		this.duration = duration;
		this.genre = genre;
		this.visible = visible;
		this.consultations = consultations;
		this.timing = timing;
		this.userid=userid;
		this.adddocname=adddocname;
		this.download=download;
		this.restrictionuds=restrictionuds;
		this.mediatype=mediatype;
		this.volume=volume;
		this.mediafolder=FindMediaFolder();
		this.recorddate=recorddate;
		this.slidesoffset=slidesoffset;
	}
	
	

	/**
	 * Gets a string representation of the object
	 * @return a string representation of the object
	 */
	public String toString() {
		return "[" + courseid + "," + getDateString() + "," + type + "," + title + ","
			+ description + "," + formation + "," + name + "," + firstname + ","
			+ ipaddress + "," + getDurationString() + "," + genre + "," + visible + ","
			+ consultations + "," + timing + "]";
	}
	
	/**
	 * Gets number of consultations
	 * @return number of consultations
	 */
	public int getConsultations() {
		return consultations;
	}

	/**
	 * Gets the course's id
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * Gets the course's date
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	
	/**
	 * Gets a date string
	 * @return the date in the convenient String format
	 */
	public String getDateString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");	
		return sdf.format(date);
	}

	/**
	 * Gets the course's description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the course's duration
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Gets the course's duration in a string
	 * @return the duration in a String
	 */
	public String getDurationString() {
		String res = "";
		int durationHour = duration / 3600;
		int durationMinute = (duration % 3600) / 60 ;
		int durationSecond = ((duration % 3600) % 60) ;
		res += durationHour > 0 ? durationHour + "h" : "";
    	res += durationHour > 0 || durationMinute > 0 ? durationMinute + "min" : "";
    	res += durationSecond + "sec";
		return res;
	}
	
	/**
	 * Gets the course's duration in a string for iTunes tags
	 * @return the duration in a String
	 */
	public String getDurationStringItunes() {
		String res = "";
		int durationHour = duration / 3600;
		int durationMinute = (duration % 3600) / 60 ;
		int durationSecond = ((duration % 3600) % 60) ;
		res += durationHour > 0 ? durationHour + ":" : "";
    	res += durationHour > 0 || durationMinute > 0 ? durationMinute + ":" : "";
    	res += durationSecond;
		return res;
	}
	
	/**
	 * Gets the formation
	 * @return the formation
	 */
	public String getFormation() {
		return formation;
	}

	/**
	 * Gets the firstname of the author
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Gets the course genre
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Gets the ip address
	 * @return the ipaddress
	 */
	public String getIpaddress() {
		return ipaddress;
	}

	/**
	 * Gets the name of the author
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the course's timing
	 * @return the timing
	 */
	public String getTiming() {
		return timing;
	}

	/**
	 * Gets the course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the course's type
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the visibility of the course
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Gets the name used by the media files of this course
	 * @return the name used by the media files of this course
	 */
	public String getMediasFileName() {
		String mediasFileName=String.valueOf(courseid);
		return mediasFileName;
	}
	
	
	/**
	 * Sets the course's type
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the course's duration
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	
	/**
	 * Sets the date
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Sets the visibility
	 * @param visible the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Sets the course's timing
	 * @param timing the timing to set
	 */
	public void setTiming(String timing) {
		this.timing = timing;
	}
	
	/**
	 * Gets the user's id
	 * @return userid the user's id
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * Sets the course's id
	 * @param courseid the course's id
	 */
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	/**
	 * Sets the course's title
	 * @param title the course's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the course's description
	 * @param description the course's description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Sets the course's formation
	 * @param formation the course's formation
	 */
	public void setFormation(String formation) {
		this.formation = formation;
	}

	/**
	 * Sets the name of the author
	 * @param name author's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the firstname of the author
	 * @param firstname author's firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Sets the ip address
	 * @param ipaddress the ip address of the amphi
	 */
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	/**
	 * Sets the course's genre
	 * @param genre the course's genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * Sets number of consultations
	 * @param consultations number of consultations
	 */
	public void setConsultations(int consultations) {
		this.consultations = consultations;
	}

	/**
	 * Sets user's id 
	 * @param userid the user's id
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	/**
	 * Gets additional document name
	 * @return the additional document name
	 */
	public String getAdddocname() {
		return adddocname;
	}

	/**
	 * Sets additional document name
	 * @param adddocname
	 */
	public void setAdddocname(String adddocname) {
		this.adddocname = adddocname;
	}

	/**
	 * Gets the download indicator
	 * @return download indicator
	 */
	public boolean isDownload() {
		return download;
	}
	
	/**
	 * Sets the download indicator
	 * @param download
	 */
	public void setDownload(boolean download) {
		this.download = download;
	}	
	
	/**
	 * Gets the restriction uds of the amphi
	 * @return restrictionuds the restriction uds
	 */
	public boolean isRestrictionuds() {
		return restrictionuds;
	}

	/**
	 * Sets the restriction uds of the amphi
	 * @param restrictionuds the restriction uds
	 */
	public void setRestrictionuds(boolean restrictionuds) {
		this.restrictionuds = restrictionuds;
	}

	/**
	 * Gets media type 
	 * @return mediatype media type
	 */
	public int getmediatype() {
		return mediatype;
	}

	/**
	 * Sets the media type
	 * @param mediatype media type
	 */
	public void setmediatype(int mediatype) {
		this.mediatype = mediatype;
	}
	
	/**
	 * Check the media availability
	 * @param type media type(mp3,ogg,...)
	 * @return true if the media is available
	 */
	public boolean isAvailable(String type) {
		if(type == null || type.equals("")) return false;
		else if(type.equals("flash")) return ((typeFlash & mediatype) > 0);
		else if(type.equals("mp3")) return ((typeMp3 & mediatype) > 0);
		else if(type.equals("ogg")) return ((typeOgg & mediatype) > 0);
		else if(type.equals("pdf")) return ((typePdf & mediatype) > 0);
		else if(type.equals("zip")) return ((typeZip & mediatype) > 0);
		else if(type.equals("videoslide")) return ((typeVideoslide & mediatype) > 0);
		else if(type.equals("adddoc")) return ((typeAdddoc & mediatype) > 0);
		else if(type.equals("hq")) return ((typeHq & mediatype) > 0);
		else if(type.equals("video")) return ((typeHq & mediatype) > 0);
		else if(type.equals("videoslideipod")) return ((typeVideoslideIpod & mediatype) > 0);
		else if(type.equals("subtitles")) return ((typeSubtitles & mediatype) > 0);
		else if(type.equals("addvideo")) return ((typeAddVideo & mediatype) > 0);
		else if(type.equals("webm")) return ((typeWebm & mediatype) > 0);
		else if(type.equals("html5")) {
			if(this.type.equals("audio"))
				return ((typeOgg & mediatype) > 0) && ((typeMp3 & mediatype) > 0);
			else
				return ((typeWebm & mediatype) > 0);
		}
		else return false;
	}
	
	/**
	 * Gets medias availables
	 * @return lst list of medias
	 */
	public List<String> getMedias() {
		List<String> lst = new ArrayList<String>();
		if ((typeFlash & mediatype) > 0) lst.add("flash"); 
		if ((typeMp3 & mediatype) > 0) lst.add("mp3");
		if ((typeOgg & mediatype) > 0) lst.add("ogg");
		if ((typePdf & mediatype) > 0) lst.add("pdf") ;
		if ((typeZip & mediatype) > 0) lst.add("zip") ;
		if ((typeVideoslide & mediatype) > 0) lst.add("videoslide") ;
		if ((typeAdddoc & mediatype) > 0) lst.add("adddoc") ;
		if ((typeHq & mediatype) > 0) lst.add("hq") ;		
		if ((typeVideoslideIpod & mediatype) > 0) lst.add("videoslideipod") ;	
		if ((typeSubtitles & mediatype) > 0) lst.add("subtitles") ;	
		if ((typeAddVideo & mediatype) > 0) lst.add("addvideo") ;	
		if ((typeWebm & mediatype) > 0) lst.add("webm") ;
		if(this.type.equals("audio") && ((typeOgg & mediatype) > 0) && ((typeMp3 & mediatype) > 0))
			lst.add("html5") ;
		else if (this.type.equals("video") && ((typeWebm & mediatype) > 0))
			lst.add("html5") ;
		return lst;
	}

	/**
	 * Gets the volume (filesystem)
	 * @return volume the volume
	 */
	public short getVolume() {
		return volume;
	}

	/**
	 * Gets the volume (filesystem)
	 * @param volume the volume
	 */
	public void setVolume(short volume) {
		this.volume = volume;
	}
	
	/**
	 * Find the media folder for constructor
	 * @return the media folder
	 */
	private String FindMediaFolder() {
		String mediafolder = "";
		String idformat = String.format("%08d", this.courseid);
				
		if(idformat.length()<=8)
			mediafolder = this.volume+"/"+idformat.substring(0, 2)+"/"+idformat.substring(2, 4)+ "/"+idformat.substring(4, 6)+"/"+idformat.substring(6, 8);
		else 
			mediafolder = this.volume + "/" + this.courseid;
			
		return mediafolder;
	}

	/**
	 * Gets the mediafolder
	 * @return the mediafolder
	 */
	public String getMediafolder() {
		return mediafolder;
	}

	/**
	 * return course's record date
	 * @return timestamp
	 */
	public Timestamp getRecorddate() {
		return recorddate;
	}

	/**
	 * set course's record date
	 * @param recorddate the timestamp
	 */
	public void setRecorddate(Timestamp recorddate) {
		this.recorddate = recorddate;
	}

	
	/**
	 * Gets a record date string
	 * @return the date in the convenient String format
	 */
	public String getRecordDateString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");	
		return sdf.format(recorddate);
	}

	/** 
	 * Get the slide offset
	 * @return the silde offset
	 */
	public Integer getSlidesoffset() {
		return slidesoffset;
	}

	/**
	 * set the slideoffset
	 * @param slidesoffset the slideoffset
	 */
	public void setSlidesoffset(Integer slidesoffset) {
		this.slidesoffset = slidesoffset;
	}
	
	/**
	 * Return true if the record is an audio record from the client
	 * @return boolean
	 */
	public boolean isAudioClient() {
		return this.type.equals("audio") && this.isAvailable("zip");
	}
	
	/**
	 * Return true if the record is a video record from the client
	 * @return boolean
	 */
	public boolean isVideoClient() {
		return this.type.equals("video") && this.isAvailable("zip");
	}
	
	/**
	 * Return true if the record is an audio from the upload page (myspace)
	 * @return boolean
	 */
	public boolean isAudioUpload() {
		return this.type.equals("audio") && !this.isAvailable("zip");
	}
	
	/**
	 * Return true if the record is a video from the upload page (myspace)
	 * @return boolean
	 */
	public boolean isVideoUpload() {
		return this.type.equals("video") && !this.isAvailable("zip");
	}
	
}
