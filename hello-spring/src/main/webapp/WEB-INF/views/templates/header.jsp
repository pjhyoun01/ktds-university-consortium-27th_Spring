<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>${param.title}</title>
	<link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
	<c:if test="${not empty param.script}">
		<script type="text/javascript" src="/js/jquery-4.0.0.slim.min.js"></script>
		<script type="text/javascript" src="${param.script}"></script>
	</c:if>
</head>
<body>
	<div class="grid list">
		<div class="aling--left">
			<c:choose>
				<c:when test="${empty sessionScope.__LOGIN_DATA__}">
					<a class="margin--right" href="/regist">회원가입</a>
					<a href="/login">로그인</a>
				</c:when>
				<c:otherwise>
					<span class="margin--right">${sessionScope.__LOGIN_DATA__.name}(${sessionScope.__LOGIN_DATA__.email})</span>
					<a class="margin--right" href="/member/view/${sessionScope.__LOGIN_DATA__.email}">내정보</a>
					<a href="/logout">로그아웃</a>
				</c:otherwise>
			</c:choose>
		</div>