package org.ulpmm.univrav.service;

import java.util.ArrayList;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.dao.IUnivrDao;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Univr;

/**
 * This thread is used to the completion of an univr course
 * 
 * @author morgan
 *
 */
public class UnivrCourseCompletion extends Thread {
	
	/** Database interface */
	private IDatabase db;
	
	/** FileSystem interface */
	private IFileSystem fs;
	
	/** UnivrDao interface */
	private IUnivrDao ud;
	
	/** The course */
	private Course c;
	
	/** the univr */
	private Univr u;
	
	/** the media's name */
	private String courseArchive;
	
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
	
	/** the RSS category */
	private String rssCategory;
	
	/** the url of the recorded interface */
	private String recordedInterfaceUrl;
	
	/** the language */
	private String language;
	
	/** The itunes author */
	private String itunesAuthor;
	/** The itunes subtitle */
	private String itunesSubtitle;
	/** The itunes summary */
	private String itunesSummary;
	/** The itunes image */
	private String itunesImage;
	/** The itunes category */
	private String itunesCategory;
	/** The itunes keywords */
	private String itunesKeywords;

	/**
	 * UnivrCourseCompletion's constructor
	 * 
	 * @param db Database interface
	 * @param fs FileSystem interface
	 * @param ud UnivrDao interface
	 * @param c the course
	 * @param u the univr
	 * @param courseArchive the media's name
	 * @param service Service interface
	 * @param rssFolderPath the rss folder path
	 * @param rssName The rss name
	 * @param rssTitle the rss title
	 * @param rssDescription the rss description
	 * @param serverUrl the url of the server
	 * @param rssImageUrl the url of the rss image
	 * @param recordedInterfaceUrl the url of the recorded interface
	 * @param language the language
	 * @param rssCategory the category of the RSS file
	 * @param itunesAuthor The itunes author
	 * @param itunesSubtitle The itunes subtitle
	 * @param itunesSummary The itunes summary
	 * @param itunesImage The itunes image
	 * @param itunesCategory The itunes category
	 * @param itunesKeywords The itunes keywords
	 */
	public UnivrCourseCompletion(IDatabase db, IFileSystem fs, IUnivrDao ud, Course c, Univr u, String courseArchive, 
			IService service, String rssFolderPath, String rssName, String rssTitle, 
			String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language, String rssCategory, String itunesAuthor,
			String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords) {
		
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
		this.rssCategory = rssCategory;
		this.itunesAuthor = itunesAuthor;
		this.itunesCategory = itunesCategory;
		this.itunesImage = itunesImage;
		this.itunesKeywords = itunesKeywords;
		this.itunesSubtitle = itunesSubtitle;
		this.itunesSummary = itunesSummary;
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
			service.generateRss(c, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
	}
	
	
}
