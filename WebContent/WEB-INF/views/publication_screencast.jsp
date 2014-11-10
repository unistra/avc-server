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
        <link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/upload_ie6.css" media="screen" />
        <script defer type="text/javascript" src="../files/js/pngfix.js"></script>
    <![endif]-->

    <script type="text/javascript" src="../files/thickbox/jquery.js"></script>
    <script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
    <script type="text/javascript" src="../files/js/dropzone.js"></script>
    <script>
    $(document).ready(function() {
        
    	Dropzone.options.uploadform = {
        	acceptedFiles: 'video/mp4',
        	addRemoveLinks: false,
       		maxFiles: 1,
       		maxFilesize: 5000,
         	uploadMultiple: false,
			previewTemplate: "<div class=\"dz-preview dz-file-preview\"><div class=\"dz-details\"><div class=\"dz-filename\"><span data-dz-name></span></div><div class=\"dz-size\" data-dz-size></div></div></div>",
        	dictDefaultMessage: '',
        	autoProcessQueue: false,
        	autoDiscover: false,
        	paramName: 'media',
        	previewsContainer: '#dropzonePreview',
        	clickable: false,
        	accept: function(file, done) {
				$('#sbmtbtn').removeAttr('disabled');
				document.getElementById('error').innerHTML = '';
            	done();
        	},
        	error: function(file, msg){
            	document.getElementById('error').innerHTML = msg;
            	this.removeAllFiles();
            	$('#sbmtbtn').attr('disabled', true);
            }, 
            init: function() {
                                 
            	var myDropzone = this;
            	$('#sbmtbtn').attr('disabled', true)
            	
            	this.element.querySelector("input[type=submit]").addEventListener("click", function(e) {

	                e.preventDefault();
	                e.stopPropagation();
	                            
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
				    				
					if(erreur==1) {
						javascript:document.getElementById('process').style.visibility='hidden';
				    	return (false);
					}
					else {
						myDropzone.processQueue();
					}	
                });
                        
                        
                this.on("maxfilesexceeded", function(file) {
                   this.removeAllFiles();
                   this.addFile(file);
                });
                
                this.on("success", function(file, responseText) {
                	 document.open();
                	 document.write(responseText);
                	 document.close();
                  }); 
            } 

       };
    });
    </script>
    
    
    <!-- google analytics -->
    <c:import url="include/google_analytics.jsp" />
    
  </head>
  
  <body>
    <div class="main">
        <div class="contents">
            <div class="banner">
                <c:import url="include/banner.jsp" />
            </div>
            
            <div class="divCenter">
                <p><fmt:message key="publicationmessage1"/> ${univAcronym}</p>
            </div>
            
            <br>
            
            <c:if test="${publication_type == 'serverFree' and pubFree == true}">
                <c:set var="borderstyleFree" value="border:2px dotted red;" />
            </c:if>
            <c:if test="${publication_type == 'serverCas'}">
                <c:set var="borderstyleCas" value="border:2px dotted red;" />
            </c:if>
            <c:if test="${publication_type == 'serverTest' and pubTest == true}">
                <c:set var="borderstyleTest" value="border:2px dotted red;" />
            </c:if>
            <c:if test="${publication_type == 'serverLocal'}">
                <c:set var="borderstyleLocal" value="border:2px dotted red;" />
            </c:if>
                        
            <!-- Choose if you have an account or not -->
            <div class="pubLinks">
                <c:if test="${pubFree == true}">
                    <div class="divPubFree" style="${borderstyleFree}">
                        <a class="linkPubFree" href="./publication_screencast?publication_type=serverFree"><fmt:message key="free"/></a>
                    </div>
                </c:if>
                <c:if test="${pubTest == true}">
                    <div class="divPubTest" style="${borderstyleTest}">
                        <a class="linkPubTest" href="./publication_screencast?publication_type=serverTest"><fmt:message key="test"/></a>
                    </div>
                </c:if>
                <div class="divPubCas" style="${borderstyleCas}">
                    <a class="linkPubCas" href="./authentication_cas?returnPage=publication_screencast"><fmt:message key="udsAccount"/> ${univAcronym}</a>
                </div>   
                <div class="divPubLocal" style="${borderstyleLocal}">
                    <a class="linkPubLocal" href="./authentication_local?returnPage=publication_screencast"><fmt:message key="authLocalLink"/></a>
                </div> 
            </div>
            
            <br>
            
            <div class="divCenter">
                <p id="error" class="${messagetype}"><c:out value="${message}" /></p>
            </div>
                                    
            <!-- VARS FOR THE FORM -->
            <c:choose>
                <c:when test="${(publication_type != 'serverFree' or pubFree == false) and publication_type != 'serverCas' and publication_type != 'serverLocal' and (publication_type != 'serverTest' or pubTest == false)}">
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
                <c:when test="${message!=null and download==null}">
                    <c:set var="checkedDownloadField" value="" />
                </c:when>
                <c:otherwise>
                    <c:set var="checkedDownloadField" value="checked" />
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${(message!=null and restrictionuds==null) or (message==null && publication_type == 'serverFree' and pubFree == true) or (message==null && publication_type == 'serverTest' and pubTest == true) or (message==null && publication_type == 'serverLocal')}">
                    <c:set var="checkedRestUdsField" value="" />
                </c:when>
                <c:otherwise>
                    <c:set var="checkedRestUdsField" value="checked" />
                </c:otherwise>
            </c:choose>

        
            <!-- FORMULAIRE -->
            
            <c:if test="${publication_type != null}">
            
            <form id="uploadform" name="uploadform" class="dropzone" action="<c:url value="./publication_screencast_validatepublication"/>" enctype="multipart/form-data" method="POST">
                
                <input type="hidden" name="publication_type" value="${publication_type}">
                                        
                <table>
                     <c:if test="${(publication_type == 'serverCas') or (publication_type == 'serverLocal')}">
                     <tr class="odd">
                        <td title="<fmt:message key="ib_login"/>"><fmt:message key="login"/> : </td>
                        <c:choose>
                            <c:when test="${user != null and ((publication_type == 'serverCas') or (publication_type == 'serverLocal'))}">
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
                            <c:when test="${user != null and ((publication_type == 'serverCas') or (publication_type == 'serverLocal'))}">
                                <td><input type="text" name="email" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly" value="${user.email }" > </td>
                            </c:when>
                            <c:otherwise>
                                <td><input type="text" name="email" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly"> </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    </c:if>
                    <c:if test="${publication_type == 'serverFree' and pubFree == true}">
                        <tr class="even">
                            <td title="<fmt:message key="ib_email"/>">E-mail : </td>
                            <td><input type="text" name="email" class="${classField}"> </td>
                        </tr>
                    </c:if>
                    <tr class="odd">
                        <td title="<fmt:message key="ib_title"/>"><fmt:message key="title"/><b class="boldStar">*</b> : </td>
                        <c:choose>
                        <c:when test="${publication_type == 'serverTest' and pubTest == true and title == null}">
                            <td><input type="text" name="title" class="${classField}" <c:out value="${disabledField}"/> value="test"> </td>
                        </c:when>
                        <c:otherwise>
                            <td><input type="text" name="title" class="${classField}" <c:out value="${disabledField}"/> value="${title}"> </td>
                        </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr class="even">
                        <td title="<fmt:message key="ib_desc"/>"><fmt:message key="description"/> : </td>
                        <td><input type="text" name="description" class="${classField}" <c:out value="${disabledField}"/> value="${description}"></td>
                     </tr>
                    <tr class="odd"> 
                        <td title="<fmt:message key="ib_name"/>"><fmt:message key="name"/><b class="boldStar">*</b> : </td>
                        <c:choose>
                            <c:when test="${user != null and ((publication_type == 'serverCas') or (publication_type == 'serverLocal')) and message==null}">
                                <td><input type="text" name="name" class="${classField}" <c:out value="${disabledField}"/> value="${user.lastname }" > </td>
                            </c:when>
                            <c:when test="${publication_type == 'serverTest' and pubTest == true and name == null}">
                                <td><input type="text" name="name" class="${classField}" <c:out value="${disabledField}"/> value="test"> </td>
                            </c:when>
                            <c:otherwise>
                                <td><input type="text" name="name" class="${classField}" <c:out value="${disabledField}"/> value="${name}"> </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr class="even">
                        <td title="<fmt:message key="ib_firstname"/>"><fmt:message key="firstname"/> : </td>
                        <c:choose>
                            <c:when test="${user!=null and ((publication_type == 'serverCas') or (publication_type == 'serverLocal')) and message==null}">
                                <td><input type="text" name="firstname" class="${classField}" <c:out value="${disabledField}"/> value="${user.firstname }" > </td>
                            </c:when>
                            <c:otherwise>
                                <td><input type="text" name="firstname" class="${classField}" <c:out value="${disabledField}"/> value="${firstname }"> </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                                
                    <tr class="odd">
                        <td title="<fmt:message key="ib_level"/>"><fmt:message key="level"/><b class="boldStar">*</b> : </td>
                        <td>
                            <select name="level" <c:out value="${disabledField}"/> >
                                <option>
                                <c:forEach var="levels" items="${levels}" varStatus="status">
                                    <c:choose>
                                        <c:when test="${publication_type == 'serverTest' and pubTest == true and levelSelected == null and status.count == 1}">
                                            <c:set var="selected" value="selected" />
                                        </c:when>
                                        <c:when test="${levelSelected == levels.code}">
                                            <c:set var="selected" value="selected" />
                                        </c:when>
                                    </c:choose>
                                    <option value="${levels.code}" title="${levels.name}" ${selected}>${levels.name}</option>
                                    <c:remove var="selected"/>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                                
                    <tr class="even">
                        <td title="<fmt:message key="ib_form"/>"><fmt:message key="component"/><b class="boldStar">*</b> : </td>
                        <td>
                            <select name="component" <c:out value="${disabledField}"/> >
                                <option>
                                <c:forEach var="discipline" items="${disciplines}" varStatus="status">
                                    <c:choose>
                                    <c:when test="${publication_type == 'serverTest' and pubTest == true and discSelected == null and status.count == 1}">
                                            <c:set var="selected" value="selected" />
                                        </c:when>
                                    <c:when test="${discSelected == discipline.codecomp}">
                                        <c:set var="selected" value="selected" />
                                    </c:when>
                                    </c:choose>
                                    <option value="${discipline.codecomp}" title="${discipline.namecomp}" ${selected}>${discipline.namecomp}</option>
                                    <c:remove var="selected"/>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    
                    
                    <tr class="odd">
                        <td title="<fmt:message key="ib_code"/>"><fmt:message key="Code d'acc&egrave;s"/> : </td>
                        <c:choose>
                        <c:when test="${publication_type == 'serverTest' and pubTest == true}">
                            <td><input type="text" name="genre" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly" value="${testKeyWord1}"></td>
                        </c:when>
                        <c:otherwise>
                            <td><input type="text" name="genre" class="${classField}" <c:out value="${disabledField}"/> value="${genre }"></td>
                        </c:otherwise>
                        </c:choose>
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
                        <td title="<fmt:message key="ib_dl"/>"><fmt:message key="Telecharger"/> : </td>
                        <td><input type="checkbox" name="download" <c:out value="${checkedDownloadField}"/> <c:out value="${disabledField}"/>></td>
                    </tr>
                    <tr class="odd">
                        <td title="<fmt:message key="ib_restrictionuds"/> ${univName}"><fmt:message key="restrictionuds"/> ${univAcronym} : </td>
                        <td><input type="checkbox" name="restrictionuds" <c:out value="${checkedRestUdsField}"/> <c:out value="${disabledField}"/>><span class="littleFont"><fmt:message key="uploadmessage6"/> ${univAcronym}</span></td>
                    </tr>
                    <tr class="even">
                        <td title="<fmt:message key="ib_file"/>"><fmt:message key="file"/><b class="boldStar">*</b> : </td>
                        <td>
                        <div class="fallback">
                        	<input type="file" name="media" id="media" class="field">
                        </div>
                        <div id="dropzonePreview"><fmt:message key="dragndrop"/></div>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="chpsObl"><b class="boldStar">*</b>: <fmt:message key="requiredField"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input type="submit" id="sbmtbtn" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible'" value="<fmt:message key="publier"/>"> </td>
                        <td><img id="process" src="../files/img/squaresCircle.gif" alt="process"/></td>
                    </tr>
                
                </table>
            </form>
                        
            <br>
            
            <div class="divCenter">
                <p><fmt:message key="publicationmessage2"/></p>
                <p><fmt:message key="publicationmessage3"/><a href="${serverUrl}/avc/test">${serverUrl}/avc/test</a></p>
                <p><fmt:message key="publicationmessage4"/></p>
            </div>
            
            </c:if>
            
        </div>
            
        <div class="footer">
            <c:import url="include/footer.jsp" />
        </div>
    </div>
  </body>
</html>
