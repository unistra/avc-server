<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
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
				    		<td>Type</td>
				    		<td><input type="hidden" name="type" value="${course.type}">${course.type}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Title</td>
				    		<td><input type="text" name="title" value="${course.title}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Description</td>
				    		<td><input type="text" name="description" value="${course.description}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Formation</td>
				    		<td><input type="text" name="formation" value="${course.formation}" class="field"></td>
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
				    		<td>MediaFolder</td>
				    		<td><input type="hidden" name="mediaFolder" value="${course.mediaFolder}">${course.mediaFolder}</td>
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
				    		<td>Restriction Uds</td>
				    		<td><input type="checkbox" name="restrictionuds" ${course.restrictionuds == true ? 'checked' : ''} ></td>
			    		</tr>
			    		<tr class="even">
			    			<td>MediaType</td>
				    		<td><input type="text" name="mediatype" value="${course.mediatype}" class="field"></td>
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
							<th colspan="2" id="adddoc"><fmt:message key="uploadadddoc"/></th>
						</tr>
						<tr class="odd">
							<td><fmt:message key="file"/> : </td>
							<td><input type="file" name="media" class="field"> </td>
						</tr>
						<tr>
							<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible';document.subeditform.valider.disabled=true;this.disabled=true;" value="<fmt:message key="sendFile"/>"> </td>
							<td><img id="process" src="../files/img/squaresCircle.gif" /></td>
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
							<th colspan="2" id="adddoc"><fmt:message key="deleteadddoc"/></th>
						</tr>
						<tr class="odd">
							<td><fmt:message key="file"/> : </td>
							<td><input type="text" name="media" class="fieldAdddoc" readonly="readonly" value="${course.adddocname}"></td>
						</tr>
						<tr>
							<td><input type="submit" name="valider" value="<fmt:message key="deletefile"/>"> </td>
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
		  		
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
