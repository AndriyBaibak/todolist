<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isErrorPage="true" %>
<html>
<head>
    <title>Show Error Page</title>
</head>
<body>
<h1>Помилка</h1>
<table width="100%" border="1">

    <tr valign="top">
        <td><b>exception message:</b></td>
        <td>${Exception.message}</td>
    </tr>
    <tr valign="top">
        <td><b>Error in stack trace:</b></td>
        <td>${Exception.stackTrace}</td>
    </tr>
</table>
<p>
    <a href="/todolist/users/${pageContext.request.userPrincipal.name}/allTasks">Повернутися на головну</a>
</p>
</body>
</html>