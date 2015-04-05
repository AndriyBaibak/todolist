<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>


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

<form:form name='registerForm' commandName="userAdd"
      action="/todolist/registration" method='POST' >

    <table align="center">
        <tr>
            <td>User:</td>
            <td><form:input path="userName" type='text'/></td>
            <td><form:errors path="userName" cssClass="error" /></td></br>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:input  id="password" path="password" type='password'/></td>
            <td><form:errors path="password" cssClass="error" /></td></br>
        </tr>
        <tr>
            <td>Confirm Password:</td>
            <td><input id="confirmPassword" type='password' name='confirmPassword' /></td>
            <td><form:errors path="confirmPassword" cssClass="error" /></td></br>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit"
                                   value="submit" ></td>
            </td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
    </form:form>
</body>
</html>
