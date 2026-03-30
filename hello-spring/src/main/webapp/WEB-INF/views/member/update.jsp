<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="/css/member/create.css">
</head>
<body>
    <h1>회원 정보 수정</h1>
    <form action="/member/update/${member.email}" method="post">
        <div class="grid">
            <label>이름</label>
            <input type="text" name="name" value="${member.name}">
            
            <label>비밀번호</label>
            <input type="text" name="password" value="${member.password}">
            
            <button type="submit">수정</button>]
        </div>
    </form>
</body>
</html>