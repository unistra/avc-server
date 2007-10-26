package org.ulpmm.univrav.service;

import java.util.ArrayList;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;

public class UnivrCourseCompletion extends Thread {
	
	private IDatabase db;
	private IFileSystem fs;
	private Course c;
	private String courseArchive;
	private IService service;
	private String rssName;
	private String rssFolderPath;
	private String rssTitle;
	private String rssDescription;
	private String serverUrl;
	private String rssImageUrl;
	private String recordedInterfaceUrl;
	private String language;

	/**
	 * @param db
	 * @param fs
	 * @param c
	 * @param courseArchive
	 */
	public UnivrCourseCompletion(IDatabase db, IFileSystem fs, Course c, String courseArchive, 
			IService service, String rssFolderPath, String rssName, String rssTitle, 
			String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language) {
		
		super();
		this.db = db;
		this.fs = fs;
		this.c = c;
		this.courseArchive = courseArchive;
		this.service = service;
		this.rssFolderPath = rssFolderPath;
		this.rssName = rssName;
		this.rssTitle = rssTitle;
		this.rssDescription = rssDescription;
		this.serverUrl = serverUrl;
		this.rssImageUrl = rssImageUrl;
		this.recordedInterfaceUrl = recordedInterfaceUrl;
		this.language = language;
	}

	/**
	 * 
	 */
	public void run() {
		fs.addCourse(c, courseArchive);
		db.modifyCourse(c);
		ArrayList<String> list = fs.getTimecodes();
		
		int time;
		/* Determines if the times of the slides must be changed or not */
		if( c.getTiming().equals("n") ) {
			time = 1;
		}
		else if( c.getTiming().equals("n-1") ){
			time = 2;
		}
		else
			time = 2;
		
		for( int i = 0 ; i< list.size() - (time-1) ; i++)
			db.addSlide(new Slide(c.getCourseid(),(int) Float.parseFloat(list.get(i))));
		
		/* Generation of the RSS files */
		if( c.getGenre() == null)
			service.generateRss(c, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
	}
	
	
}
