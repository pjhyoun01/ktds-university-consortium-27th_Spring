<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>영화 등록</title>
	<link type="text/css" rel="stylesheet" href="/css/common.css">
	<c:if test="${not empty param.css}">
		<link type="text/css" rel="stylesheet" href="/css/${param.css}.css">
	</c:if>
	<c:if test="${not empty param.script}">
		<script type="text/javascript" src="/js/jquery-4.0.0.slim.min.js"></script>
		<script type="text/javascript" src="/js/${param.script}.js"></script>
	</c:if>
</head>
<body>
	<div class="header">
		<div class="header--witdh">
			<div class="header__left">
				<a href="/">
					<img class="left--logo" src="/images/logo.svg" alt="logo 이미지">
				</a>
				<div class="left__nav">
					<a href="/">
						<div>영화</div>
					</a>
					<div>TV 프로그램</div>
					<div>인물</div>
					<div>Awards</div>
					<div>More</div>
				</div>
			</div>
			<div class="header__right">
				<a href="/movie/insert">영화 등록</a>
				<a href="/login">로그인</a>
				<a href="/member/regist">회원가입</a>
				<img class="search--img" src="/images/search.svg" alt="검색 이미지">
			</div>
		</div>
	</div>