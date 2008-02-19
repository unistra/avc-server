<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<tr class="row2">
	<td colspan="3">&nbsp;</td>
	<td class="tdalign">
		<img src="../files/styles/${sessionScope.style}/img/smile.png" alt="smile"><br>
		smil
	</td>
	<td class="tdalign">
		<img src="../files/styles/${sessionScope.style}/img/real_v2.png" alt="real"><br>
		realplayer
	</td>
	<td class="tdalign">
		<img src="../files/styles/${sessionScope.style}/img/flash.png" alt="flash"><br>
		flash
	</td>
	<td class="tdalign">
		<img src="../files/styles/${sessionScope.style}/img/ogg_v2.png" alt="ogg"><br>
		ogg
	</td>
	<td class="tdalign">
		<img src="../files/styles/${sessionScope.style}/img/mp3_v2.png" alt="mp3"><br>
		mp3
	</td>
	<td class="tdalign">
		<img src="../files/styles/${sessionScope.style}/img/winzip3.png" alt="zip"><br>
		zip
	</td>
	<td class="tdalign">
		<img src="../files/styles/${sessionScope.style}/img/acrobat.png" alt="pdf"><br>
		pdf
	</td>
</tr>

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
					<c:param name="type" value="real"/>
				</c:url>
				<td><a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video"></a></td>
			</c:when>
			<c:otherwise>
				<c:url var="thick_codeform" scope="page" value="./thick_codeform">
					<c:param name="id" value="${course.courseid}"/>
					<c:param name="type" value="real"/>
					<c:param name="width" value="250"/>
					<c:param name="height" value="120"/>
				</c:url>
				<td><a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/${typeImg}.png" alt="video"></a></td>
			</c:otherwise>
		</c:choose>
		<td>	    				
			<b><fmt:message key="Titre :"/> </b> <c:out value="${course.title}" /> <br>
			<b><fmt:message key="Enseignant :"/> </b> <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br>
			<div id="row${status.count}col1link">
				<a href="javascript:switchDetails('row${status.count}')"><fmt:message key="[+] plus de détails"/></a>
			</div>	    				
			<div id="row${status.count}col1details" class="hidden">
					<b><fmt:message key="Formation :"/> </b> <c:out value="${course.formation}" /> <br>
					<b><fmt:message key="Sujet :"/> </b> <c:out value="${course.description}" /> <br>
					<a href="javascript:switchDetails('row${status.count}')"><fmt:message key="[-] moins de détails"/></a>
			</div>	    				
		</td>
		<td>
			<fmt:message key="dateFormat" var="dateFormat" />
			<b><fmt:message key="Date :"/> </b> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format> <br>
			<b><fmt:message key="Consultations :"/> </b> <c:out value="${course.consultations}" /> <br>
			<div id="row${status.count}col2link">
				<a href="javascript:switchDetails('row${status.count}')"><fmt:message key="[+] plus de détails"/></a>
			</div>
			<div id="row${status.count}col2details" class="hidden">
					<b><fmt:message key="Dur&eacute;e :"/> </b> <c:out value="${course.durationString}" /> <br>
					<b><fmt:message key="Type :"/> </b> <c:out value="${course.type}" /> <br>
					<a href="javascript:switchDetails('row${status.count}')"><fmt:message key="[-] moins de détails"/></a>
			</div>
		</td>
		<td class="tdalign">
			<c:choose>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="smil"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="smil"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
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
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="real"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="tdalign">
			<c:choose>

				<c:when test="${course.type == 'audio'}">
					<img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip">
				</c:when>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="flash"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="flash"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="tdalign">
			<c:choose>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="ogg"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="ogg"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="tdalign">
			<c:choose>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="mp3"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="mp3"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="tdalign">
			<c:choose>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="zip"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="zip"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="tdalign">
			<c:choose>
				<c:when test="${course.genre == null}">
					<c:url var="courseaccess" scope="page" value="./courseaccess">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="pdf"/>
					</c:url>
					<a href="<c:out value="${courseaccess}" />"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:when>
				<c:otherwise>
					<c:url var="thick_codeform" scope="page" value="./thick_codeform">
						<c:param name="id" value="${course.courseid}"/>
						<c:param name="type" value="pdf"/>
						<c:param name="width" value="250"/>
						<c:param name="height" value="120"/>
					</c:url>
					<a href="<c:out value="${thick_codeform}" />" title="<fmt:message key="Acc&egrave;s au cours"/>" class="thickbox"><img src="../files/styles/${sessionScope.style}/img/chip.png" alt="chip"></a>
				</c:otherwise>
			</c:choose>
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