<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 28.11.2014
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        .center_b {
            /* Ширина необходимого блока */
            width: 250px;
            /* Высота необходимого блока */
            height: 250px;
            /* Абсолютное позиционирование блока */
            position: absolute;
            /* Отрицательный отступ от верхнего края страницы, это половина ширины блока со знаком минус */
            margin-top: -125px;
            /* Отрицательный отступ от левого края страницы, это половина высоты блока со знаком минус */
            margin-left: -125px;
            /* Отступ в процентах от верхнего края экрана */
            top: 50%;
            /* Отступ в процентах от левого края экрана */
            left: 50%;
        }
    </style>
</head>
<body>
<div class="center_b">

    Завдання видалено!
    <p><img src="picture/delete.jpg"/></p>

    <p>
        <a href="/TodoList-1.0">Повернутися на головну</a>
    </p>
</div>

</body>
</html>
