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
            margin-left: 15%; /* Отступ слева */
            width: 70%; /* Ширина слоя */

            padding: 10px; /* Поля вокруг текста */
        }
    </style>
</head>
<body>
<div id="addtask">
    <form id="add" action="todolist/add" method="post">

        <p>Додати нове завдання:<input type="submit" value="Додати"></p>

        <p>Описання завдання:<input id="ntask" name="newTask" type="text"></p>

        <p>Введіть кінцеву дату виконання:<input name="date" type="date" name="calendar"></p>


    </form>
</div>

<table width="700" bgcolor="#faebd7" border="1"
       align="center">

    <caption>Усі завдання</caption>
    <tr>
        <th>Описання</th>
        <th>Дата створення</th>
        <th>Кінцева дата виконання</th>
    </tr>

    <div text-align="center"
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
                <form id="del" action="todolist/del" method="post">
                    <input type="hidden" name="id for delete" value="${task.idTasks}">
                    <p><input type="submit" value="Видалити"></p>
                </form>
            </td>
        </tr>
    </c:forEach>
    </div>
</table>

</body>
</html>
