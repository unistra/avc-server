<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="uploadPage"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/upload.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/upload_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	<div class="banner">
	    		<c:import url="include/banner.jsp" />
	    	</div>
	    	
	    	<div class="divCenter">
	    		<p><fmt:message key="publicationmessage1"/></p>
	    	</div>
	    	
	    	<br>
	    	
	    	<c:if test="${publication_type == 'serverFree'}">
	    	 	<c:set var="borderstyleFree" value="border:2px dotted red;" />
	    	</c:if>
	    	<c:if test="${publication_type == 'serverCas'}">
	       		<c:set var="borderstyleCas" value="border:2px dotted red;" />
	    	</c:if>
	    				
	    	<!-- Choose if you have an account or not -->
	    	<div class="pubLinks">
	    		<div class="divPubCas" style="${borderstyleCas}">
	    			<a class="linkPubCas" href="./authentication_cas?returnPage=publication"><fmt:message key="udsAccount"/></a>
	    		</div>	    		
	    		<div class="divPubFree" style="${borderstyleFree}">
	    			<a class="linkPubFree" href="./publication?publication_type=serverFree"><fmt:message key="free"/></a>
	    		</div>
	    	</div>
	    	<br>
	    	<div class="divCenter">
	    		<p class="${messagetype}"><c:out value="${message}" /></p>
	  	 	</div>
	    		    		    	
	    	<!-- VARS FOR THE FORM -->
	  	  	<c:choose>
				<c:when test="${publication_type != 'serverFree' and publication_type != 'serverCas'}">
					<c:set var="classField" value="txtDisabled" />
					<c:set var="disabledField" value="disabled" />
				</c:when>
				<c:otherwise>
					<c:set var="classField" value="field" />
					<c:set var="disabledField" value="" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${message!=null and visible==null}">
					<c:set var="checkedVisbleField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedVisbleField" value="checked" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(message!=null and restrictionuds==null) or (message==null && publication_type == 'serverFree')}">
					<c:set var="checkedRestUdsField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedRestUdsField" value="checked" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${(message!=null and permission!=null)}">
					<c:set var="checkedPermField" value="checked" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedPermField" value="" />
				</c:otherwise>
			</c:choose>
		
			<!-- FORMULAIRE -->
	    	<form action="<c:url value="./publication_validatepublication"/>" method="POST">
	    		
	    		<input type="hidden" name="id" value="${publication_id}">
	    		<input type="hidden" name="mediapath" value="${publication_mediapath}">
	    		<input type="hidden" name="publication_type" value="${publication_type}">
	    								
				<table>
					 <c:if test="${publication_type == 'serverCas'}">
	   				 <tr class="odd">
						<td title="<fmt:message key="ib_login"/>"><fmt:message key="login"/> : </td>
						<c:choose>
							<c:when test="${user != null and publication_type == 'serverCas'}">
								<td><input type="text" name="login" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly" value="${user.login }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="login" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr class="even">
						<td title="<fmt:message key="ib_email"/>">E-mail : </td>
						<c:choose>
							<c:when test="${user != null and publication_type == 'serverCas'}">
								<td><input type="text" name="email" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly" value="${user.email }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="email" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					</c:if>
					<c:if test="${publication_type == 'serverFree'}">
						<tr class="even">
							<td title="<fmt:message key="ib_email"/>">E-mail : </td>
							<td><input type="text" name="email" class="${classField}"> </td>
						</tr>
					</c:if>
					<tr class="odd">
						<td title="<fmt:message key="ib_title"/>"><fmt:message key="title"/><b class="boldStar">*</b> : </td>
						<td><input type="text" name="title" class="${classField}" <c:out value="${disabledField}"/> value="${title}"> </td>
					</tr>
					<tr class="even">
						<td title="<fmt:message key="ib_desc"/>"><fmt:message key="description"/> : </td>
						<td><input type="text" name="description" class="${classField}" <c:out value="${disabledField}"/> value="${description}"></td>
					 </tr>
					<tr class="odd"> 
						<td title="<fmt:message key="ib_name"/>"><fmt:message key="name"/><b class="boldStar">*</b> : </td>
						<c:choose>
							<c:when test="${user != null and publication_type == 'serverCas' and message==null}">
								<td><input type="text" name="name" class="${classField}" <c:out value="${disabledField}"/> value="${user.lastname }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="name" class="${classField}" <c:out value="${disabledField}"/> value="${name}"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr class="even">
						<td title="<fmt:message key="ib_firstname"/>"><fmt:message key="firstname"/> : </td>
						<c:choose>
							<c:when test="${user!=null and publication_type == 'serverCas' and message==null}">
								<td><input type="text" name="firstname" class="${classField}" <c:out value="${disabledField}"/> value="${user.firstname }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="firstname" class="${classField}" <c:out value="${disabledField}"/> value="${firstname }"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					
					<!--				
					<tr class="odd">
						<td title="<fmt:message key="ib_form"/>"><fmt:message key="ue"/> : </td>
						<td><input type="text" name="ue" class="${classField}" <c:out value="${disabledField}"/> value="${ue}"> </td>
					</tr>-->
									
					<tr class="odd">
						<td title="<fmt:message key="ib_level"/>"><fmt:message key="level"/><b class="boldStar">*</b> : </td>
						<td>
							<select name="level">
								<option value=""></option>
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
						<td title="<fmt:message key="ib_form"/>"><fmt:message key="component"/><b class="boldStar">*</b> : </td>
						<td>
							<select name="component">
								<option value=""></option>
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
						<td title="<fmt:message key="ib_code"/>"><fmt:message key="Code d'acc&egrave;s"/> : </td>
						<td><input type="text" name="genre" class="${classField}" <c:out value="${disabledField}"/> value="${genre }"></td>
					</tr>				
					<tr class="even">
				    	<td title="<fmt:message key="ib_tags"/>">Tags : </td>
				  	  <td><input type="text" name="tags" class="${classField}" <c:out value="${disabledField}"/> value="${tags }"></td>
			    	</tr>
			    	<tr class="odd">
					    <td title="<fmt:message key="ib_visible"/>">Visible : </td>
					    <td><input type="checkbox" name="visible" <c:out value="${checkedVisbleField}"/> <c:out value="${disabledField}"/>></td>
			   		</tr>
			   		<tr class="even">
					  	<td title="<fmt:message key="ib_restrictionuds"/>">Restriction Uds : </td>
					   	<td><input type="checkbox" name="restrictionuds" <c:out value="${checkedRestUdsField}"/> <c:out value="${disabledField}"/>><font class="chpsHd"><fmt:message key="uploadmessage6"/></font></td>
			   		</tr>
			   		<tr class="odd">
					  	<td title="<fmt:message key="ib_permission"/>"><fmt:message key="permission"/><b class="boldStar">*</b> : </td>
					   	<td><input type="checkbox" name="permission" <c:out value="${checkedPermField}"/> <c:out value="${disabledField}"/>><font class="chpsHd"><fmt:message key="uploadmessage7"/></font></td>
			   		</tr>
			   		
			    	<tr>
			    		<td class="chpsObl"><b class="boldStar">*</b>: <fmt:message key="requiredField"/></td>
			    	</tr>
					<tr>
						<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible'" value="<fmt:message key="publier"/>" <c:out value="${disabledField}"/>> </td>
						<td><img id="process" src="../files/img/squaresCircle.gif" /></td>
					</tr>
				
				</table>
			</form>
			
			<br>
			
			<div class="divCenter">
	    		<p><fmt:message key="publicationmessage2"/></p>
	    		<p><fmt:message key="publicationmessage3"/><a href="http://audiovideocours.u-strasbg.fr/avc/test">http://audiovideocours.u-strasbg.fr/avc/test</a></p>
	    		<p><fmt:message key="publicationmessage4"/></p>
	    	</div>
			
    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
