<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="util" uri="com.byka.util" %>
<html>
<head><title>Search result</title></head>
<body>

<table>
    <tr>
        <th>name</th>
        <th>addInfo</th>
        <th>link</th>
    </tr>
    <c:forEach items="${result}" var="i">
        <tr>
            <td>${i.name}</td>
            <td>${util:formatDate(i.date)}</td>
            <td><a href="${i.link}">link</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
