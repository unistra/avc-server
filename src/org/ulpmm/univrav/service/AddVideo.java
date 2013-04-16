package org.ulpmm.univrav.service;

import org.apache.commons.fileupload.FileItem;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Job;


public class AddVideo extends Thread {
	
	/** FileSystem interface */
	private IFileSystem fs;
	
	/** The course */
	private Course c;
	
	/** the media's name */
	private FileItem mediaFile;
	
	/** Service interface */
	private IService service;
	
	/** the url of the server */
	private String serverUrl;	

	/** true if medias encodage is separated */
	private boolean sepEnc;
	
	/** the course folder */
	private String coursesFolder;
	
	/** the offset */
	private Integer offset;
	
	
	
	/**
	 * Constructor of replace media
	 * 
	 * @param fs the file system
	 * @param c the course
	 * @param offset the offset
	 * @param mediaFile the media file
	 * @param service the service
	 * @param serverUrl the server url 
	 * @param sepEnc separate encodage
	 * @param coursesFolder the course folder
	 */
	public AddVideo(IFileSystem fs, Course c, Integer offset, FileItem mediaFile, IService service, String serverUrl, boolean sepEnc, String coursesFolder) {
		super();
		this.fs = fs;
		this.c = c;
		this.offset = offset;
		this.mediaFile = mediaFile;
		this.service = service;
		this.serverUrl = serverUrl;
		this.sepEnc = sepEnc;
		this.coursesFolder = coursesFolder;
		
	}
	
	
	
	/**
	 * The process to add additional video
	 */
	public void run() {
				
		String fileName = mediaFile.getName();
		/* Used to fix full path problem with IE */
		if( fileName.indexOf("\\") != -1 ) {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,fileName.length());
			mediaFile.setFieldName(fileName);
		}
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1,fileName.length()).toLowerCase();
				
		//Get the new mediatype
		int mediatype = Course.typeAddVideo;
						
		// Set the offset
		c.setSlidesoffset(offset);
		service.modifyCourse(c);
		
		// move file
		fs.moveAddVideo(c, mediaFile, fileName, extension);
		
		// get the previous addv job
		String type = "ADDV";
		Job j = service.getJob(c.getCourseid(), type);
				
		if(j!=null) {
			// Modify the original job
			j.setExtension(extension);
			j.setMediatype(mediatype);
			j.setStatus("waiting");
			service.modifyJob(j);
		}
		else {
			// create job
			service.createJob(c, mediatype, type, extension, coursesFolder);
		}
		
		// If medias encodage isnt separated
		if(!sepEnc) {
			String job_line =c.getCourseid()+":"+"waiting"+":"+mediatype+":" + type + ":"+c.getMediafolder()+":"+extension;
			service.modifyJobStatus(c.getCourseid(), "processing", type);
			service.launchJob(serverUrl, job_line);				
		}
		
	}
		
}
