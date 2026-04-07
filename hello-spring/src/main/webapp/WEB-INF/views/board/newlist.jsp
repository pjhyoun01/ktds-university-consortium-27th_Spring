<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/templates/header.jsp"/>

<h1>게시글 목록</h1>
<div class="flex">
	<div>총 ${searchCount}개의 게시글이 검색되었습니다.</div>
	
	<div class="btn-group">
		<div class="right-align">
		<c:if test="${not empty sessionScope.__LOGIN_DATA__}">
			<a href="/write">새로운 게시글 작성</a>
		</c:if>
			<a href="/member">회원 목록</a>
			
			
		</div>
	</div>
</div>
<ul class="grid articles">
<li class="header">
	<ul class="header-item">
	<li>번호</li>
	<li>제목</li>
	<li>이름</li>
	<li>조회수</li>
	<li>등록일</li>
	<li>수정일</li>
	</ul>
</li>
<c:choose>
	<c:when test="${not empty searchResult}">
	<%-- searchResult가 존재하면, 반복하여 데이터를 보여주고 --%>
	<li class="body">
		<c:forEach items="${searchResult}" var="board">
		<ul class="body-item">
			<li class="center">${board.id}</li>
			<li>
			<a href="/view/${board.id}">${board.subject}</a>
			</li>
			<li>${board.membersVO.name}</li>
			<li class="center">${board.viewCnt}</li>
			<li class="center">${board.crtDt}</li>
			<li class="center">${board.mdfyDt}</li>
		</ul>
		</c:forEach>
	</li>
	</c:when>
	<c:otherwise>
	<%-- searchResult가 존재하지 않으면, "검색된 데이터가 없습니다"를 보여주고 --%>
	<li class="footer">
		<ul class="footer-item">
		<li class="center">검색된 데이터가 없습니다.</li>
		</ul>
	</li>
	</c:otherwise>
</c:choose>
</ul>
<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
