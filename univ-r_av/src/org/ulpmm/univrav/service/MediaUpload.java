package org.ulpmm.univrav.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.fileupload.FileItem;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Tag;

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
	/** true if medias encodage is separated */
	private boolean sepEnc;
	/** the course folder */
	private String coursesFolder;
	

	/**
	 * MediaUpload's constructor
	 * 
	 * @param db Database interface
	 * @param fs FileSystem interface
	 * @param c The course
	 * @param mediaFile the media's name
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
	 * @param rssCategory the category of the RSS file
	 * @param itunesAuthor The itunes author
	 * @param itunesSubtitle The itunes subtitle
	 * @param itunesSummary The itunes summary
	 * @param itunesImage The itunes image
	 * @param itunesCategory The itunes category
	 * @param itunesKeywords The itunes keywords
	 * @param sepEnc true if medias encodage is separated
	 * @param coursesFolder the courses folder
	 */
	public MediaUpload(IDatabase db, IFileSystem fs, Course c, FileItem mediaFile, String tags,
			IService service, String rssFolderPath, String rssName, String rssTitle, 
			String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language, String rssCategory, String itunesAuthor,
			String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords, boolean sepEnc,String coursesFolder) {
		
		super();
		this.db = db;
		this.fs = fs;
		this.c = c;
		this.mediaFile = mediaFile;
		this.tags = tags;
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
		this.sepEnc = sepEnc;
		this.coursesFolder = coursesFolder;
	}

	/**
	 * The process to create a course inside a thread
	 */
	public void run() {
		
		String fileName = mediaFile.getName();
		/* Used to fix full path problem with IE */
		if( fileName.indexOf("\\") != -1 ) {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,fileName.length());
			mediaFile.setFieldName(fileName);
		}
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1,fileName.length()).toLowerCase();
		
		// Create flash course in filesystem
		fs.mediaUpload(c, mediaFile, fileName, extension);
		// add course into database for direct flash access
		db.addCourse(c);
		
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
				
		int mediatype = Course.typeFlash+Course.typeMp3+Course.typeOgg+((c.getType().equals("video"))?Course.typeHq:0)+(c.getType().equals("video")?Course.typeZip:0);
		
		// If medias encodage isnt separated
		if(!sepEnc) {

			// Generate all medias (can be long)
			fs.generateMediaUploadMedias(c,extension);

			// Modify mediatype
			// Gets the mediatype from database (if an upload document has been added during the medias generation)
			c.setmediatype(mediatype);
			db.modifyCourseMediatype(c.getCourseid(),mediatype);

			/* Generation of the RSS files */
			if( c.getGenre() == null)
				service.generateRss(c, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		}
		else {
			String type = c.getType().equals("video") ? "MUV" : "MUA";
			service.createJob(c,mediatype,type,extension,coursesFolder);
		}
	}
	
	
}
