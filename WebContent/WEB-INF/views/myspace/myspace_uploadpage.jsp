<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
  <head>
    
    <meta charset="utf-8">

    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="uploadPage"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/upload.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
	
	<!-- to validate upload form -->
	<script type="text/javascript">
	
		$(document).ready(function(){
			$("#uploadform").submit(function(){
				var erreur = 0;

				if(document.uploadform.title.value=="") {
			    	document.getElementById('error').innerHTML = "${err_title}";
					erreur = 1;
			    }
			    else if(document.uploadform.name.value=="") {
			    	document.getElementById('error').innerHTML = "${err_name}";
					erreur = 1;
			    }
			    else if(document.uploadform.level.value=="") {
			    	document.getElementById('error').innerHTML = "${err_lvl}";
					erreur = 1;
			    }
			    else if(document.uploadform.component.value=="") {
			    	document.getElementById('error').innerHTML = "${err_component}";
					erreur = 1;
			    }
			    else if(document.uploadform.media.value=="") {
					document.getElementById('error').innerHTML = "${err_file}";
					erreur = 1;
				}
			    else {
					var file = document.uploadform.media.value;
					var uploadFormats = "${uploadFormats}"
					var tokenUploadFormats = uploadFormats.split(" ");
					var fichier = document.uploadform.media.value;
					var ext = fichier.substring(fichier.lastIndexOf(".")+1,fichier.length).toLowerCase();
					var isExtVal = false;
					
					for ( var i = 0; i < tokenUploadFormats.length; i++ ) {
						if(!isExtVal) {
							isExtVal = ext == tokenUploadFormats[i].toLowerCase();
						}
					}
					
					if(!isExtVal) {
						document.getElementById('error').innerHTML = "${err_fileformat}";
						erreur = 1;
					}
			    }
					
				if(erreur==1) {
					javascript:document.getElementById('process').style.visibility='hidden';
					return (false);
				}
				else {
					return (true);
				}		    
			});
		});
	</script>
				
	<!-- google analytics -->
	<c:import url="../include/google_analytics.jsp" />
	
  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	<div class="banner">
	    		<c:import url="../include/banner.jsp" />
	    	</div>
	    	
	    	<div class="divCenter">
	    		<p class="error" id="error"><c:out value="${message}" /></p>
	  	 	</div>
	  	 	
	  	 	
	  	 	<c:choose>
				<c:when test="${message!=null and visible==null}">
					<c:set var="checkedVisbleField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedVisbleField" value="checked" />
				</c:otherwise>
			</c:choose>
				<c:choose>
				<c:when test="${message!=null and download==null}">
					<c:set var="checkedDownloadField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedDownloadField" value="checked" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${hd==null}">
					<c:set var="checkedHdField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedHdField" value="checked" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${message!=null and restrictionuds==null}">
					<c:set var="checkedRestUdsField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedRestUdsField" value="checked" />
				</c:otherwise>
			</c:choose>		
	    	
	    	<form action="<c:url value="./myspace_mediaupload"/>" method="post" enctype="multipart/form-data" name="uploadform" id="uploadform">
				<table>
				<tr class="odd"> 
					<td title="<fmt:message key="ib_login"/>"><fmt:message key="login"/> : </td>
					<td><input type="text" value="${user.login}" readonly="readonly" class="txtDisabled"> </td>
				</tr>
				<tr class="even"> 
					<td title="<fmt:message key="ib_email"/>">E-mail : </td>
					<td><input type="text" name="email" class="txtDisabled" readonly="readonly" value="${user.email }" > </td>
				</tr>
				<tr class="odd">
					<td title="<fmt:message key="ib_title"/>"><fmt:message key="title"/><b class="boldStar">*</b> : </td>
					<td><input type="text" name="title" class="field" value="${title}"> </td>
				</tr>
				<tr class="even">
					<td title="<fmt:message key="ib_desc"/>"><fmt:message key="description"/> : </td>
					<td><input type="text" name="description" class="field" value="${description}"></td>
				 </tr>
				<tr class="odd">
					<td title="<fmt:message key="ib_name"/>"><fmt:message key="name"/><b class="boldStar">*</b> : </td>
				 	<c:choose>
						<c:when test="${message==null}">
							<td><input type="text" name="name" value="${user.lastname}" class="field"> </td>
						</c:when>
						<c:otherwise>
							<td><input type="text" name="name" value="${name}" class="field"> </td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr class="even">
					<td title="<fmt:message key="ib_firstname"/>"><fmt:message key="firstname"/> : </td>
				 	<c:choose>
						<c:when test="${message==null}">
							<td><input type="text" name="firstname" value="${user.firstname}" class="field"> </td>
						</c:when>
						<c:otherwise>
							<td><input type="text" name="firstname" value="${firstname}" class="field"> </td>
						</c:otherwise>
					</c:choose>
				</tr>
								
				<tr class="odd">
						<td title="<fmt:message key="ib_level"/>"><fmt:message key="level"/><b class="boldStar">*</b> : </td>
						<td>
							<select name="level">
								<option>
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
								<option>
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
					<td><input type="password" name="genre" class="field" value="${genre}"></td>
				</tr>
				<tr class="even">
				   	<td title="<fmt:message key="ib_tags"/>">Tags :</td>
					<td><input type="text" name="tags" class="field" value="${tags}"></td>
			    </tr>
			    <tr class="odd">
				    <td title="<fmt:message key="ib_visible"/>">Visible :</td>
				    <td><input type="checkbox" name="visible" <c:out value="${checkedVisbleField}"/>></td>
			   	</tr>
			   	<tr class="even">
				    <td title="<fmt:message key="ib_dl"/>"><fmt:message key="Telecharger"/> :</td>
				    <td><input type="checkbox" name="download" <c:out value="${checkedDownloadField}"/>></td>
			   	</tr>
				<tr class="odd">
				    <td title="<fmt:message key="ib_restrictionuds"/> ${univName}"><fmt:message key="restrictionuds"/> ${univAcronym} :</td>
				    <td><input type="checkbox" name="restrictionuds" <c:out value="${checkedRestUdsField}"/>> <span class="littleFont"><fmt:message key="uploadmessage6"/> ${univAcronym}</span></td>
			   	</tr>
				<tr class="even">
					<td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/><b class="boldStar">*</b> : </td>
					<td><input type="file" name="media" id="media" class="field"> </td>
				</tr>
				<tr>
			    	<td class="chpsObl"><b class="boldStar">*</b>: <fmt:message key="requiredField"/></td>
			    	<td></td>
			    </tr>
				<tr>
					<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible';" value="<fmt:message key="sendFile"/>"> </td>
					<td><img id="process" src="../files/img/squaresCircle.gif" alt="process..."/></td>
				</tr>
				<tr>
					<td><a href="<c:url value="${gobackurl}" />"><fmt:message key="Retour"/></a></td>
					<td></td>
		    	</tr>
				
				</table>
			</form>
			   
			
			<br>
			<p class="message"><fmt:message key="uploadmessage1"/></p>
			<br>
			<p class="message"><fmt:message key="uploadmessage2"/></p>
			<br>
			<p class="message"><fmt:message key="uploadmessage3"/></p>
			<br>
			<p class="message"><fmt:message key="uploadmessage5"/></p>

    	</div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
