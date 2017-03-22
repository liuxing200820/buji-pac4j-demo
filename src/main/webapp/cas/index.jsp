<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page import="org.pac4j.core.profile.CommonProfile" %>
<%@ page import="java.util.List" %>
<%@ page import="io.buji.pac4j.subject.Pac4jPrincipal" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	final Pac4jPrincipal principal = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);
	List<CommonProfile> profiles = null;
	if (principal != null) {
		profiles = principal.getProfiles();
	}
%>
<h1>protected area</h1>
<a href="..">Back</a><br />
<br /><br />
profiles: <%=profiles%>
<br/>
<shiro:principal property="profiles"/>
<br/>
<shiro:hasRole name="ROLE_ADMIN">
	hao
</shiro:hasRole>