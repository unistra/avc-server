package org.ulpmm.univrav.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Tag;

/**
 * This thread is used to add a course
 * 
 * @author morgan
 *
 */
public class CourseAddition extends Thread {
	
	/** Database interface */
	private IDatabase db;
	
	/** FileSystem interface */
	private IFileSystem fs;
	
	/** The course */
	private Course c;
	
	/** the media's name */
	private String courseArchive;
	
	/** List of tags **/
	private String tags;
	
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
	

	/**
	 * CourseAddition's constructor
	 * 
	 * @param db Database interface
	 * @param fs FileSystem interface
	 * @param c The course
	 * @param courseArchive the media's name
	 * @param tags tags list
	 * @param service Service interface
	 * @param rssFolderPath the rss folder path
	 * @param rssName The rss name
	 * @param rssTitle the rss title
	 * @param rssDescription the rss description
	 * @param serverUrl the url of the server
	 * @param rssImageUrl the url of the rss image
	 * @param recordedInterfaceUrl the url of the recorded interface
	 * @param language the language
	 */
	public CourseAddition(IDatabase db, IFileSystem fs, Course c, String courseArchive, String tags,
			IService service, String rssFolderPath, String rssName, String rssTitle, 
			String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language) {
		
		super();
		this.db = db;
		this.fs = fs;
		this.c = c;
		this.courseArchive = courseArchive;
		this.tags=tags;
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
		db.addCourse(c);
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
			db.addSlide(new Slide(c.getCourseid(), Math.round(Float.parseFloat(list.get(i)))));
		
		// Adding tags
		if(tags!=null && !tags.equals("")) {
			// ADD TAGS		
			List<String> listTmp=new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(tags);
			String token = null;
			while (st.hasMoreTokens()) {
				token = st.nextToken();
				if(!listTmp.contains(token)) {
					service.addTag(new Tag(0, //is not used
						token, // the tag
						c.getCourseid()) // the course
					);
					listTmp.add(token);
				}
			}
			listTmp = null;
			st = null;
			token = null;
		}
		
		/* Generation of the RSS files */
		if( c.getGenre() == null)
			service.generateRss(c, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
	}
	
	
}
