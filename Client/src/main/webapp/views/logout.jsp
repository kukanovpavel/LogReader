<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: Pkukanov
  Date: 16.12.2016
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta http-equiv="refresh" content="0;URL=login.jsp" />
    <title></title>
    <p>Bye!</p>
    <%
        try {
            ru.kukanov.db.JavaToMySQL.insertRecordIntoTable(request.getRemoteUser(), "logout");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.logout();
    %>

    <script>window.location.replace("login.jsp")</script>
</head>
<body>

</body>
</html>
