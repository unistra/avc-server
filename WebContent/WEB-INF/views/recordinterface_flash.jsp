<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
  <head>
    
    <meta charset="utf-8">
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Visualisation du cours"/>&nbsp;${course.title}</title>

	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization.css">
	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/colorbox/colorbox.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<c:forEach var="rssfile" items="${rssfiles}" begin="0" end="1">
		<c:if test="${rssfile.key != null}">	
			<link rel="alternate" type="application/rss+xml" title="${rssfile.key}" href="${rssfile.value}"/>
		</c:if>
	</c:forEach>
	
	<script type="text/javascript" src="../files/js/jquery.min.js"></script>

	<!-- Redirection to html5 for mobile -->
	<script type="text/javascript" src="../files/js/mobile_detection.js"></script>
	<script type="text/javascript">
		if (isMobile.any()) {
			document.location = "./courseaccess?id=${course.courseid}&type=html5";
		}
	</script>
	
	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/jwflvplayer/swfobject.js"></script>
	<!-- <script type="text/javascript" src="../files/js/ieupdate.js"></script> -->
	<script type="text/javascript" src="../files/js/recordinterface_flash.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
	<script type="text/javascript" src="../files/colorbox/jquery.colorbox-min.js"></script>
	
	<script type="text/javascript">
		var timecodes = ${slides};
		var style = "${sessionScope.style}";
		var slidesurl = "${slidesurl}";
		var timing = ${timing};
	</script>
	<c:choose>
   	<c:when test="${course.type!='video' && !fn:contains(mediaLst, 'addvideo')}">
		<script type="text/javascript">
     		var flashvars =
     	 	{
       			file:					'${courseurl}', 
        		id:						'flashvideo', 
       			autostart:				'true',
       			image:					'../files/img/logo_audio.png', 	
       			plugins:				'captions-1',
               	'captions.file':		'${courseurlfolder}/additional_docs/${course.courseid}_captions.xml',
           		'captions.back':		'true'		  
     		};
     	</script>
	</c:when>
   	<c:otherwise>
		<script type="text/javascript">
    		var flashvars =
     	 	{
       	 		file:					'${courseurl}', 
        		id:						'flashvideo', 
       			autostart:				'true',
       			image:					'../files/img/logo_audio.png',
           		type:					'lighttpd',			  
           		plugins:				'captions-1',
           		'captions.file':		'${courseurlfolder}/additional_docs/${course.courseid}_captions.xml',
           		'captions.back':		'true'
     		};
     	</script>
	</c:otherwise>
   	</c:choose>
   				 			          	    
	<script type="text/javascript">
    	var params =
    	{
    		allowfullscreen:		'true', 
       		allowscriptaccess:		'always',
       		wmode:					'transparent'
     	 };

      	var attributes =
     	 {
     		id:						'flashvideo', 
       		name:					'flashvideo'
     	}; 		
	</script>
	
	<meta name="keywords" content="${course.name},${course.title},${formationfullname}">
	<META NAME="robots" CONTENT="nofollow">

	<!-- google analytics -->
	<c:import url="include/google_analytics.jsp" />
		
  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="firstline">
		    	<div class="amphitheatre">${building} | ${amphi}</div>
		    	<div class="closeButton">
		    		<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png" alt="close"></a>
	    			<c:forEach var="rssfile" items="${rssfiles}" begin="0" end="1" varStatus="status">
	    				<c:if test="${rssfile.key != null}">
	    					<c:url value="itpc://${fn:substringAfter(serverUrl,\"://\")}/${fn:substringAfter(rssfile.value,\"../\")}" var="variableURL"></c:url>
							<a href="${variableURL}" rel="alternate" type="application/rss+xml" title="${rssfile.key}"><img src="../files/img/itunes_abo.png" alt="itunes_icon"></a>					
							<c:if test="${status.index == 0}"><a href="${rssfile.value}" rel="alternate" type="application/rss+xml" title="${rssfile.key}"><img src="../files/img/rss_abo.png" alt="rss_icon"><fmt:message key="Abonnement"/> <fmt:message key="Auteur"/></a>&nbsp;-&nbsp;</c:if>
	    					<c:if test="${status.index == 1}"><a href="${rssfile.value}" rel="alternate" type="application/rss+xml" title="${rssfile.key}"><img src="../files/img/rss_abo.png" alt="rss_icon"><fmt:message key="Abonnement"/> <fmt:message key="Formation"/></a>&nbsp;-&nbsp;</c:if>
	    				</c:if>
	    			</c:forEach>
	    		</div> 
	    	</div>
	    	
	    	<div class="visumain"> 
	    	
	    		<div class="visuleft"> 
	    								   				 		
   				 	<!-- player flash if slides not null -->
					<c:if test="${fn:length(slides) != 0}">
						<script type="text/javascript">
							swfobject.embedSWF('../files/jwflvplayer/player.swf', 'flashvideo', '320', '260', '9.0.124', false, flashvars, params, attributes);
   				 		</script>		 
   				 		<div class="flash" id="flash"><p id="flashvideo"><a href="http://www.adobe.com/go/getflashplayer">Get flash to see this player</a> <br>or<br> <a href="./courseaccess?id=${course.courseid}&amp;type=html5">Try the html5 player</a></p></div>	 	            	
	            		<br>
	            		
	            			<c:if test="${fn:contains(mediaLst, 'hq')}">
	            				<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="hq"/>
								</c:url>
								<div class="highquality2">
									<a href="<c:out value="${courseaccess}" />"><b><fmt:message key="Highquality"/></b></a>
								</div>	
							</c:if>
						
					</c:if>
	                                   
	                  
					<div class="info"> 
						<b><fmt:message key="Titre :"/> </b> <c:out value="${course.title}"/> 
						<br>
						<b><fmt:message key="Auteur :"/> </b> <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br>
						<b>Tags : </b>  <c:forEach var="tags" items="${tags}"><a href="./tags?tags=${tags.tag}"><c:out value="${tags.tag} "/></a></c:forEach> <br>
					
						<div id="infocollink">
							<a href="javascript:switchDetails('info')"><fmt:message key="[+] plus de détails"/></a>
						</div>	   				
						<div id="infocoldetails" class="hidden">
							<b><fmt:message key="Formation :"/> </b> <c:out value="${formationfullname}" /> <br>
							<b><fmt:message key="Sujet :"/> </b> <c:out value="${course.description}" /> <br>
							<fmt:message key="dateFormat" var="dateFormat" />
							<b><fmt:message key="Date :"/> </b> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format> <br>
							<b><fmt:message key="RecordDate"/> : </b> <c:if test="${course.recorddate != null && course.recorddate != ''}"><dt:format pattern="${dateFormat}">${course.recorddate.time}</dt:format></c:if> <br>
							<b><fmt:message key="Type :"/> </b> <c:out value="${course.type}" /> <br>
							<b><fmt:message key="Dur&eacute;e :"/> </b> <c:out value="${course.durationString}" /> <br>
							<b><fmt:message key="Consultations :"/> </b> <c:out value="${course.consultations}" /> <br>
							<a href="javascript:switchDetails('info')"><fmt:message key="[-] moins de détails"/></a>
						</div> 	
					</div>
					
					<br>
					
					<c:if test="${!univr}">					
					<div class="permalien">						
						<c:url var="permacourseaccess" scope="page" value="${serverUrl}/avc/courseaccess">
							<c:param name="id" value="${course.courseid}"/>
						</c:url>
						<b>URL:</b> <input id="permalieninput1" type="text" value="${permacourseaccess}" onClick="javascript:focus();select();" readonly>
						<br>
					
						<div id="permaliencollink">
							<a href="javascript:switchDetails('permalien')"><fmt:message key="[+] plus de détails"/></a>
						</div>	   				
						<div id="permaliencoldetails" class="hidden">				
							<c:url var="permaauthor" scope="page" value="${serverUrl}/avc/courses">
								<c:choose>	
									<c:when test="${course.firstname==null || course.firstname==''}">
										<c:param name="author" value="${course.name}"/>
									</c:when>
									<c:otherwise>
										<c:param name="author" value="${course.name} ${course.firstname}"/>
									</c:otherwise>
								</c:choose>		
							</c:url>	
							<b>URL <fmt:message key="Auteur"/>:</b> <input id="permalieninput2" type="text" value="${permaauthor}" onClick="javascript:focus();select();" readonly>
							<br>
							<c:url var="permaformation" scope="page" value="${serverUrl}/avc/courses">
								<c:param name="formation" value="${course.formation}"/>
							</c:url>	
							<b>URL <fmt:message key="Formation"/>:</b> <input id="permalieninput3" type="text" value="${permaformation}" onClick="javascript:focus();select();" readonly>
							<br>
							
							<!-- Embed objects -->
							<c:if test="${embedvs!=null}">
							<b><fmt:message key="Embed"/> VS:</b> <input id="permalieninput4" type="text" value="${embedvs}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
							<c:if test="${embedaudio!=null}">
							<b><fmt:message key="Embed"/> Audio:</b> <input id="permalieninput5" type="text" value="${embedaudio}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
							<c:if test="${embedvideo!=null}">
							<b><fmt:message key="Embed"/> Video:</b> <input id="permalieninput6" type="text" value="${embedvideo}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
							<c:if test="${embedhq!=null}">
							<b><fmt:message key="Embed"/> HD:</b> <input id="permalieninput7" type="text" value="${embedhq}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
														
							<a href="javascript:switchDetails('permalien')"><fmt:message key="[-] moins de détails"/></a>
						</div> 
					</div>   
					</c:if>
							
					<br>	
								
					<div class="telecharger">
					<table>
					<tr>
					<td>
						<b class="type"><fmt:message key="Telecharger"/>:&nbsp;</b>
					</td>
						
					<c:choose>	
					<c:when test="${course.download}">	
						
						<!-- FLASH ONLY -->
						 <c:if test="${course.mediatype == 1}">
							<td>	
								<b class="type"><fmt:message key="processing"/></b>
							</td>
						</c:if>
							
						<!-- VIDEOSLIDE -->
						 <c:if test="${fn:contains(mediaLst, 'videoslide')}">
							<td class="tdalign">	
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="videoslide"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.videoslide']);"><img src="../files/styles/${sessionScope.style}/img/videoslide.png" alt="vs"></a><br><b class="type">mp4</b>
							</td>
						</c:if>
						
						<!-- VIDEOSLIDE IPOD -->
						 <c:if test="${fn:contains(mediaLst, 'videoslideipod')}">
							<td class="tdalign">	
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="videoslideipod"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.videoslide']);"><img src="../files/styles/${sessionScope.style}/img/videoslide.png" alt="vs"></a><br><b class="type">mp4<br/>ipod</b>
							</td>
						</c:if>
											
						<!-- OGG -->
						<c:if test="${fn:contains(mediaLst, 'ogg')}">
							<td class="tdalign">	
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="ogg"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.ogg']);"><img src="../files/styles/${sessionScope.style}/img/ogg_v2.png" alt="ogg"></a><br><b class="type">ogg</b>	
							</td>
						</c:if>
						
						<!-- MP3 -->
						<c:if test="${fn:contains(mediaLst, 'mp3')}">
							<td class="tdalign">
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="mp3"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.mp3']);"><img src="../files/styles/${sessionScope.style}/img/mp3_v2.png" alt="mp3"></a><br><b class="type">mp3</b>
							</td>
						</c:if>
							
						<!-- ZIP -->	
						<c:if test="${fn:contains(mediaLst, 'zip')}">
							<td class="tdalign">		
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="zip"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.zip']);"><img src="../files/styles/${sessionScope.style}/img/winzip3.png" alt="zip"></a><br><b class="type">zip</b>
							</td>
						</c:if>
						
						<!-- PDF -->
						<c:if test="${fn:contains(mediaLst, 'pdf')}">
							<td class="tdalign">
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="pdf"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.pdf']);"><img src="../files/styles/${sessionScope.style}/img/acrobat.png" alt="pdf"></a><br><b class="type">pdf</b>
							</td>
						</c:if>
						
						<!-- VIDEO -->
						 <c:if test="${fn:contains(mediaLst, 'hq')}">
							<td class="tdalign">	
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="video"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.video']);"><img src="../files/styles/${sessionScope.style}/img/videodl.png" alt="video"></a><br><b class="type">video</b>
							</td>
						</c:if>
							
						</tr>	
						
					</c:when>
					<c:otherwise>
						<td>	
							<b class="type"><fmt:message key="nodownload"/></b>
						</td>
					</c:otherwise>	
					</c:choose>
											
					</table>
					</div>		
									
					<c:if test="${fn:contains(mediaLst, 'adddoc') && course.adddocname !=null}">
						<c:url var="courseaccess" scope="page" value="./courseaccess">
							<c:param name="id" value="${course.courseid}"/>
							<c:param name="type" value="adddoc"/>
						</c:url>
						<br />
						<div class="info">	
							<b>Supplément: </b><a href="<c:out value="${courseaccess}" />" onClick="javascript: _gaq.push(['_trackPageview', '/download/${course.courseid}.adddoc']);">${course.adddocname }</a>	
						</div>
					</c:if>
					
					<!-- html5 link for audio mp3 or video mp4 -->
					<c:if test="${fn:contains(mediaLst, 'html5')}">			
	            		<c:url var="courseaccess" scope="page" value="./changeinterface">
							<c:param name="courseid" value="${course.courseid}"/>
							<c:param name="recordinterface" value="html5"/>
						</c:url>
						<br />
						<div class="linkhtml5"> 
							<a href="<c:out value="${courseaccess}" />"><b><fmt:message key="linkhtml5"/></b></a>
						</div>	
					</c:if>
										
	            </div>
	            
	            <div class="visuright">
	            
	            <!-- current slide -->  
	           	<c:if test="${fn:length(slides) != 0}">
						<div id="currentDia">
							<div id="frontslide"></div>
							<div id="backslide"></div>
						</div>	
				</c:if>	
				
				<!-- player flash if no slides-->
				<c:if test="${fn:length(slides) == 0}">     
	            		<script type="text/javascript">
     			  			swfobject.embedSWF('../files/jwflvplayer/player.swf', 'flashvideo', '640', '500', '9.0.124', false, flashvars, params, attributes);
   						</script>
   						<div class="flash" id="flash"><p id="flashvideo"><a href="http://www.adobe.com/go/getflashplayer">Get flash to see this player</a> <br>or<br> <a href="./courseaccess?id=${course.courseid}&amp;type=html5">Try the html5 player</a></p></div>	 	            	
	            		<br>	
	            			
	            			<c:if test="${fn:contains(mediaLst, 'hq')}">
	            				<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="hq"/>
								</c:url>
								<div class="highquality">
									<a href="<c:out value="${courseaccess}" />"><b><fmt:message key="Highquality"/></b></a>	
								</div>
							</c:if>
				</c:if>	
					
				</div>
			</div>
			
			<br>
			
			<div id="videoLine">
			</div>
			
			
			<div class="leftPagination">
				<a href="javascript:previousPage()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="Precedent"/></a>
			</div>
			
			<div class="rightPagination">
			Page <span id="pageNumber"></span>
			<a href="javascript:nextPage()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="Suivant"/></a>
			| Diapo <span id="slideNumber"></span>
			</div>

    	</div>
	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
    
    
  </body>
</html>
