<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

	<jsp:include page="/WEB-INF/views/common/header.jsp">
		<jsp:param value="제작진 등록" name="title"/>
		<jsp:param value="insert" name="css"/>
	</jsp:include>

	<div class="content">
		<div class="form-container">
			<h1>제작진 등록</h1>
			<form:form modelAttribute="insertVO" action="/producer/insert" method="post" enctype="multipart/form-data">
				<div>
					<div>
						<label for="producerName">이름</label>
					</div>
					<input id="producerName" type="text" name="producerName" placeholder="제작진의 이름을 입력해주세요.">
				</div>
				<div>
					<div>
						<label for="file">프로필 사진</label>
					</div>
					<input id="file" type="file" name="file" />
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