<%--
  Created by IntelliJ IDEA.
  User: Andriy
  Date: 30.03.2015
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
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


<h1>Registration</h1>

<form:form name='registerForm' commandName="userAdd"
      action="/todolist/register" method='POST' >

    <table align="center">
        <tr>
            <td>User:</td>
            <td><form:input path="userName" type='text'/></td>
            <td><form:errors path="userName" cssClass="error" /></td></br>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:input path="password" type='password'/></td>
            <td><form:errors path="password" cssClass="error" /></td></br>
        </tr>
        <tr>
            <td>Confirm Password:</td>
            <td><input type='password' name='confirmPassword' /></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit"
                                   value="submit" /></td>
            </td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
    </form:form>
</body>
</html>
