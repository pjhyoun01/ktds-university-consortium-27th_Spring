<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

	<jsp:include page="/WEB-INF/views/common/header.jsp">
		<jsp:param value="배우 목록" name="title"/>
		<jsp:param value="list" name="css"/>
	</jsp:include>

	<div class="content">
		<h1>배우 목록</h1>
		<div class="form-container">
			<c:forEach items="${actorList}" var="actor">
				<div>
					<div>${actor.actorId}</div>
					<div>${actor.actorName}</div>
					<div>${actor.actorProfileUrl}</div>
				</div>
			</c:forEach>
		</div>
	</div>

</body>
</html>