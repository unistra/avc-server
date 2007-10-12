package org.ulpmm.univrav.tests;

import java.sql.Timestamp;
import java.util.Date;

import org.ulpmm.univrav.dao.FileSystemImpl;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;

import junit.framework.TestCase;

public class TestDaoFS extends TestCase {

	private static IFileSystem fs;
	
	public void testUpload() {
		
		System.out.println(System.getProperties().getProperty("user.dir"));
		
		/*Course c = new Course(5,new Timestamp(new Date().getTime()),"","Test Junit FS","Test d'ajout de cours sur le syst. de fichiers","essai","Kieffer","Laurent",
				"127.0.0.1",0,"suppression",true,0,"n-1");
		Course c2 = new Course(6,new Timestamp(new Date().getTime()),"","Test Junit FS","Test d'ajout de cours sur le syst. de fichiers","essai","Kieffer","Laurent",
				"127.0.0.1",0,"suppression",true,0,"n");*/
		
		fs = new FileSystemImpl("scripts");
		//fs.addCourse(c,"2007-03-27-16h-33m-30s.tar");
		
		fs = new FileSystemImpl("scripts");
		//fs.addCourse(c2,"2007-01-29-17h-36m-35s.zip");
	}
}
