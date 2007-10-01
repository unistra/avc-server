<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="http://jakarta.apache.org/taglibs/datetime-1.0" %>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<table cellspacing="0">
	<tr>
		<th colspan="3" id="courses"><%=Messages._("Les cours", l)%></th>
		<th colspan="2"><%=Messages._("Visualisez", l)%></th>
		<th colspan="4"><%=Messages._("T&eacute;l&eacute;chargez", l)%></th>
	</tr>
	<tr class="row2">
		<td colspan="3">&nbsp;</td>
		<td class="tdalign">
			<img src="../files/img/smile.png" alt="smile"><br>
			smil
		</td>
		<td class="tdalign">
			<img src="../files/img/real_v2.png" alt="real"><br>
			realplayer
		</td>
		<td class="tdalign">
			<img src="../files/img/ogg_v2.png" alt="ogg"><br>
			ogg
		</td>
		<td class="tdalign">
			<img src="../files/img/mp3_v2.png" alt="mp3"><br>
			mp3
		</td>
		<td class="tdalign">
			<img src="../files/img/winzip3.png" alt="zip"><br>
			zip
		</td>
		<td class="tdalign">
			<img src="../files/img/acrobat.png" alt="pdf"><br>
			pdf
		</td>
	</tr>
	
	<!-- Displays the courses of the list in the table -->
	<c:set var="class" value="row1" />
	<c:forEach var="course" varStatus="status" items="${courses}">
		<tr class="${class}">
			<!-- Defines the image file which represents the course type -->
			<c:choose>
				<c:when test="${course.type == 'audio' && course.genre == ''}">
					<c:set var="typeImg" value="sound" />
				</c:when>
				<c:when test="${course.type == 'audio'}">
					<c:set var="typeImg" value="sound_locked" />
				</c:when>
				<c:when test="${course.type == 'video' && course.genre == ''}">
					<c:set var="typeImg" value="video" />
				</c:when>
				<c:when test="${course.type == 'video'}">
					<c:set var="typeImg" value="video_locked" />
				</c:when>
			</c:choose> 
			<td><img src="../files/img/${typeImg}.png" alt="video"></td>
			<td>	    				
				<b><%=Messages._("Titre :", l)%> </b> <c:out value="${course.title}" /> <br>
				<b><%=Messages._("Enseignant :", l)%> </b> <c:out value="${course.name}" /> <br>
				<div id="row${status.count}col1link">
					<a href="javascript:switchDetails('row${status.count}')"><%=Messages._("[+] plus de détails", l)%></a>
				</div>	    				
				<div id="row${status.count}col1details" class="hidden">
 					<b><%=Messages._("Formation :", l)%> </b> <c:out value="${course.formation}" /> <br>
 					<b><%=Messages._("Sujet :", l)%> </b> <c:out value="${course.description}" /> <br>
 					<a href="javascript:switchDetails('row${status.count}')"><%=Messages._("[-] moins de détails", l)%></a>
				</div>	    				
			</td>
			<td>
				<b><%=Messages._("Date :", l)%> </b> <dt:format pattern="dd/MM/yyyy">${course.date.time}</dt:format> <br>
				<b><%=Messages._("Consultations :", l)%> </b> <c:out value="${course.consultations}" /> <br>
				<div id="row${status.count}col2link">
					<a href="javascript:switchDetails('row${status.count}')"><%=Messages._("[+] plus de détails", l)%></a>
				</div>
				<div id="row${status.count}col2details" class="hidden">
 					<b><%=Messages._("Dur&eacute;e :", l)%> </b> <c:out value="${course.durationString}" /> <br>
 					<b><%=Messages._("Type :", l)%> </b> <c:out value="${course.type}" /> <br>
 					<a href="javascript:switchDetails('row${status.count}')"><%=Messages._("[-] moins de détails", l)%></a>
				</div>
			</td>
			<td class="tdalign">
				<c:choose>
					<c:when test="${course.genre == ''}">
						<a href="./recordInterface?id=${course.courseid}"><img src="../files/img/chip.png" alt="chip"></a>
					</c:when>
					<c:otherwise>
						<a href="./codeform?id=${course.courseid}&type=smil&height=100&width=250" title="<%=Messages._("Acc&egrave;s au cours", l)%>" class="thickbox"><img src="../files/img/chip.png" alt="chip"></a>
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdalign">
				<c:choose>
					<c:when test="${course.genre == ''}">
						<a href="./recordInterface?id=${course.courseid}"><img src="../files/img/chip.png" alt="chip"></a>
					</c:when>
					<c:otherwise>
						<a href="./codeform?id=${course.courseid}&type=smil&height=100&width=250" title="<%=Messages._("Acc&egrave;s au cours", l)%>" class="thickbox"><img src="../files/img/chip.png" alt="chip"></a>
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tdalign">
				<a href="."><img src="../files/img/chip.png" alt="chip"></a>
			</td>
			<td class="tdalign">
				<a href="."><img src="../files/img/chip.png" alt="chip"></a>
			</td>
			<td class="tdalign">
				<a href="."><img src="../files/img/chip.png" alt="chip"></a>
			</td>
			<td class="tdalign">
				<a href="."><img src="../files/img/chip.png" alt="chip"></a>
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
	
</table>	