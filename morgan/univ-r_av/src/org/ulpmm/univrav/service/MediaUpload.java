package org.ulpmm.univrav.service;

import org.apache.commons.fileupload.FileItem;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;

public class MediaUpload extends Thread {
	
	private IDatabase db;
	private IFileSystem fs;
	private Course c;
	private FileItem mediaFile;
	private IService service;
	private String rssName;
	private String rssFolderPath;
	private String rssTitle;
	private String rssDescription;
	private String serverUrl;
	private String rssImageUrl;
	private String recordedInterfaceUrl;
	private String language;
	private boolean hq;
	

	/**
	 * 
	 * @param db
	 * @param fs
	 * @param c
	 * @param mediaFile
	 * @param service
	 * @param rssFolderPath
	 * @param rssName
	 * @param rssTitle
	 * @param rssDescription
	 * @param serverUrl
	 * @param rssImageUrl
	 * @param recordedInterfaceUrl
	 * @param language
	 * @param hq High Quality
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
