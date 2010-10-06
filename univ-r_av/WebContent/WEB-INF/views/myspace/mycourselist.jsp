<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>


<!-- Displays the courses of the list in the table -->
<c:set var="class" value="row1" />
<c:forEach var="course" varStatus="status" items="${courses}">

<!-- Defines the image file which represents the course type -->
		<c:choose>
			<c:when test="${course.type == 'audio' && course.genre == null && !course.restrictionuds}">
				<c:set var="typeImg" value="sound" />
			</c:when>
			<c:when test="${course.type == 'audio' && !course.restrictionuds}">
				<c:set var="typeImg" value="sound_locked" />
			</c:when>
			<c:when test="${course.type == 'video' && course.genre == null && !course.restrictionuds}">
				<c:set var="typeImg" value="video" />
			</c:when>
			<c:when test="${course.type == 'video' && !course.restrictionuds}">
				<c:set var="typeImg" value="video_locked" />
			</c:when>
			<c:when test="${course.type == 'audio' && course.genre == null && course.restrictionuds}">
				<c:set var="typeImg" value="uds_sound" />
			</c:when>
			<c:when test="${course.type == 'audio' && course.restrictionuds}">
				<c:set var="typeImg" value="uds_sound_locked" />
			</c:when>
			<c:when test="${course.type == 'video' && course.genre == null && course.restrictionuds}">
				<c:set var="typeImg" value="uds_video" />
			</c:when>
			<c:when test="${course.type == 'video' && course.restrictionuds}">
				<c:set var="typeImg" value="uds_video_locked" />
			</c:when>
		</c:choose>

	
		<c:url var="courseaccess" scope="page" value="./courseaccess">
			<c:param name="id" value="${course.courseid}"/>
			<c:param name="type" value="flash"/>
		</c:url>		
		<tr class="${class}" style="cursor:pointer">
				

		
		
		<td onclick="document.location.href='${courseaccess}'">
		<script type="text/javascript">
			document.write('<img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video">');
		</script>
		<noscript>
			<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video"></a>
		</noscript>
		</td>
		<td onclick="document.location.href='${courseaccess}'"> 	    				
			<b><fmt:message key="Titre :"/> </b> <c:out value="${course.title}" /> <br>
			<b><fmt:message key="Auteur :"/> </b> <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br>			
		</td>
		<td onclick="document.location.href='${courseaccess}'">
			<b><fmt:message key="Formation :"/> </b> <c:out value="${course.formation}" /> <br>
			<b><fmt:message key="Sujet :"/> </b> <c:out value="${course.description}" /> <br>
		</td>
		<td nowrap="nowrap" onclick="document.location.href='${courseaccess}'">
			<fmt:message key="dateFormat" var="dateFormat" />
			<b><fmt:message key="Date :"/> </b> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format> <br>
			<b><fmt:message key="Dur&eacute;e :"/> </b> <c:out value="${course.durationString}" /> <br>
		</td>
		<td nowrap="nowrap" onclick="document.location.href='${courseaccess}'">
			<b><fmt:message key="Type :"/> </b> <c:out value="${course.type}" /> <br>
			<b><fmt:message key="Consultations :"/> </b> <c:out value="${course.consultations}" /> <br>
		</td>
			

		<c:url var="edit" scope="page" value="./myspace_editmycourse">
				<c:param name="id" value="${course.courseid}"/>
		</c:url>
		<td class="tdalign" onclick="document.location.href='${edit}'">
			<div class="btnEdit">
			<script type="text/javascript">
					document.write('<a class="aEdit"><fmt:message key="Editer"/> </a>');
				</script>
				<noscript>
					<a href="<c:out value="${edit}"/>"  class="aEdit"><fmt:message key="Editer"/> </a>
				</noscript>
	    	</div>
		</td>
		
		<c:url var="deleteurl" scope="page" value="./myspace_deletecourse">
    		<c:param name="id" value="${course.courseid}"/>
		</c:url>
		<td class="tdalign" >
    		<div class="btnDelete">
    			<a class="aDelete" href="javascript:confirmation('<fmt:message key="Supprimer_confirmation"/> ${course.courseid} : ${fn:replace(course.title,'\'',' ')}?','${deleteurl}')"><fmt:message key="Supprimer"/> </a>
    		</div>
		</td>
		
		</tr>
		
	<!-- Defines the class of the next row of the table -->
	<c:choose>
		<c:when test="${status.count % 2 == 0}">
			<c:set var="class" value="row1" />
		</c:when>
		<c:otherwise>
			<c:set var="class" value="row2" />
		</c:otherwise>
	</c:choose> 
</c:forEach>