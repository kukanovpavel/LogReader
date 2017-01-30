<%@ page import="java.sql.SQLException" %>
<%@ page import="ru.kukanov.utils.ReadConfig" %><%--
  Created by IntelliJ IDEA.
  User: Pkukanov
  Date: 13.12.2016
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Page</title>
    <style type="text/css">
        .leftDiv{
            float: left;
            border-style: ridge;
        }
        .rightDiv{
            float: right;
            border-style: ridge;
        }
        .divTable{
            border-style: ridge;
            white-space: pre-wrap;
            word-break: break-all;
            word-wrap: break-word;
            display: table;
            width: 100%;
        }
        .divTableRow {
            display: table-row;
        }
        .divTableHeading {
            background-color: #EEE;
            display: table-header-group;
        }
        .divTableCell, .divTableHead {
            border: 1px solid #999999;
            display: table-cell;
            padding: 3px 10px;
        }
        .divTableHeading {
            background-color: #EEE;
            display: table-header-group;
            font-weight: bold;
        }
        .divTableFoot {
            background-color: #EEE;
            display: table-footer-group;
            font-weight: bold;
        }
        .divTableBody {
            display: table-row-group;
        }
    </style>

</head>
<%
    try {
        ru.kukanov.db.JavaToMySQL.insertRecordIntoTable(request.getRemoteUser(), "login");
    } catch (SQLException e) {
        e.printStackTrace();
        String s = new ReadConfig().getPropValues();
    }
%>
<body id="body">

<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="<c:url value="/resources/js/ajax.js" />"></script>
<script src="<c:url value="/resources/js/js.js" />"></script>
<script src="<c:url value="/resources/js/jscolor.js" />"></script>


<div class="mainDiv">

    <div class="leftDiv" align="right" >
        <form  action="search" id="testForm" method="post" >
            inText:
            <input   type="text" name="inText"/><br><br>
            serverName:
            <input  type="text" name="serverName"/><br><br>

            Date: from
            <input  type="text" name="fromDate"/>
            to
            <input  type="text" name="toDate"/>
            <input type="button" id="addDate" value="addDate"><br><br>
            <div id="date">
            </div>
            isFile:
            <input  type="checkbox" name="isFile"/>
            Format:
            <select name="fileFormat">
                <option value="log">log</option>
                <option value="xml">xml</option>
                <option value="html">html</option>
                <option value="rtf">rtf</option>
                <option value="doc">doc</option>
                <option value="pdf">pdf</option>
            </select>
            <br><br>

            <%--<input type="button" id="submit" value="Send">--%>
            <input type="submit" id="submit" value="Send">
            <input type="button" id="clear" value="Clear">
        </form>

    </div>


    <div class="rightDiv" align="right">
        <p> <%= request.getRemoteUser() %></p>
        <form action="logout.jsp">
            <input type="submit" value="logout" />
        </form>
        <label>
            <input class="jscolor" onchange="update(this.jscolor)" value="FFFFFF">
        </label>
    </div>

</div>
<br><br>



<div class="divTable">
    <div class="divTableBody">
        <div  class="divTableRow">
            <div id="result" class = "divTableCell"></div>
            <c:forEach var="list" items="${list}">
                <div><c:out value="${list.date}"/>
                    <c:out value="${list.thread}"/>

                    <td><c:out value="${list.text}"/></div><br><br>

            </c:forEach>
        </div>
    </div>
</div>
<!-- DivTable.com -->


</body>
</html>