package org.ulpmm.univrav.test;

import static org.junit.Assert.*;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.ulpmm.univrav.service.ServiceImpl;

public class ServiceImplTest {
	
	protected ServiceImpl service;

	@Before
	public void setUp() throws Exception {
		service = new ServiceImpl();
	}

	@Test
	public void testCleanString() {
		String sclean = service.cleanString("su&c>c<e\"s%s#!+  ");
		assertEquals("success!", sclean);
	}

	@Test
	public void testCleanFileName() {
		String sclean = service.cleanFileName("Sùuçccèess!2013");
		assertEquals("S_u_cc_ess_2013", sclean);
	}
	
	@Test
	public void testEncrypt() {
		String e = service.encrypt("success");
		String hash_success = "53a5687cb26dc41f2ab4033e97e13adefd3740d6";
		assertEquals(hash_success, e);
	}
	
	@Test
	public void testGeneratePassword() {
		String p = service.generatePassword(10);
		assertEquals(p.length(),10);
		String p2 = service.generatePassword(10);
		assertNotEquals(p, p2);
	}
	
	@Test
	public void testGetLevelCodeByFormation() {
		String level = service.getLevelCodeByFormation("CH-51");
		assertEquals("51", level);
	}
	
	
	@Test
	public void testGetComponentCodeByFormation() {
		String component = service.getComponentCodeByFormation("CH-51");
		assertEquals("CH", component);
	}
	
	@Test
	public void testGetFormationByCodes() {
		String formation = service.getFormationByCodes("51", "CH");
		assertEquals("CH-51", formation);
	}
	
	@Test
	public void testEncodeString() {
		try {
			String utf8 = new String("été".getBytes(),"UTF-8");
			assertEquals("été", service.encodeString(utf8));
			String iso = new String("été".getBytes(),"8859_1");
			assertEquals("été", service.encodeString(iso));
		} catch (UnsupportedEncodingException e) {
			fail("error unsupported encoding exception");
		}
	}
	
	@Test
	public void testGetTagCloud() {
		List<String> listTag = Arrays.asList(new String[] {"car", "bathroom", "human"});
		List<String> tagcloud = service.getTagCloud(listTag);
		assertEquals("<a href=\"./tags?tags=bathroom\" id=\"tag2\">bathroom</a>&nbsp;&nbsp;", tagcloud.get(0));		
		assertEquals("<a href=\"./tags?tags=car\" id=\"tag1\">car</a>&nbsp;&nbsp;",tagcloud.get(1));		
		assertEquals("<a href=\"./tags?tags=human\" id=\"tag3\">human</a>&nbsp;&nbsp;",tagcloud.get(2));
	}

	@Test
	public void testGenerateXmlStats() {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("nbcourses", 10);
		hm.put("nbduration", 2500);
		hm.put("nbviews", 25);
		hm.put("nbauthors", 3);
		String xml = service.generateXmlStats(hm,"http://localhost/univ-r_av",false);		
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
						  "<ask stat=\"ok\">\n" +
						  "<stat>\n" +
						  "<nbauthors>3</nbauthors>\n" +
						  "<nbcourses>10</nbcourses>\n" +
						  "<nbduration>41min40sec</nbduration>\n" +
						  "<nbviews>25</nbviews>\n" +
						  "</stat>\n" +
						  "</ask>\n";
		assertEquals(expected, xml);
	}
	
}
