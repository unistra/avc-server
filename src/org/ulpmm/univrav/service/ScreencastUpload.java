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
public class ScreencastUpload extends Thread {
	
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
		
	/** the url of the server */
	private String serverUrl;	

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
	 * @param serverUrl the url of the server
	 * @param sepEnc true if medias encodage is separated
	 * @param coursesFolder the courses folder
	 */
	public ScreencastUpload(IDatabase db, IFileSystem fs, Course c, FileItem mediaFile, String tags,
			IService service, String serverUrl, boolean sepEnc, String coursesFolder) {
		
		super();
		this.db = db;
		this.fs = fs;
		this.c = c;
		this.mediaFile = mediaFile;
		this.tags = tags;
		this.service = service;
		this.serverUrl = serverUrl;
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
			StringTokenizer st = new StringTokenizer(tags," ,;");
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
				
		//int mediatype = Course.typeFlash + Course.typeMp3 + Course.typeOgg + Course.typeHq + Course.typeWebm;
		int mediatype = Course.typeMp3 + Course.typeHq + Course.typeWebm;
		
		// If medias encodage isnt separated
		if(!sepEnc) {
	
			String type = "CSC";
			service.createJob(c,mediatype,type,extension,coursesFolder);
			
			String job_line =c.getCourseid()+":"+"waiting"+":"+mediatype+":" + type + ":"+c.getMediafolder()+":"+extension;
			service.modifyJobStatus(c.getCourseid(), "processing", type);
			service.launchJob(serverUrl, job_line);				
		}
		else {
			String type = "CSC";
			service.createJob(c,mediatype,type,extension,coursesFolder);
		}
	}
	
	
}
