package org.ulpmm.univrav.test;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ulpmm.univrav.entities.Course;

public class CourseTest {
	protected Course course;

	@Before
	public void setUp() throws Exception {
		course = new Course(
				1, 
				new Timestamp(0), 
				"audio", 
				"My first course", 
				"This is a test", 
				"00-O0", 
				"Dupond", 
				"Jean", 
				"127.0.0.1", 
				3600, 
				"access code", 
				true, 
				10, 
				"n-1", 
				1, 
				null, 
				true, 
				false, 
				0, 
				(short) 1, 
				new Timestamp(0), 
				null
		);
	}

	@Test
	public void testGetDateString() {		
		assertEquals("1970/01/01", course.getDateString());
	}

	@Test
	public void testGetDurationString() {		
		assertEquals("1h0min0sec", course.getDurationString());
	}

	@Test
	public void testGetDurationStringItunes() {
		assertEquals("1:0:0", course.getDurationStringItunes());
	}

	@Test
	public void testGetMediasFileName() {
		assertEquals("1", course.getMediasFileName());
	}

	@Test
	public void testIsAvailable() {
		// null or empty
		assertEquals(false, course.isAvailable(null));
		assertEquals(false, course.isAvailable(""));
		// flash
		assertEquals(false, course.isAvailable("flash"));
		course.setmediatype(Course.typeFlash);
		assertEquals(true, course.isAvailable("flash"));
		// mp3
		assertEquals(false, course.isAvailable("mp3"));
		course.setmediatype(Course.typeFlash+Course.typeMp3);
		assertEquals(true, course.isAvailable("mp3"));
		// ogg
		assertEquals(false, course.isAvailable("ogg"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg);
		assertEquals(true, course.isAvailable("ogg"));
		// pdf
		assertEquals(false, course.isAvailable("pdf"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf);
		assertEquals(true, course.isAvailable("pdf"));
		// zip
		assertEquals(false, course.isAvailable("zip"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip);
		assertEquals(true, course.isAvailable("zip"));
		// videoslide
		assertEquals(false, course.isAvailable("videoslide"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide);
		assertEquals(true, course.isAvailable("videoslide"));
		// adddoc
		assertEquals(false, course.isAvailable("adddoc"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide+Course.typeAdddoc);
		assertEquals(true, course.isAvailable("adddoc"));
		// hq, video
		assertEquals(false, course.isAvailable("hq"));
		assertEquals(false, course.isAvailable("video"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide+Course.typeAdddoc+Course.typeHq);
		assertEquals(true, course.isAvailable("hq"));
		assertEquals(true, course.isAvailable("video"));
		// videoslideipod
		assertEquals(false, course.isAvailable("videoslideipod"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide+Course.typeAdddoc+Course.typeHq+Course.typeVideoslideIpod);
		assertEquals(true, course.isAvailable("videoslideipod"));
		// subtitles
		assertEquals(false, course.isAvailable("subtitles"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide+Course.typeAdddoc+Course.typeHq+Course.typeVideoslideIpod+Course.typeSubtitles);
		assertEquals(true, course.isAvailable("subtitles"));
		// addvideo
		assertEquals(false, course.isAvailable("addvideo"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide+Course.typeAdddoc+Course.typeHq+Course.typeVideoslideIpod+Course.typeSubtitles+
				Course.typeAddVideo);
		assertEquals(true, course.isAvailable("addvideo"));
		// webm (mp4)
		assertEquals(false, course.isAvailable("webm"));
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide+Course.typeAdddoc+Course.typeHq+Course.typeVideoslideIpod+Course.typeSubtitles+
				Course.typeAddVideo+Course.typeWebm);
		assertEquals(true, course.isAvailable("webm"));
		// html5
		course.setmediatype(Course.typeFlash);
		assertEquals(false, course.isAvailable("html5"));
		course.setmediatype(Course.typeMp3+Course.typeOgg);
		assertEquals(true, course.isAvailable("html5"));
		course.setType("video");
		course.setmediatype(Course.typeWebm);
		assertEquals(true, course.isAvailable("html5"));
		// error
		assertEquals(false, course.isAvailable("blabla"));
	}

	@Test
	public void testGetMedias() {
		// test with no medias
		List<String> lst = course.getMedias();
		assertEquals(0, lst.size());
		// test with all medias
		course.setmediatype(Course.typeFlash+Course.typeMp3+Course.typeOgg+Course.typePdf+Course.typeZip+
				Course.typeVideoslide+Course.typeAdddoc+Course.typeHq+Course.typeVideoslideIpod+Course.typeSubtitles+
				Course.typeAddVideo+Course.typeWebm);
		lst = course.getMedias();
		assertEquals(true, lst.contains("flash"));
		assertEquals(true, lst.contains("mp3"));
		assertEquals(true, lst.contains("ogg"));
		assertEquals(true, lst.contains("pdf"));
		assertEquals(true, lst.contains("zip"));
		assertEquals(true, lst.contains("videoslide"));
		assertEquals(true, lst.contains("adddoc"));
		assertEquals(true, lst.contains("hq"));
		assertEquals(true, lst.contains("videoslideipod"));
		assertEquals(true, lst.contains("subtitles"));
		assertEquals(true, lst.contains("addvideo"));
		assertEquals(true, lst.contains("webm"));
		assertEquals(true, lst.contains("html5"));
	}

	@Test
	public void testGetRecordDateString() {
		assertEquals("1970/01/01", course.getRecordDateString());
	}

	@Test
	public void testIsAudioClient() {
		assertEquals(false, course.isAudioClient());
		course.setmediatype(Course.typeZip);
		assertEquals(true, course.isAudioClient());
	}

	@Test
	public void testIsVideoClient() {
		assertEquals(false, course.isVideoClient());
		course.setType("video");
		course.setmediatype(Course.typeZip);
		assertEquals(true, course.isVideoClient());
	}

	@Test
	public void testIsAudioUpload() {
		assertEquals(true, course.isAudioUpload());
		course.setmediatype(Course.typeZip);
		assertEquals(false, course.isAudioUpload());
	}

	@Test
	public void testIsVideoUpload() {
		course.setType("video");
		assertEquals(true, course.isVideoUpload());
		course.setmediatype(Course.typeZip);
		assertEquals(false, course.isVideoUpload());
	}
	
	@Test
	public void testGetMediafolder() {
		assertEquals("1/00/00/00/01", course.getMediafolder());
	}
}
