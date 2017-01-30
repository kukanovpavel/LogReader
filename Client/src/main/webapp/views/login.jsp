<%--
  Created by IntelliJ IDEA.
  User: Pkukanov
  Date: 16.12.2016
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Security WebApp login page</title>
</head>
<body bgcolor="#cccccc">
<div align="center">
<blockquote>
    <h2>Please enter your user name and password:</h2>
    <p>
    <form method="POST" action="j_security_check">
        <table border=1>
            <tr>
                <td>Username:</td>
                <td><input type="text" name="j_username"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="j_password"></td>
            </tr>
            <tr>
                <td colspan=2 align=right><input type=submit
                                                 value="Submit"></td>
            </tr>
        </table>
    </form>
</blockquote>
</div>
</body>
</html>