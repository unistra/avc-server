package org.ulpmm.univrav.service;

import java.util.ArrayList;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;

public class CourseAddition extends Thread {
	
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
	public CourseAddition(IDatabase db, IFileSystem fs, Course c,
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
		db.addCourse(c);
		ArrayList<String> list = fs.getTimecodes();
		for( int i = 0 ; i< list.size() ; i++)
			db.addSlide(new Slide(c.getCourseid(),"XXXXXXXXXX",(int) Float.parseFloat(list.get(i))));
	}
	
	
}
