<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>회원 목록</title>
    <link rel="stylesheet" href="/css/list.css">
</head>
<body>
    <h1>회원 목록</h1>
    <div class="menu">
        <p>${boardCount}개의 게시물</p>
        <div>
            <a href="/member/create">회원 등록</a>
            <a href="/view">게시물 목록</a>
        </div>
    </div>
    <table>
        <tr>
            <th>이메일</th>
            <th>이름</th>
            <th>비밀번호</th>
        </tr>
        <c:choose>
            <c:when test="${not empty memberList}">
                <c:forEach var="member" items="${memberList}">
                    <tr>
                        <td>
                            <a href="/member/view/${member.email}">${member.email}</a>
                        </td>
                        <td>${member.name}</td>
                        <td>${member.password}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <td colspan="3">회원이 없습니다</td>
            </c:otherwise>
        </c:choose>


    </table>
</body>
</html>