<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://www.springframework.org/tags/form" %>

<html>
<head><title>Home</title></head>
<body>
<x:form action="search" method="post">
    <input type="text" name="searchTerm"/>
    <input type="submit" value="search"/>
</x:form>
</body>
</html>