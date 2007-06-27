<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:useBean id="sqlbean1" class="paquet.SqlBean" />
<jsp:useBean id="sqlbean2" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Projet AudioCours</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
<script type="text/javascript">

var enablepersist="off" //Enable saving state of content structure using session cookies? (on/off)
var collapseprevious="no" //Collapse previously open content when opening present? (yes/no)

if (document.getElementById){
document.write('<style type="text/css">')
document.write('.switchcontent{display:none}')
document.write('.switchtriangle{display:block}')
document.write('</style>')
}


function getElementbyClass(classname){
ccollect=new Array()
var inc=0
var alltags=document.all? document.all : document.getElementsByTagName("*")
for (i=0; i<alltags.length; i++){
if (alltags[i].className==classname)
ccollect[inc++]=alltags[i]
}
}

function contractcontent(omit){
var inc=0
while (ccollect[inc]){
if (ccollect[inc].id!=omit)
ccollect[inc].style.display="none"
inc++
}
}

function expandcontent(cid){
if (typeof ccollect!="undefined"){
if (collapseprevious=="yes")
contractcontent(cid)
document.getElementById(cid).style.display=(document.getElementById(cid).style.display!="block")? "block" : "none"
	}
}

function expandTriangle(imgId){
if (typeof ccollect!="undefined"){
if (collapseprevious=="yes")
contractcontent(imgId)
document.getElementById(imgId).style.display=(document.getElementById(imgId).style.display!="none")? "none" : "block"
	}
}

function revivecontent(){
contractcontent("omitnothing")
selectedItem=getselectedItem()
selectedComponents=selectedItem.split("|")
for (i=0; i<selectedComponents.length-1; i++)
document.getElementById(selectedComponents[i]).style.display="block"
}

function get_cookie(Name) {
var search = Name + "="
var returnvalue = "";
if (document.cookie.length > 0) {
offset = document.cookie.indexOf(search)
if (offset != -1) {
offset += search.length
end = document.cookie.indexOf(";", offset);
if (end == -1) end = document.cookie.length;
returnvalue=unescape(document.cookie.substring(offset, end))
}
}
return returnvalue;
}

function getselectedItem(){
if (get_cookie(window.location.pathname) != ""){
selectedItem=get_cookie(window.location.pathname)
return selectedItem
}
else
return ""
}

function saveswitchstate(){
var inc=0, selectedItem=""
while (ccollect[inc]){
if (ccollect[inc].style.display=="block")
selectedItem+=ccollect[inc].id+"|"
inc++
}

//document.cookie=window.location.pathname+"="+selectedItem
}

function do_onload(){
getElementbyClass("switchcontent")
if (enablepersist=="on" && typeof ccollect!="undefined")
revivecontent()
}

if (window.addEventListener)
window.addEventListener("load", do_onload, false)
else if (window.attachEvent)
window.attachEvent("onload", do_onload)
else if (document.getElementById)
window.onload=do_onload

if (enablepersist=="on" && document.getElementById)
window.onunload=saveswitchstate

</script>

</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" style="margin: 0">

