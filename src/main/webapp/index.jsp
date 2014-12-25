<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>

    <title>TodoList</title>
    <style type="text/css">
        #addtask {
            margin-left: 25%; /* Отступ слева */
            width: 50%; /* Ширина слоя */
        }
    </style>
</head>
<body style="background-color:rgba(48, 48, 48, 0.16)">
<body>
<div id="back">
<div id="addtask">
    <form id="add" action="/todolist/add" method="post">

        <div align="center">
        <p><big>Додати нове завдання </big></p>
        </div>

        <p>Описання завдання:<input id="ntask" name="newTask" type="text"></p>

        <p>Введіть кінцеву дату виконання:<input name="date" type="date" name="calendar"></p>
        <div align="center">
        <input type="submit" value="Додати">
        </div>


    </form>
</div>

<table width="700" border="1"
       align="center">

    <caption><big>Усі завдання</big></caption>
    <tr>
        <th>Описання</th>
        <th>Дата створення</th>
        <th>Кінцева дата виконання</th>
    </tr>

    <div text-align="right"
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td valign="middle" align="center">
                <c:out value="${task.description}"></c:out>
            </td>
            <td valign="middle" align="center">
                <c:out value="${task.createdDate}"></c:out>
            </td>
            <td valign="middle" align="center">
                <c:out value="${task.deadline}"></c:out>
            </td>
            <td valign="middle" align="center">
                <form id="del" action="/todolist/del" method="post">
                    <input type="hidden" name="id for delete" value="${task.idTasks}">
                    <p><input type="submit" value="Видалити"></p>
                </form>
            </td>
        </tr>
    </c:forEach>
    </div>

</table>
<embed align="left" src="http://www.clocktag.com/cs/m51.swf"  width="150" height="150" wmode="transparent" type="application/x-shockwave-flash"></embed><embed align="right" src="http://www.clocktag.com/cs/t51.swf"  width="200" height="110"  wmode="transparent" type="application/x-shockwave-flash"></embed>
</div>
</body>
</body>
</html>
