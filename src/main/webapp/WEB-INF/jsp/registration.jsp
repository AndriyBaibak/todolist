<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <title></title>
    <style type="text/css">
        .error {
            color: #ff0000;
        }
    </style>
</head>
<body>


<h1 align="center">Registration</h1>

<form:form commandName="userAdd" action=""
           method='POST'>

    <table align="center">
        <tr>
            <td>User:</td>
            <td><form:input path="userName" type='text'/></td>
            <td><form:errors path="userName" cssClass="error"/></td>
            </br>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:password path="password"/></td>
            <td><form:errors path="password" cssClass="error"/></td>
        <tr>
            <td>Confirm Password:</td>
            <td><form:password path="confirmPassword"/></td>
            <td><form:errors path="confirmPassword" cssClass="error"/></td>
        <tr>
            <td colspan='2'><input type="submit"
                                   value="submit"></td>
            </td>
        </tr>
    </table>

</form:form>
</body>
</html>
