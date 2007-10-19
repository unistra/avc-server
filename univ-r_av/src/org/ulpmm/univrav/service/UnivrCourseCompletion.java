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

	/**
	 * @param db
	 * @param fs
	 * @param c
	 * @param courseArchive
	 */
	public UnivrCourseCompletion(IDatabase db, IFileSystem fs, Course c,
			String courseArchive) {
		super();
		this.db = db;
		this.fs = fs;
		this.c = c;
		this.courseArchive = courseArchive;
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
	}
	
	
}
