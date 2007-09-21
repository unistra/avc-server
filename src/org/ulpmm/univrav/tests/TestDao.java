package org.ulpmm.univrav.tests;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.ulpmm.univrav.dao.DaoImpl;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Smil;

import junit.framework.TestCase;

public class TestDao extends TestCase {

	private static DaoImpl dao = new DaoImpl();
	
	public void testCourse() {
		/* Adds a new course */
		Course c = new Course(1,new Timestamp(new Date().getTime()),"audio","Test Junit 1","Test d'ajout de cours","essai","Kieffer","Laurent",
				"127.0.0.1",5,"suppression",true,0,"n-1");
		dao.addCourse(c);
		c = new Course(2,new Timestamp(new Date().getTime()),"video","Test Junit 2","Test d'ajout de cours","test","Kieffer","Laurent",
				"127.0.0.1",5,"suppression",true,0,"n-1");
		dao.addCourse(c);
		c = new Course(3,new Timestamp(new Date().getTime()),"audio","Test Junit 3","Test d'ajout de cours","test","Kieffer","Laurent",
				"127.0.0.1",5,"suppression",true,0,"n-1");
		c = new Course(4,new Timestamp(new Date().getTime()),"audio","Test Junit 4","Test d'ajout de cours null","test","Kieffer","Laurent",
				"127.0.0.1",5,null,true,0,"n-1");
		dao.addCourse(c);
		
		/* Adds the smils */
		Smil s = new Smil(1,"1.smil");
		dao.addSmil(s);
		s = new Smil(2,"2.smil");
		dao.addSmil(s);
		s = new Smil(3,"3.smil");
		dao.addSmil(s);
		
		/* Adds the slides */
		Slide sl = new Slide(1,"1.jpg",0);
		dao.addSlide(sl);
		sl = new Slide(1,"2.jpg",3);
		dao.addSlide(sl);
		sl = new Slide(1,"3.jpg",5);
		dao.addSlide(sl);
		sl = new Slide(2,"1.jpg",0);
		dao.addSlide(sl);
		sl = new Slide(2,"2.jpg",3);
		dao.addSlide(sl);
		sl = new Slide(2,"3.jpg",5);
		dao.addSlide(sl);
		sl = new Slide(3,"1.jpg",0);
		dao.addSlide(sl);
		sl = new Slide(3,"2.jpg",3);
		dao.addSlide(sl);
		sl = new Slide(3,"3.jpg",5);
		dao.addSlide(sl);
		
		
		System.out.println("List of all courses :");
		List<Course> l = dao.getAllCourses();
		for( int i=0 ; i<l.size() ; i++) {
			System.out.println(l.get(i).getTitle());
		}
		
		System.out.println("List of the audio courses in the test formation :");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "audio");
		map.put("formation", "test");
		
		l = dao.getCourses(map);
		for( int i=0 ; i<l.size() ; i++) {
			System.out.println(l.get(i).getTitle());
		}
		
		System.out.println("Course with ID 2 :");
		Course c2 = dao.getCourse(2);
		System.out.println(c2.getTitle());
		
		System.out.println("Course with ID 3 and genre suppression :");
		Course c3 = dao.getCourse(3,"suppression");
		System.out.println(c3.getTitle());
		
		/* Modifies the course #2 */
		/*map = new HashMap<String, String>();
		map.put("title", "Test Junit 2 modifié");
		map.put("formation", "modification");
		dao.modifyCourse(2, map);*/
		c2 = new Course(2,new Timestamp(new Date().getTime()),"video","Test Junit 2 modifié","Test d'ajout de cours","test","Kieffer","Laurent",
			"127.0.0.1",5,"suppression",true,0,"n-1");
		dao.modifyCourse(c2);
		System.out.println("Course with ID 2 :");
		c2 = dao.getCourse(2);
		System.out.println(c2.getTitle());
		
		/* Delete the smils */
		/*dao.deleteSmil(1);
		dao.deleteSmil(2);
		dao.deleteSmil(3);
		
		/* Delete the slides */
		/*dao.deleteSlide(1);
		dao.deleteSlide(2);
		dao.deleteSlide(3);
		
		/* Delete the courses */
		/*dao.deleteCourse(1);
		dao.deleteCourse(2);
		dao.deleteCourse(3);*/
	}

	public void testAmphi() {
		fail("Not yet implemented"); // TODO
	}

	public void testSlide() {
		fail("Not yet implemented"); // TODO
	}

	public void testSmil() {
		fail("Not yet implemented"); // TODO
	}

}
