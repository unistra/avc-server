<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	
	<meta name="keywords" content="${course.name},${course.title},${course.formation}">
	<META NAME=”robots” CONTENT=”nofollow”>

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="firstline">
		    	<div class="amphitheatre">${building} | ${amphi}</div>
		    	<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png"></a>
	    	</div>
	    	
	    	<table class="flashslide">
	    	<tr>
				<td id="cellule_flash">
					<p id="flash">Please install flash player</p>
	            	<script type="text/javascript">
		            var so = new SWFObject('../files/jwflvplayer/mediaplayer.swf','flashvideo','320','260','8');
		            so.addParam('allowscriptaccess','always'); 
		            so.addParam('allowfullscreen','true');
		            so.addVariable('width','320');
		            so.addVariable('height','260');
		            so.addVariable('file','${courseurl}');
		            so.addVariable("image","../files/img/logo_audio.png");
		            so.addVariable('autostart','true');
		            so.addVariable('javascriptid','flashvideo');
		            so.addVariable('enablejs','true');
		            so.write('flash');
	            	</script>
	            	
	            	<div class="highquality">
	            		<c:if test="${course.highquality}">
	            		<c:url var="courseaccess" scope="page" value="./courseaccess">
							<c:param name="id" value="${course.courseid}"/>
							<c:param name="type" value="hq"/>
						</c:url>
						<a href="<c:out value="${courseaccess}" />"><fmt:message key="Highquality"/></a>	
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
							<b><fmt:message key="Formation :"/> </b> <c:out value="${course.formation}" /> <br>
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
							<b>URL <fmt:message key="Auteur"/>:</b> <input id="permalieninput" type="text" value="${serverUrl}/avc/courses?author=${course.name} ${course.firstname}" onClick="javascript:focus();select();" readonly>
							<br>
							<b>URL <fmt:message key="Formation"/>:</b> <input id="permalieninput" type="text" value="${serverUrl}/avc/courses?formation=${course.formation}" onClick="javascript:focus();select();" readonly>
							<br>
							<a href="javascript:switchDetails('permalien')"><fmt:message key="[-] moins de détails"/></a>
						</div> 
					</div>   
							
					<br>
													
					<div class="telecharger">
					<table>
					
					<tr>
					
						<td class="tdalign">
							<b id=type><fmt:message key="Telecharger"/>:&nbsp;</b>
						</td>
					
						<!-- SMIL -->
						<td class="tdalign">
							<c:url var="courseaccess" scope="page" value="./courseaccess">
								<c:param name="id" value="${course.courseid}"/>
								<c:param name="type" value="smil"/>
							</c:url>
							<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/smile.png" alt="smil"></a>
							<b id=type>smil</b>
						</td>
						
						<!-- OGG -->
						<td class="tdalign">	
							<c:url var="courseaccess" scope="page" value="./courseaccess">
								<c:param name="id" value="${course.courseid}"/>
								<c:param name="type" value="ogg"/>
							</c:url>
							<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/ogg_v2.png" alt="ogg"></a>	
							<b id=type>ogg</b>
						</td>
						
						<td class="tdalign">
							<c:url var="courseaccess" scope="page" value="./courseaccess">
								<c:param name="id" value="${course.courseid}"/>
								<c:param name="type" value="mp3"/>
							</c:url>
							<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/mp3_v2.png" alt="mp3"></a>
							<b id=type>mp3</b>
						</td>
							
						<td class="tdalign">		
							<c:url var="courseaccess" scope="page" value="./courseaccess">
								<c:param name="id" value="${course.courseid}"/>
								<c:param name="type" value="zip"/>
							</c:url>
							<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/winzip3.png" alt="zip"></a>
							<b id=type>zip</b>
						</td>
						
						<td class="tdalign">
							<c:url var="courseaccess" scope="page" value="./courseaccess">
								<c:param name="id" value="${course.courseid}"/>
								<c:param name="type" value="pdf"/>
							</c:url>
							<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/acrobat.png" alt="pdf"></a>
							<b id=type>pdf</b>
						</td>
					</tr>						
					</table>
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
