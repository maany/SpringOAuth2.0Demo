<%--
  Created by IntelliJ IDEA.
  User: OPSKMC
  Date: 7/27/15
  Time: 8:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OAuth Error</title>
</head>
<body>

    <p><c:out value="${message}"/> (<c:out value="${error.summary}"/>)</p>
    <p>Please go back to your client application and try again, or contact the owner and ask for support</p>

</body>
</html>
