<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>


<!-- Displays the courses of the list in the table -->
<c:set var="class" value="row1" />
<c:forEach var="course" varStatus="status" items="${courses}">

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

	<c:choose>
			<c:when test="${course.genre == null}">
				<c:url var="courseaccess" scope="page" value="./courseaccess">
					<c:param name="id" value="${course.courseid}"/>
					<c:param name="type" value="flash"/>
				</c:url>		
				<tr class="${class}" onclick="document.location.href='${courseaccess}'" style="cursor:pointer">
				</c:when>
			<c:otherwise>
				<c:url var="courseaccess" scope="page" value="./thick_codeform">
					<c:param name="id" value="${course.courseid}"/>
					<c:param name="type" value="flash"/>
					<c:param name="width" value="250"/>
					<c:param name="height" value="120"/>
				</c:url>
				<tr class="${class}" onclick="dotb('', '${courseaccess}?width=250&height=120');return false;" style="cursor:pointer">
		</c:otherwise>
	</c:choose>

		
		<c:choose>
			<c:when test="${course.genre == null}">
				<td><a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video"></a></td>
				<td> 	    				
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Titre :"/> </b> <c:out value="${course.title}" /> <br></a>
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Auteur :"/> </b> <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br></a>			
				</td>
				<td>
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Formation :"/> </b> <c:out value="${course.formation}" /> <br></a>
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Sujet :"/> </b> <c:out value="${course.description}" /> <br></a>
				</td>
				<td nowrap="nowrap">
					<fmt:message key="dateFormat" var="dateFormat" />
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Date :"/> </b> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format> <br></a>
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Dur&eacute;e :"/> </b> <c:out value="${course.durationString}" /> <br></a>
				</td>
				<td nowrap="nowrap">
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Type :"/> </b> <c:out value="${course.type}" /> <br></a>
					<a href="<c:out value="${courseaccess}"/>" id="lienCol"><b><fmt:message key="Consultations :"/> </b> <c:out value="${course.consultations}" /> <br></a>
				</td>
				
			</c:when>
			<c:otherwise>
				<td><a href="<c:out value="${courseaccess}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video"></a></td>
				<td> 	    				
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Titre :"/> </b> <c:out value="${course.title}" /> <br></a>
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Auteur :"/> </b> <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br></a>			
				</td>
				<td>
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Formation :"/> </b> <c:out value="${course.formation}" /> <br></a>
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Sujet :"/> </b> <c:out value="${course.description}" /> <br></a>
				</td>
				<td nowrap="nowrap">
					<fmt:message key="dateFormat" var="dateFormat" />
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Date :"/> </b> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format> <br></a>
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Dur&eacute;e :"/> </b> <c:out value="${course.durationString}" /> <br></a>
				</td>
				<td nowrap="nowrap">
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Type :"/> </b> <c:out value="${course.type}" /> <br></a>
					<a href="<c:out value="${courseaccess}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Consultations :"/> </b> <c:out value="${course.consultations}" /> <br></a>
				</td>
			</c:otherwise>
		</c:choose>
		

		<c:url var="edit" scope="page" value="./editmycourse">
				<c:param name="id" value="${course.courseid}"/>
		</c:url>
		<td class="tdalign" onclick="document.location.href='${edit}'">
			<a href="<c:out value="${edit}"/>"  class="aEdit"><fmt:message key="Editer"/> </a>
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