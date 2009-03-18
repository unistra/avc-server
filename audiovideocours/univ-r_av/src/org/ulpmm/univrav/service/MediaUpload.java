package org.ulpmm.univrav.service;

import org.apache.commons.fileupload.FileItem;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;

/**
 * This thread is used to upload a media
 * 
 * @author morgan
 *
 */
public class MediaUpload extends Thread {
	
	/** Database interface */
	private IDatabase db;
	
	/** FileSystem interface */
	private IFileSystem fs;
	
	/** The course */
	private Course c;
	
	/** the media's name */
	private FileItem mediaFile;
	
	/** Service interface */
	private IService service;
	
	/** The rss name */
	private String rssName;
	
	/** the rss folder path */
	private String rssFolderPath;
	
	/** the rss title */
	private String rssTitle;
	
	/** the rss description */
	private String rssDescription;
	
	/** the url of the server */
	private String serverUrl;
	
	/** the url of the rss image */
	private String rssImageUrl;
	
	/** the url of the recorded interface */
	private String recordedInterfaceUrl;
	
	/** the language */
	private String language;

	/** the high quality indicator **/
	private boolean hq;
	

	/**
	 * MediaUpload's constructor
	 * 
	 * @param db Database interface
	 * @param fs FileSystem interface
	 * @param c The course
	 * @param mediaFile the media's name
	 * @param service Service interface
	 * @param rssFolderPath the rss folder path
	 * @param rssName The rss name
	 * @param rssTitle the rss title
	 * @param rssDescription the rss description
	 * @param serverUrl the url of the server
	 * @param rssImageUrl the url of the rss image
	 * @param recordedInterfaceUrl the url of the recorded interface
	 * @param language the language
	 * @param hq the high quality indicator
	 */
	public MediaUpload(IDatabase db, IFileSystem fs, Course c, FileItem mediaFile, 
			IService service, String rssFolderPath, String rssName, String rssTitle, 
			String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language,boolean hq) {
		
		super();
		this.db = db;
		this.fs = fs;
		this.c = c;
		this.mediaFile = mediaFile;
		this.service = service;
		this.rssFolderPath = rssFolderPath;
		this.rssName = rssName;
		this.rssTitle = rssTitle;
		this.rssDescription = rssDescription;
		this.serverUrl = serverUrl;
		this.rssImageUrl = rssImageUrl;
		this.recordedInterfaceUrl = recordedInterfaceUrl;
		this.language = language;
		this.hq=hq;
	}

	/**
	 * The process to create a course inside a thread
	 */
	public void run() {
		fs.mediaUpload(c, mediaFile,hq);
		db.addCourse(c);
		
		/* Generation of the RSS files */
		if( c.getGenre() == null)
			service.generateRss(c, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
	}
	
	
}
