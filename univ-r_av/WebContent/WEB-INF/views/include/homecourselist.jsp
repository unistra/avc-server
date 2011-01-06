<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!-- Displays the courses of the list in the table -->
<c:set var="class" value="row1" />

<!-- Set the list -->
<c:if test="${param.courses == 'lastcourses'}">
	<c:set var="listCourses" value="${lastcourses}" />
</c:if>
<c:if test="${param.courses == 'selectioncourses'}">
	<c:set var="listCourses" value="${selectioncourses}" />
</c:if>
<c:if test="${param.courses == 'collectioncourses'}">
	<c:set var="listCourses" value="${collectioncourses}" />
</c:if>


<c:forEach var="course" varStatus="status" items="${listCourses}">
	
	<!-- Defines the image file which represents the course type -->
	<c:choose>
		<c:when test="${course.type == 'audio' && course.genre == null}">
			<c:set var="typeImg" value="sound" />
		</c:when>
		<c:when test="${course.type == 'audio'}">
			<c:set var="typeImg" value="sound_locked" />
		</c:when>
		<c:when test="${course.type == 'video' && course.genre == null}">
			<c:set var="typeImg" value="video" />
		</c:when>
		<c:when test="${course.type == 'video'}">
			<c:set var="typeImg" value="video_locked" />
		</c:when>
	</c:choose>
		
	<!-- URL of the public course -->
	<c:url var="courseaccess" scope="page" value="./courseaccess">
		<c:param name="id" value="${course.courseid}"/>
		<c:param name="type" value="flash"/>
	</c:url>
		
	<tr class="${class}">			
		<td>
			<a href="<c:out value="${courseaccess}" />" class="acourse"><img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video"></a>
		</td>
		<td> 	    	
			<a href="<c:out value="${courseaccess}" />" class="acourse">			
			<b><c:out value="${course.title}"/></b> <br>
			par <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br>
			</a>
		</td>
			
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