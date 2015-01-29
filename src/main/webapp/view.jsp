<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>


<html>
<head>

    <title>TodoList</title>
    <style type="text/css">
        #addtask {
            margin-left: 25%;
            width: 50%;
        }
    </style>
</head>
<script type="text/javascript">
    function update(txt){

        document.getElementById('description').innerHTML = txt.value;
        var id = document.getElementById('idTask').value;
        var newDescription = txt.value;
        $.ajax({
            type: "POST",
            url: "/todolist/update",
            data:{"newDescription":newDescription,"id":id}
        });
    }
</script>
<script type="text/javascript">
    function change(idInput, idDescription) {
        if(document.getElementById(idInput).style.display=='none') {
            document.getElementById(idInput).style.display = '';
            document.getElementById(idDescription).style.display = 'none';
        } else {
            document.getElementById(idInput).style.display = 'none';
        }
        return false;
    }
</script>

<body style="background-color:rgba(48, 48, 48, 0.16)">
<body>
<div id="back">
<div id="addtask">
    <form id="add" action="/todolist/add" method="post">

        <div align="center">
        <p><big>Додати нове завдання </big></p>
        </div>

        <p>Описання завдання:<input id="ntask" name="newTask" type="text"></p>

        <p>Оберіть кінцеву дату виконання:<input id="date" type="date" name="calendar"></p>
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

            <td valign="middle" align="center" >
                <div id = 'description' onclick="change('test','description')"> <c:out value="${task.description}"> </c:out></div><br>
                <input style="display:none" id='test' type='text' onblur="update(test)" value="${task.description}">
            </td>
            <td valign="middle" align="center">
                <c:out value="${task.createdDate}"></c:out>
            </td>
            <td valign="middle" align="center">
                <c:out value="${task.deadline}"></c:out>
            </td>
            <td valign="middle" align="center">
                <form id="del" action="/todolist/del" method="post">
                    <input id='idTask' type="hidden" name="id for delete" value="${task.idTasks}">
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
