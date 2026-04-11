<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

	<jsp:include page="/WEB-INF/views/common/header.jsp">
		<jsp:param value="제작진 목록" name="title"/>
		<jsp:param value="list" name="css"/>
	</jsp:include>

	<div class="content">
		<h1>제작진 목록</h1>
		<div class="form-container">
			<c:forEach items="${producerList}" var="producer">
				<div>
					<img alt="${producer.producerName}.img" src="/file/${producer.fileGroupId}">
					<div>${producer.producerName}</div>
					<div>${producer.producerId}</div>
				</div>
			</c:forEach>
		</div>
	</div>

</body>
</html>