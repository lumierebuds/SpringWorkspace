<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>KH 자유게시판</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
	<link href="/study/resources/css/common/common.css" rel="stylesheet" />
</head>
<body>
	<c:set var="contextPath" value="<%= request.getContextPath() %>" scope="session"/>
    <header class="bg-blue text-white p-3">
        <div class="container d-flex justify-content-between align-items-center">
            <h1 class="h4" onclick="location.href='/study/list'">KH 자유게시판</h1>
            <div class="d-flex align-items-center">
                <img src="/study/resources/images/sudal.PNG" class="profile mr-2">
                <span>KH 수달</span>
            </div>
        </div>
    </header>
</body>
</html>
