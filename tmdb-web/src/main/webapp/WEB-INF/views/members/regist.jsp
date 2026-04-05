<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원 가입</title>
	<link type="text/css" rel="stylesheet" href="/css/common.css">
	<link type="text/css" rel="stylesheet" href="/css/members/regist.css">
	<script type="text/javascript" src="/js/jquery-4.0.0.slim.min.js"></script>
	<script type="text/javascript" src="/js/members/regist.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="content">
		<div class="form-container">
			<h1>회원 등록</h1>
			<form:form modelAttribute="registVO" action="/members/regist" method="post">
				<div>
					<div>
						<label for="email">이메일</label>
						<form:errors path="email" cssClass="valid__error" element="span" />
					</div>
					<input id="email" type="text" name="email" placeholder="이메일을 입력해주세요.">
				</div>
				<div>
					<div>
						<label for="name">이름</label>
						<form:errors path="name" cssClass="valid__error" element="span" />
					</div>
					<input id="name" type="text" name="name" placeholder="이름을 입력해주세요.">
				</div>
				<div>
					<div>
						<label for="password">비밀번호</label>
						<form:errors path="password" cssClass="valid__error" element="span" />
					</div>
					<div class="input--password">
						<input id="password" type="password" name="password" placeholder="비밀번호를 입력해주세요">
						<label for="view-password">보기</label>
						<input id="view-password" type="checkbox">
					</div>
				</div>
				<div>
					<div>
						<label for="confirm-password">확인 비밀번호</label>
						<form:errors path="confirmPassword" cssClass="valid__error" element="span" />
					</div>
					<div class="input--password">
						<input id="confirm-password" type="password" name="confirmPassword" placeholder="동일한 비밀번호를 입력해주세요">
						<label for="view-confirm-password">보기</label>
						<input id="view-confirm-password" type="checkbox">
					</div>
				</div>
				<div>
				</div>
				<div class="button-group">
					<button type="submit">등록</button>
					<a href="/" class="btn-cancel">
						취소
					</a>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>