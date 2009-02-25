package org.ulpmm.univrav.service;

import java.util.ArrayList;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.dao.IUnivrDao;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Univr;

public class UnivrCourseCompletion extends Thread {
	
	private IDatabase db;
	private IFileSystem fs;
	private IUnivrDao ud;
	private Course c;
	private Univr u;
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
	 * 
	 * @param db
	 * @param fs
	 * @param ud
	 * @param c
	 * @param u
	 * @param courseArchive
	 * @param service
	 * @param rssFolderPath
	 * @param rssName
	 * @param rssTitle
	 * @param rssDescription
	 * @param serverUrl
	 * @param rssImageUrl
	 * @param recordedInterfaceUrl
	 * @param language
	 */
	public UnivrCourseCompletion(IDatabase db, IFileSystem fs, IUnivrDao ud, Course c, Univr u, String courseArchive, 
			IService service, String rssFolderPath, String rssName, String rssTitle, 
			String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language) {
		
		super();
		this.db = db;
		this.fs = fs;
		this.ud = ud;
		this.c = c;
		this.u = u;
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
	 * The process to create a course inside a thread
	 */
	public void run() {
		fs.addCourse(c, courseArchive);
		db.modifyCourse(c);
		ArrayList<String> list = fs.getTimecodes(c.getMediaFolder());
		
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
		
		ud.publishCourse(u.getCourseid(), u.getGroupCode(),u.getEstablishment());
		
		/* Generation of the RSS files */
		if( c.getGenre() == null)
			service.generateRss(c, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
	}
	
	
}
