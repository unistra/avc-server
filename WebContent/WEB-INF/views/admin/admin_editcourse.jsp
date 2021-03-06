<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
  <head>

  	<meta charset="utf-8">
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/admin.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/menus_off_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	<div class="banner">
	    		<c:import url="../include/banner.jsp" />
	    	</div>
	    	
	    	<div class="links">
		    	<c:import url="./links.jsp" />
	    	</div>
	    	
	    	<div class="message">
	    		<p class="${messagetype}"><c:out value="${message}" /></p>
	    	</div>
	        	
	    	<div class="editform">
		    	<form method="POST" action="<c:url value="${posturl}"/>" class="subeditform" name="subeditform">
			    	<table>
			    		<tr class="tableheader">
							<th colspan="2" id="editcourse"><fmt:message key="editcourse"/></th>
						</tr>
			    		<tr class="odd">
				    		<td>N°</td>
				    		<td><input type="hidden" name="courseid" value="${course.courseid}">${course.courseid}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Date</td>
				    		<td><input type="hidden" name="date" value="${course.date.time}">${course.date}</td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Record Date</td>
				    		<td><input type="hidden" name="recorddate" value="${course.recorddate.time}">${course.recorddate}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Type</td>
				    		<td><input type="hidden" name="type" value="${course.type}">${course.type}</td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Title</td>
				    		<td><input type="text" name="title" value="${course.title}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Description</td>
				    		<td><input type="text" name="description" value="${course.description}" class="field"></td>
			    		</tr>
			    		<!--<tr class="even">
				    		<td>Formation</td>
				    		<td><input type="text" name="formation" value="${course.formation}" class="field"></td>
			    		</tr>-->
			    		<tr class="odd">
						<td title="<fmt:message key="ib_level"/>"><fmt:message key="level"/></td>
						<td>
							<select name="level">
								<c:forEach var="levels" items="${levels}">
									<c:if test="${levelSelected == levels.code}">
										<c:set var="selected" value="selected" />
									</c:if>
									<option value="${levels.code}" title="${levels.name}" ${selected}>${levels.name}</option>
									<c:remove var="selected"/>
								</c:forEach>
							</select>
						</td>
					</tr>
								
					<tr class="even">
						<td title="<fmt:message key="ib_form"/>"><fmt:message key="component"/></td>
						<td>
							<select name="component">
								<c:forEach var="discipline" items="${disciplines}">
									<c:if test="${discSelected == discipline.codecomp}">
										<c:set var="selected" value="selected" />
									</c:if>
									<option value="${discipline.codecomp}" title="${discipline.namecomp}" ${selected}>${discipline.namecomp}</option>
									<c:remove var="selected"/>
								</c:forEach>
							</select>
						</td>
					</tr>
			    		<tr class="odd">
				    		<td>Name</td>
				    		<td><input type="text" name="name" value="${course.name}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Firstname</td>
				    		<td><input type="text" name="firstname" value="${course.firstname}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Ipaddress</td>
				    		<td><input type="text" name="ipaddress" value="${course.ipaddress}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Duration</td>
				    		<td><input type="hidden" name="duration" value="${course.duration}">${course.durationString}</td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Password</td>
				    		<td><input type="text" name="genre" value="${course.genre}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Visible</td>
				    		<td><input type="checkbox" name="visible" ${course.visible == true ? 'checked' : ''} ></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Consultations</td>
				    		<td><input type="hidden" name="consultations" value="${course.consultations}">${course.consultations}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Timing</td>
				    		<td><input type="hidden" name="timing" value="${course.timing}">${course.timing}</td>
			    		</tr>
			    		<tr class="odd">
				    		<td>UserId</td>
				    		<td><input type="text" name="userid" value="${course.userid}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Tags</td>
				    		<td><input type="text" name="tags" value="${tags}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>AddDocName</td>
				    		<td><input type="hidden" name="adddocname" value="${course.adddocname}">${course.adddocname}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Download</td>
				    		<td><input type="checkbox" name="download" ${course.download == true ? 'checked' : ''} ></td>
			    		</tr>
			    		<tr class="odd">
				    		<td><fmt:message key="restrictionuds"/> ${univAcronym}</td>
				    		<td><input type="checkbox" name="restrictionuds" ${course.restrictionuds == true ? 'checked' : ''} ><span class="littleFont"><fmt:message key="uploadmessage6"/> ${univAcronym}</span></td>
			    		</tr>
			    		<tr class="even">
			    			<td>MediaType</td>
				    		<td><input type="text" name="mediatype" value="${course.mediatype}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Slides Offset</td>
				    		<td><input type="text" name="slidesoffset" value="${course.slidesoffset}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Volume</td>
				    		<td><input type="hidden" name="volume" value="${course.volume}">${course.volume}</td>
			    		</tr>
			    	</table>
			    	<br>
			    	<input type="submit" class="valider" name="valider" value="<fmt:message key="Valider"/>">			    	
		    	</form>
		    	
		    </div>
		    	
		    <br>
		    <p><fmt:message key="editmessage3"/></p> 
			<br>
			<br>
		    	
		    <div class="editform">
		    		    			    	
		    <c:choose>
		    	<c:when test="${!fn:contains(mediaLst, 'adddoc') && course.adddocname == null}">
		    		<form action="<c:url value="./admin_adddocupload"/>" method="post" enctype="multipart/form-data">
						<input type="hidden" name="courseid" value="${course.courseid}">
						<input type="hidden" name="returnUrl" value="/avc/admin_editcourse?id=${course.courseid}">
						<table>
						<tr class="tableheader">
							<th colspan="2" class="adddoc" id="adddoc"><fmt:message key="uploadadddoc"/></th>
						</tr>
						<tr class="odd">
							<td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/> : </td>
							<td><input type="file" name="media" class="field"> </td>
						</tr>
						<tr>
							<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible';document.subeditform.valider.disabled=true;" value="<fmt:message key="sendFile"/>"> </td>
							<td><img id="process" src="../files/img/squaresCircle.gif" alt="process..." /></td>
						</tr>
						</table>
					</form>
		   		 </c:when>
		    	<c:otherwise>
		    	<form method="POST" action="<c:url value="./admin_deleteadddoc" />">	    	
		    		<input type="hidden" name="courseid" value="${course.courseid}">
		    		<input type="hidden" name="returnUrl" value="/avc/admin_editcourse?id=${course.courseid}">
		    		<table>
						<tr class="tableheader">
							<th colspan="2" class="adddoc" id="adddoc"><fmt:message key="deleteadddoc"/></th>
						</tr>
						<tr class="odd">
							<td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/> : </td>
							<td><input type="text" name="media" class="fieldAdddoc" readonly="readonly" value="${course.adddocname}"></td>
						</tr>
						<tr>
							<td><input type="submit" name="valider" value="<fmt:message key="deletefile"/>"> </td>
						</tr>
					</table>
				</form>
		    	</c:otherwise>
		    </c:choose>
		    		    		    
		    <br />
		    
		    <!-- show this block if the record is from the client -->
			<c:if test="${showAddVidBlock}">
			
			<c:url var="thick_replacemedia" scope="page" value="./thick_replacemedia">
				<c:param name="width" value="370"/>
				<c:param name="height" value="150"/>
			</c:url>
			<c:choose>
				<c:when test="${addvinprocess}">
		    		<table>
						<tr class="tableheader">
								<th colspan="1" class="replacemedia" id="replacemedia"><fmt:message key="replacemedia"/>&nbsp;<a href="<c:out value="${thick_replacemedia}" />" title="<fmt:message key="replacemedia"/>" id="replacemediahelp" class="thickbox"><fmt:message key="replacemediahelp"/></a></th>
							</tr>
						<tr class="odd">
							<td><fmt:message key="processing"/></td>
						</tr>
					</table>
			    </c:when>
		    	<c:when test="${!fn:contains(mediaLst, 'addvideo')}">
					<form action="<c:url value="./admin_replacemedia?courseid=${course.courseid}"/>" method="post" enctype="multipart/form-data">
						<input type="hidden" name="courseid" value="${course.courseid}">
						<input type="hidden" name="returnUrl" value="/avc/admin_editcourse?id=${course.courseid}">
						<table>
							<tr class="tableheader">
								<th colspan="2" class="replacemedia" id="replacemedia"><fmt:message key="replacemedia"/>&nbsp;<a href="<c:out value="${thick_replacemedia}" />" title="<fmt:message key="replacemedia"/>" id="replacemediahelp" class="thickbox"><fmt:message key="replacemediahelp"/></a></th>
							</tr>
							<tr class="odd">
								<td title="<fmt:message key="slidesoffset"/>"><fmt:message key="slidesoffset"/> : </td>
								<td><input type="text" name="slidesoffset" value="${course.slidesoffset }" class="field"></td>
							</tr>
							<tr class="even">
								<td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/> : </td>
								<td><input type="file" name="media" class="field"> </td>
							</tr>
							<tr>
								<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process2').style.visibility='visible';document.subeditform.valider.disabled=true;" value="<fmt:message key="sendFile"/>"> </td>
								<td><img id="process2" src="../files/img/squaresCircle.gif" alt="process..." /></td>
							</tr>
						</table>
					</form>
				</c:when>
			<c:otherwise>
		   		<form method="POST" action="<c:url value="./admin_deletereplacemedia" />">	    	
		    		<input type="hidden" name="courseid" value="${course.courseid}">
		    		<input type="hidden" name="returnUrl" value="/avc/admin_editcourse?id=${course.courseid}">
		    		<table>
						<tr class="tableheader">
							<th colspan="2" class="replacemedia" id="replacemedia"><fmt:message key="deletereplacemedia"/></th>
						</tr>
						<tr class="odd">
							<td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/> : </td>
							<td><input type="text" name="media" class="fieldReplaceMedia" readonly="readonly" value="<fmt:message key="mediapresent"/>"></td>
						</tr>
						<tr>
							<td><input type="submit" name="valider" value="<fmt:message key="deletefile"/>"> </td>
						</tr>
					</table>
				</form>
		    	</c:otherwise>
		    </c:choose>		    		    
		   
		   <br />
		   </c:if>
		   
		    <c:choose>
		    	<c:when test="${!fn:contains(mediaLst, 'subtitles')}">
		    		<form action="<c:url value="./admin_addsubtitles?courseid=${course.courseid}"/>" method="post" enctype="multipart/form-data">
						<input type="hidden" name="courseid" value="${course.courseid}">
						<input type="hidden" name="returnUrl" value="/avc/admin_editcourse?id=${course.courseid}">
						<table>
						<tr class="tableheader">
							<th colspan="2" class="thsubtitles" id="thsubtitles"><fmt:message key="uploadsubtitles"/>&nbsp;<a class="captex" href="../files/jwflvplayer/caption_example.txt"><fmt:message key="examplesubtitles"/></a> </th>
						</tr>
						<tr class="odd">
							<td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/> : </td>
							<td><input type="file" name="media_subtitles" class="field"> </td>
						</tr>
						<tr>
							<td><input type="submit" name="subvalider" onclick="javascript:document.getElementById('subprocess').style.visibility='visible';document.subeditform.subvalider.disabled=true;" value="<fmt:message key="sendFile"/>"> </td>
							<td><img id="subprocess" src="../files/img/squaresCircle.gif" alt="process" /></td>
						</tr>
						</table>
					</form>
		   		 </c:when>
		    	<c:otherwise>
		    	<form method="POST" action="<c:url value="./admin_deletesubtitles" />">	    	
		    		<input type="hidden" name="courseid" value="${course.courseid}">
		    		<input type="hidden" name="returnUrl" value="/avc/admin_editcourse?id=${course.courseid}">
		    		<table>
						<tr class="tableheader">
							<th colspan="2" class="thsubtitles" id="thsubtitles"><fmt:message key="deletesubtitles"/></th>
						</tr>
						<tr class="odd">
							<td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/> : </td>
							<td><input type="text" name="media_subtitles" class="fieldSubtitles" readonly="readonly" value="<fmt:message key="subtitlepresent"/>"></td>
						</tr>
						<tr>
							<td><input type="submit" name="subvalider" value="<fmt:message key="deletefile"/>"> </td>
						</tr>
					</table>
				</form>
		    	</c:otherwise>
		    </c:choose>
		   
		   <br>
		
			<a href="<c:url value="${gobackurl}" />"><fmt:message key="Retour"/></a>
			
	    	</div>
	    	
	    	<br>
	    	<p><fmt:message key="editmessage1"/></p> 
	        <p><fmt:message key="editmessage2"/></p>
	        <br>
	        <p><fmt:message key="editmessage4"/></p>  
		  		
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
