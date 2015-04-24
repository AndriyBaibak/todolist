<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
</head>
<body>
<c:redirect  url="/users/${pageContext.request.userPrincipal.name}/allTasks" />
</body>
</html>