<jsp:include page="../includes/banniere.jsp"/>
<!--suite-->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><table width="757" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="../_mm/zero.gif" width="10" height="30"></td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45" valign="top"><img src="../_mm/pyramide_gris.jpg" width="45" height="50"></td>
                <td valign="bottom">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="ArialBlack18">Cours en Direct</td>
                    </tr>
                    <tr>
                      <td height="6" style="background-image:url(../_mm/titre_trait.gif)"><img src="../_mm/zero.gif" width="712" height="6"></td>
                    </tr>
                  </table>
                  </td>
              </tr>
            </table></td>
        </tr>
        <tr>
    		<td valign="top">

				<FORM name="choix" method="POST" action="interface.jsp">
		    	<INPUT type="hidden" name="ip"/>
		    	<INPUT type="hidden" name="type"/>
    	    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
    	        	<td width="65">&nbsp;</td>
    	            <td>
    	            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10">&nbsp;</td>
							<td width="5">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						
						<%
							/* Recherche de tous les bâtiments des salles */
							sqlbean1.setWriter(out);
							sqlbean1.connect();
							sqlbean1.query("SELECT DISTINCT(\"Lieu\") FROM \"Live\"");
							
							int numDiv = 1;
							
							while( sqlbean1.next() ) {
								String batiment = (String) sqlbean1.getColumn("Lieu");
						%>
								<tr>
									<td width="10">
										<a href="javascript:expandcontent('sc_<%= numDiv%>');expandcontent('img_<%= numDiv%>')"></a>
											<div id="imgBas_<%= numDiv%>" class="switchtriangle">
									        	<img src="../_mm/triangle_gris.gif" width="10" height="10" border="0" onClick="expandTriangle('imgBas_<%= numDiv%>')">
									        </div>
									        <div id="img_<%= numDiv%>" class="switchcontent">
												<img src="../_mm/triangle_orange.gif" width="10" height="10" border="0" onClick="expandTriangle('imgBas_<%= numDiv%>')">
									        </div>
						   				
									</td>
									<td width="5">&nbsp;</td>
									<td class="Arial12Bold">
									<a href="javascript:expandcontent('sc_<%= numDiv%>');expandcontent('img_<%= numDiv%>')" onClick="expandTriangle('imgBas_<%= numDiv%>')">
											<%= batiment %>
									</a></td>
		                		</tr>
		                		<tr>
								<td width="10">&nbsp;</td>
								<td width="5">&nbsp;</td>
	                  			<td>
	                  				<div id="sc_<%= numDiv%>" class="switchcontent">
	                      				<table width="100%" border="0" cellpadding="2" cellspacing="0">
	                      				<tr>
	                      					<td class="Arial11">
	              								<table width="100%" border="0" cellspacing="0" cellpadding="0">		
		                <%
		                		/* Parcours de la liste des salles d'un batiment, affichage de 3 salles par ligne */
		                		sqlbean2.setWriter(out);
		                		sqlbean2.connect();
		                		sqlbean2.pquery("SELECT * FROM \"Live\" WHERE \"Lieu\"= ? ", batiment);
		                		while( sqlbean2.next() ) {
		                			int nbSalles = 1; //Nombre de salles à afficher dans la liste
		                			String[] salle1 = new String[3];
		                			String[] salle2 = new String[3];
		                			String[] salle3 = new String[3];
		                			
		                			salle1[0] = (String) sqlbean2.getColumn("Salle");
		                			salle1[1] = (String) sqlbean2.getColumn("Chemin");
		                			salle1[2] = (String) sqlbean2.getColumn("TypeLive");
		                			
		                			if( sqlbean2.next() ) {
		                				nbSalles++;
		                				salle2[0] = (String) sqlbean2.getColumn("Salle");
			                			salle2[1] = (String) sqlbean2.getColumn("Chemin");
			                			salle2[2] = (String) sqlbean2.getColumn("TypeLive");
		                			}
									if( sqlbean2.next() ) {
										nbSalles++;
										salle3[0] = (String) sqlbean2.getColumn("Salle");
			                			salle3[1] = (String) sqlbean2.getColumn("Chemin");
			                			salle3[2] = (String) sqlbean2.getColumn("TypeLive");
		                			}
		                %>
                		
											<tr>
												<td width="45"><img src="../_mm/zero.gif" width="45" height="10"></td>
												<td class="Arial12"><strong><%= salle1[0] %></strong></td>
												<% if( nbSalles >= 2 ) { %>
												<td class="Arial12"><strong><%= salle2[0] %></strong></td>
												<% } else { %>
											    <td class="Arial12"><strong>&nbsp;</strong></td>
											    <% }
												if( nbSalles == 3 ) { %>
												<td class="Arial12"><strong><%= salle3[0] %></strong></td>
												<% } else { %>
											    <td class="Arial12"><strong>&nbsp;</strong></td>
											    <% } %>
											</tr>
											<tr>
												<td><img src="../_mm/zero.gif" width="10" height="5"></td>
											    <td><img src="../_mm/zero.gif" width="10" height="5"></td>
											    <td><img src="../_mm/zero.gif" width="10" height="5"></td>
											    <td><img src="../_mm/zero.gif" width="10" height="5"></td>
											</tr>
											<tr>
											  <td>&nbsp;</td>
											  <td width="200">
											  	<INPUT	name="salle" type="image" src="../_mm/photos_salles/<%= salle1[1].replace('.','_') %>.jpg" alt="<%= salle1[0] %>" onclick="document.choix.ip.value='<%= salle1[1]%>';document.choix.type.value='<%= salle1[2]%>';document.choix.submit();">
											  </td>
											  <% if( nbSalles >= 2 ) { %>
											  <td width="200">
											    <INPUT	name="salle" type="image" src="../_mm/photos_salles/<%= salle2[1].replace('.','_') %>.jpg" alt="<%= salle2[0] %>" onclick="document.choix.ip.value='<%= salle2[1]%>';document.choix.type.value='<%= salle2[2]%>';document.choix.submit();">
											  </td>
											  <% } else { %>
											  <td>&nbsp;</td>
											  <% }
												if( nbSalles == 3 ) { %>
											  <td>
											     <INPUT	name="salle" type="image" src="../_mm/photos_salles/<%= salle3[1].replace('.','_') %>.jpg" alt="<%= salle3[0] %>" onclick="document.choix.ip.value='<%= salle3[1]%>';document.choix.type.value='<%= salle3[2]%>';document.choix.submit();">
											  </td>
											  <% } else { %>
											  <td>&nbsp;</td>
											  <% } %>
											</tr>
											<tr>
												<td>&nbsp;</td>
											    <td><img src="../_mm/zero.gif" width="10" height="20"></td>
											    <td>&nbsp;</td>
											    <td>&nbsp;</td>
                							</tr>
                      	<%
		                		}
		                	sqlbean2.disconnect();
                      		numDiv++;
                      	%>
                      		</table>
                      					</td>
                      				</tr>
                      				</table>
                      			</div>
                      		</td>
                      	</tr>
                      	<%
							}
							sqlbean1.disconnect();
                      	%>

    	            	</table>
    	            </td>
				</tr>
				</table>
				</FORM>
			</td>
  		</tr>
		</table>
	</td>
</tr>
</table>
</body>
</html>