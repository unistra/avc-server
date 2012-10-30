package org.ulpmm.univrav.service;

import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;


public class MediaRetag extends Thread {
	
	/** FileSystem interface */
	private IFileSystem fs;
	
	/** The course */
	private Course c;
	
	
	/**
	 * MediaRetag's constructor
	 * @param fs FileSystem interface
	 * @param c The course
	 */
	public MediaRetag(IFileSystem fs, Course c) {
		super();
		this.fs = fs;
		this.c = c;
	}
	
	
	
	/**
	 * The process to modify media tag
	 */
	public void run() {
		fs.mediaRetag(c);
	}

}
