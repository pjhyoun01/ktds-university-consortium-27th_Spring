<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board list</title>
<link rel="stylesheet" href="/css/list.css">
</head>
<body>
    <h1>Board list</h1>
    <div class="menu">
        <p>${boardCount}개의 게시물</p>
        <div>
            <a href="/write">게시글 작성</a>
            <a href="/member/view">회원 목록</a>
        </div>
    </div>
    <table>
        <tr>
            <th>아이디</th>
            <th>제목</th>
            <th>컨텐츠</th>
            <th>이메일</th>
            <th>조회수</th>
            <th>작성일</th>
            <th>수정일</th>
            <th>파일명</th>
            <th>확장명</th>
        </tr>

        <c:choose>
            <c:when test="${not empty boardList}">
                <c:forEach var="board" items="${boardList}">
                    <tr>
                        <td>
                            <a href="/view/${board.id}">${board.id}</a>
                        </td>
                        <td>${board.subject}</td>
                        <td>${board.content}</td>
                        <td>${board.email}</td>
                        <td>${board.viewCnt}</td>
                        <td>${board.crtDt}</td>
                        <td>${board.mdfyDt}</td>
                        <td>${board.fileName}</td>
                        <td>${board.originFileName}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <td colspan="9">게시글이 없습니다</td>
            </c:otherwise>
        </c:choose>
        
    </table>
</body>
</html>