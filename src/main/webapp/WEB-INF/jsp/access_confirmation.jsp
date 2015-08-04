<%--
  Created by IntelliJ IDEA.
  User: OPSKMC
  Date: 7/27/15
  Time: 7:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>OAuth Demo</title>
</head>
<body>
<h1>OAuth Demo Server</h1>
<% if (session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) != null && !(session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) instanceof UnapprovedClientAuthenticationException)) { %>
<%--error--%>
<p>Access could not be granted</p>
<p><%= ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %></p>

<div class="error">
    <h2>Woops!</h2>

    <p>Access could not be granted. (<%= ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>)</p>
</div>
<% } %>

<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
<authz:authorize access="hasRole('ROLE_USER')">
    <form id="confirmationForm" name="confirmationForm" action="<%=request.getContextPath()%>/ws/oauth/authorize" method="post">
        <input name="user_oauth_approval" value="true" type="hidden"/>
        <label><input name="authorize" value="Authorize" type="submit"></label>
    </form>
    <form id="denialForm" name="denialForm" action="<%=request.getContextPath()%>/ws/oauth/authorize" method="post">
        <input name="user_oauth_approval" value="false" type="hidden"/>
        <label><input name="deny" value="Deny" type="submit"></label>
    </form>
</authz:authorize>
</body>
</html>
