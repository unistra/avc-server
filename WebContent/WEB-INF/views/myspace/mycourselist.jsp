<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!-- Displays the courses of the list in the table -->
<c:set var="class" value="row1" />
<c:forEach var="course" varStatus="status" items="${courses}">
	<tr class="${class}">
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
				<c:url var="thick_codeform" scope="page" value="./thick_codeform">
					<c:param name="id" value="${course.courseid}"/>
					<c:param name="type" value="flash"/>
					<c:param name="width" value="250"/>
					<c:param name="height" value="120"/>
				</c:url>
				<td><a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video"></a></td>
				<td> 	    				
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Titre :"/> </b> <c:out value="${course.title}" /> <br></a>
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Auteur :"/> </b> <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br></a>			
				</td>
				<td>
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Formation :"/> </b> <c:out value="${course.formation}" /> <br></a>
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Sujet :"/> </b> <c:out value="${course.description}" /> <br></a>
				</td>
				<td nowrap="nowrap">
					<fmt:message key="dateFormat" var="dateFormat" />
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Date :"/> </b> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format> <br></a>
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Dur&eacute;e :"/> </b> <c:out value="${course.durationString}" /> <br></a>
				</td>
				<td nowrap="nowrap">
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Type :"/> </b> <c:out value="${course.type}" /> <br></a>
					<a href="<c:out value="${thick_codeform}"/>" class="thickbox" id="lienCol"><b><fmt:message key="Consultations :"/> </b> <c:out value="${course.consultations}" /> <br></a>
				</td>
			</c:otherwise>
		</c:choose>
		
			
		<td class="tdalign">
			<c:choose>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="flash"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/flash_chip.png" alt="flash"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="flash"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/flash_chip.png" alt="flash"></a>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="tdalign">
			<c:choose>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="real"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/real_chip.png" alt="real"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="real"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/real_chip.png" alt="real"></a>
				</c:otherwise>
			</c:choose>
		</td>
		
		<td class="tdalign">
			<c:url var="edit" scope="page" value="./editmycourse">
					<c:param name="id" value="${course.courseid}"/>
			</c:url>
			<a href="<c:out value="${edit}"/>">edit</a>
	
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