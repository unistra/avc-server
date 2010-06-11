<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Visualisation du cours"/>&nbsp;${course.title}</title>

	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization.css">
	
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
	
	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/jwflvplayer/swfobject.js"></script>
	<!-- <script type="text/javascript" src="../files/js/ieupdate.js"></script> -->
	<script type="text/javascript" src="../files/js/recordinterface_flash.js"></script>
	<script type="text/javascript">
		var timecodes = ${slides};
		var style = "${sessionScope.style}";
		var slidesurl = "${slidesurl}";
		var timing = ${timing};
	</script>
	
	<meta name="keywords" content="${course.name},${course.title},${formationfullname}">
	<META NAME=”robots” CONTENT=”nofollow”>

	<!-- google analytics -->
	<c:import url="include/google_analytics.jsp" />
		
  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="firstline">
		    	<div class="amphitheatre">${building} | ${amphi}</div>
		    	<div class="closeButton">
		    		<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png"></a>
	    			<c:forEach var="rssfile" items="${rssfiles}" begin="0" end="1" varStatus="status">
	    				<c:if test="${rssfile.key != null}">
	    					<c:url value="itpc://${fn:substringAfter(serverUrl,\"://\")}/${fn:substringAfter(rssfile.value,\"../\")}" var="variableURL"></c:url>
							<a href="${variableURL}" rel="alternate" type="application/rss+xml" title="${rssfile.key}" /><img src="../files/img/itunes_abo.png" alt="itunes_icon"></a>					
							<c:if test="${status.index == 0}"><a href="${rssfile.value}" rel="alternate" type="application/rss+xml" title="${rssfile.key}" /><img src="../files/img/rss_abo.png" alt="rss_icon"><fmt:message key="Abonnement"/> <fmt:message key="Auteur"/></a>&nbsp;-&nbsp;</c:if>
	    					<c:if test="${status.index == 1}"><a href="${rssfile.value}" rel="alternate" type="application/rss+xml" title="${rssfile.key}" /><img src="../files/img/rss_abo.png" alt="rss_icon"><fmt:message key="Abonnement"/> <fmt:message key="Formation"/></a>&nbsp;-&nbsp;</c:if>
	    				</c:if>
	    			</c:forEach>
	    		</div> 
	    	</div>
	    	
	    	<table class="flashslide">
	    	<tr>
				<td id="cellule_flash">
				
					<c:if test="${course.type!='video'}">
						<script type="text/javascript">
     				 		var flashvars =
     						 {
       			     			file:					'${courseurl}', 
        			 			id:						'flashvideo', 
       				 			autostart:				'true',
       				 			image:					'../files/img/logo_audio.png' 			  
     				 		};
     					</script>
					</c:if>
					<c:if test="${course.type=='video'}">
						<script type="text/javascript">
     				 		var flashvars =
     						 {
       			     			file:					'${courseurl}', 
        			 			id:						'flashvideo', 
       				 			autostart:				'true',
       				 			image:					'../files/img/logo_audio.png',
           				 		type:					'lighttpd'			  
     				 		};
     					</script>
					</c:if>
					          	    
	            	<script type="text/javascript">
     				 
     				 var params =
     				 {
       			     	allowfullscreen:		'true', 
       					allowscriptaccess:		'always'
     				 };

      				var attributes =
     				 {
     				 	id:						'flashvideo', 
       					name:					'flashvideo'
     				 };

    				  swfobject.embedSWF('../files/jwflvplayer/player.swf', 'flashvideo', '320', '260', '9.0.124', false, flashvars, params, attributes);
    				  
   				 	</script>
   				 
   				 	<p id="flash"><a id="flashvideo" href="http://www.adobe.com/go/getflashplayer">Get flash to see this player </a></p>
   				 	            	
	            	<div class="highquality">
	            		<c:if test="${fn:contains(mediaLst, 'hq')}">
	            			<c:url var="courseaccess" scope="page" value="./courseaccess">
								<c:param name="id" value="${course.courseid}"/>
								<c:param name="type" value="hq"/>
							</c:url>
							<a href="<c:out value="${courseaccess}" />"><b><fmt:message key="Highquality"/></b></a>	
						</c:if>
					</div>
	                                   
	                  
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
							<c:param name="type" value="flash"/>
						</c:url>
						<b>URL:</b> <input id="permalieninput" type="text" value="${permacourseaccess}" onClick="javascript:focus();select();" readonly>
						<br>
					
						<div id="permaliencollink">
							<a href="javascript:switchDetails('permalien')"><fmt:message key="[+] plus de détails"/></a>
						</div>	   				
						<div id="permaliencoldetails" class="hidden">				
							<c:url var="permaauthor" scope="page" value="${serverUrl}/avc/courses">
								<c:param name="author" value="${course.name} ${course.firstname}"/>
							</c:url>	
							<b>URL <fmt:message key="Auteur"/>:</b> <input id="permalieninput" type="text" value="${permaauthor}" onClick="javascript:focus();select();" readonly>
							<br>
							<c:url var="permaformation" scope="page" value="${serverUrl}/avc/courses">
								<c:param name="formation" value="${course.formation}"/>
							</c:url>	
							<b>URL <fmt:message key="Formation"/>:</b> <input id="permalieninput" type="text" value="${permaformation}" onClick="javascript:focus();select();" readonly>
							<br>
							
							<!-- Embed objects -->
							<c:if test="${embedvs!=null}">
							<b><fmt:message key="Embed"/> VS:</b> <input id="permalieninput" type="text" value="${embedvs}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
							<c:if test="${embedaudio!=null}">
							<b><fmt:message key="Embed"/> Audio:</b> <input id="permalieninput" type="text" value="${embedaudio}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
							<c:if test="${embedvideo!=null}">
							<b><fmt:message key="Embed"/> Video:</b> <input id="permalieninput" type="text" value="${embedvideo}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
							<c:if test="${embedhq!=null}">
							<b><fmt:message key="Embed"/> HD:</b> <input id="permalieninput" type="text" value="${embedhq}" onClick="javascript:focus();select();" readonly>
							<br>
							</c:if>
														
							<a href="javascript:switchDetails('permalien')"><fmt:message key="[-] moins de détails"/></a>
						</div> 
					</div>   
					</c:if>
							
					<br>
						
					<c:if test="${course.download}">						
						<div class="telecharger">
						<table>
						<tr>
						<td class="tdalign">
							<b id="type"><fmt:message key="Telecharger"/>:&nbsp;</b>
						</td>
						
						<!-- FLASH ONLY -->
						 <c:if test="${course.mediatype == 1}">
							<td class="tdalign">	
								<b id="type"><fmt:message key="processing"/></b>
							</td>
						</c:if>
							
						<!-- VIDEOSLIDE -->
						 <c:if test="${fn:contains(mediaLst, 'videoslide')}">
							<td class="tdalign">	
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="videoslide"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/videoslide.png" alt="vs"></a><br><b id="type">mp4</b>
							</td>
						</c:if>
											
						<!-- OGG -->
						<c:if test="${fn:contains(mediaLst, 'ogg')}">
							<td class="tdalign">	
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="ogg"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/ogg_v2.png" alt="ogg"></a><br><b id="type">ogg</b>	
							</td>
						</c:if>
						
						<!-- MP3 -->
						<c:if test="${fn:contains(mediaLst, 'mp3')}">
							<td class="tdalign">
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="mp3"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/mp3_v2.png" alt="mp3"></a><br><b id="type">mp3</b>
							</td>
						</c:if>
							
						<!-- ZIP -->	
						<c:if test="${fn:contains(mediaLst, 'zip')}">
							<td class="tdalign">		
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="zip"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/winzip3.png" alt="zip"></a><br><b id="type">zip</b>
							</td>
						</c:if>
						
						<!-- PDF -->
						<c:if test="${fn:contains(mediaLst, 'pdf')}">
							<td class="tdalign">
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="pdf"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/acrobat.png" alt="pdf"></a><br><b id="type">pdf</b>
							</td>
						</c:if>
						
						<!-- VIDEO -->
						 <c:if test="${fn:contains(mediaLst, 'hq')}">
							<td class="tdalign">	
								<c:url var="courseaccess" scope="page" value="./courseaccess">
									<c:param name="id" value="${course.courseid}"/>
									<c:param name="type" value="video"/>
								</c:url>
								<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/videodl.png" alt="video"></a><br><b id="type">video</b>
							</td>
						</c:if>
							
						</tr>						
						</table>
						</div>
					</c:if>	
					
					<br>
					
					<div class="info">
					
					<c:if test="${fn:contains(mediaLst, 'adddoc') && course.adddocname !=null}">
						<c:url var="courseaccess" scope="page" value="./courseaccess">
								<c:param name="id" value="${course.courseid}"/>
								<c:param name="type" value="adddoc"/>
							</c:url>
						<b>Supplément: </b><a href="<c:out value="${courseaccess}" />">${course.adddocname }</a>	
					</c:if>
					
					</div>
					
	            </td>
	  
	            <td id="cellule_dia">	
	           		<div id="currentDia">          	           		
	            		<img class="slide" id="slide" src="../files/img/DiaVide.png" width="620" height="472">
					</div>
				</td>
			</tr>
			</table>
			
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
