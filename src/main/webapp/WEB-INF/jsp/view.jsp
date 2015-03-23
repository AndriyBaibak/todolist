<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>


<html>
<head>

    <title>TodoList</title>
    <style type="text/css">
        #addtask {
            margin-left: 25%;
            width: 50%;
        }
        .error {
            color: #ff0000;
        }
    </style>

</head>
<script type="text/javascript">
    function ValidFormFields(task, date) {
        if (document.getElementById(task).value == "") {
            document.getElementById(task).style.border = '2px inset red';
            return false;
        }
        if (document.getElementById(date).value == "") {
            document.getElementById(date).style.border = '2px inset red';
            return false;
        }
        return true;
    }
</script>
<script type="text/javascript">
    function update(textToSave, beReplaced, id, newDescription, newDeadline, type) {
        var newData = document.getElementById(textToSave).value;
        if (document.getElementById(textToSave).value == '') {
            newData = document.getElementById(beReplaced).innerHTML;
        }
        if(type=='description') {
            $.ajax({
                type: "POST",
                url: "/todolist/updateTask/" + id,
                data: {"description": newDescription.value, "deadline": newDeadline}

            });
        }else{
            $.ajax({
                type: "POST",
                url: "/todolist/updateTask/" + id,
                data: {"description": newDescription, "deadline": newDeadline.value}
            });
        }
        document.getElementById(textToSave).style.display = 'none';
        document.getElementById(beReplaced).style.display = '';
        document.getElementById(beReplaced).innerHTML = newData;
    }
</script>
<script type="text/javascript">
    function change(view, hide) {
        if (document.getElementById(view).style.display == 'none') {
            document.getElementById(view).style.display = '';
            document.getElementById(hide).style.display = 'none';
        } else {
            document.getElementById(view).style.display = 'none';
        }
        document.getElementById(view).focus();
        return false;
    }
</script>

<body style="background-color:rgba(48, 48, 48, 0.16)">
<body>
<div id="back">
    <div id="addtask">

            <div align="center">
                <p><big>Додати нове завдання </big></p>
            </div>
            <form:form action="/todolist/addTask" method="post" commandName="taskView" onsubmit="return ValidFormFields('ntask','date')">

                <tr>
                    <td>Описання:</td>
                    <td><form:input id="ntask" path="description" onfocus="this.style.border='2px inset'"/></td>
                    <td><form:errors path="description" cssClass="error" /></td></br>
                </tr>
                <tr>
                    <td>Оберіть кінцеву дату виконання:</td>
                    <td><form:input id="date" path="deadline" type="date" placeholder="yyyy-MM-dd" onfocus="this.style.border='2px inset'"/></td>
                    <td><form:errors path="deadline" cssClass="error" /></td></br>
                </tr>

            <div align="center">
                <input type="submit" value="Додати">
            </div>
            </form:form>
    </div>

    <table width="700" border="1"
           align="center">

        <caption><big>Усі завдання</big></caption>
        <tr>
            <th>Описання</th>
            <th>Дата створення</th>
            <th>Кінцева дата виконання</th>
        </tr>

        <c:forEach var="task" items="${tasks}">
            <form:form commandName="taskViewUpdate" method="post" action="/todolist/updateTask">
            <tr>
                <div>
                    <td valign="middle" align="center">
                        <div id="div + ${task.description} + ${task.id}"
                             onclick="change('input + ${task.description} + ${task.id}','div + ${task.description} + ${task.id}')">
                            <c:out value="${task.description}"> </c:out></div>
                        <form:input  cssStyle="display:none" path="description" id='input + ${task.description} + ${task.id}'
                               onblur="update('input + ${task.description} + ${task.id}','div + ${task.description} + ${task.id}','${task.id}',this, '${task.deadline}','description')" value="${task.description}"/></td>

                <td valign="middle" align="center">
                        <c:out value="${task.createdDate}"></c:out>
                    </td>
                    <td valign="middle" align="center">
                        <div id="div + ${task.deadline} + ${task.id}"
                             onclick="change('input + ${task.deadline} + ${task.id}','div + ${task.deadline} + ${task.id}')" >
                            <c:out value="${task.deadline}"> </c:out></div>
                        <form:input cssStyle="display:none" id="input + ${task.deadline} + ${task.id}" type="date"
                               onblur="update('input + ${task.deadline} + ${task.id}','div + ${task.deadline} + ${task.id}','${task.id}','${task.description}',this,'deadline')"
                               value="${task.deadline}" path="deadline"/>
                    </td>
            </form:form>
                    <td valign="middle" align="center">
                        <form id="del" action="/todolist/deleteTask/${task.id}" method="post">
                           <p><input type="submit" value="Видалити"></p>
                        </form>
                    </td>
                </div>
            </tr>
       </c:forEach>

    </table>
    <embed align="left" src="http://www.clocktag.com/cs/m51.swf" width="150" height="150" wmode="transparent"
           type="application/x-shockwave-flash"></embed>
    <embed align="right" src="http://www.clocktag.com/cs/t51.swf" width="200" height="110" wmode="transparent"
           type="application/x-shockwave-flash"></embed>
</div>
</body>
</body>
</html>
