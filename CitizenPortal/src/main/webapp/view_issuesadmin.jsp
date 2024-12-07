<%@ page import="java.util.List" %>
<%@ page import="com.example.servlets.Issue" %>


<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>View Issues</title>
</head>
<body>
    <h2>Reported Issues</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Issue Description</th>
            <th>Politician ID</th>
        </tr>
        <c:forEach var="issue" items="${issueList}">
            <tr>
                <td>${issue.id}</td>
                <td>${issue.username}</td>
                <td>${issue.issueDescription}</td>
                <td>${issue.politicianId}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
