<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board list</title>
</head>
<body>
    <h1>Board list</h1>
    <p>${boardCount}개의 게시물</p>
    <c:forEach var="board" items="${boardList}">
        <div>아이디: ${board.id}</div>
        <div>제목: ${board.subject}</div>
        <div>컨텐츠: ${board.content}</div>
        <div>이메일: ${board.email}</div>
        <div>조회수: ${board.viewCnt}</div>
        <div>작성일: ${board.crtDt}</div>
        <div>수정일: ${board.mdfyDt}</div>
        <div>파일명: ${board.fileName}</div>
        <div>확장명: ${board.originFileName}</div>
    </c:forEach>

    <a href="/board/write">게시글 작성</a>
</body>
</html>