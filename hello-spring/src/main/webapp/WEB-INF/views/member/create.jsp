<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Sign up</title>
    <link rel="stylesheet" href="/css/member/create.css">
</head>
<body>
    <h1>회원가입</h1>
    <form action="/member/create" method="post">

        <div class="grid">
            <label>이메일</label>
            <input type="text" name="email">
            
            <label>이름</label>
            <input type="text" name="name">
            
            <label>비밀번호</label>
            <input type="text" name="password">
            
            <button type="submit">회원가입</button>
        </div>
    </form>
</body>
</html>