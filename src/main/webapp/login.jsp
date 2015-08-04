<%--
  Created by IntelliJ IDEA.
  User: OPSKMC
  Date: 7/28/15
  Time: 6:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
</head>
<body>
<c:if test="${not empty param.authentication_error}">
    <h1>Woops!</h1>

    <p class="error">Your login attempt was not successful.</p>
</c:if>
<c:if test="${not empty param.authorization_error}">
    <h1>Woops!</h1>

    <p class="error">You are not permitted to access that resource.</p>
</c:if>

<form id="loginForm" name="loginForm"
      action="<c:url value="/login.do"/>" method="post">
    <p>
        <label>Username: <input type='text' name='j_username'
                                /></label>
    </p>
    <p>
        <label>Password: <input type='text' name='j_password'
                                /></label>
    </p>

    <p>
        <input name="login" value="Login" type="submit"/>
    </p>
</form>
</body>
</html>
