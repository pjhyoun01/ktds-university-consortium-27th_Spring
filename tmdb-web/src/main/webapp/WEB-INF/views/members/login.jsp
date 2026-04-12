<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h1>로그인페이지</h1>
	<form:form modelAttribute="loginVO" action="/login" method="post" >
		<label for="email">이메일</label>
		<input type="text" name="email" id="email"/>
		<label for="password">비밀번호</label>
		<input type="password" name="password" id="password"/>
		
		<button type="submit"></button>
		
	</form:form>
</body>
</html>