<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>회원 상세 조회</title>
    <link rel="stylesheet" href="/css/member/view.css">
</head>
<body>
    <h1>회원 상세 조회</h1>
    <div>
        <div>
            <div>이메일: </div>
            <div>이름: </div>
            <div>비밀번호</div>
        </div>
        <div>
            <div>${member.email}</div>
            <div>${member.name}</div>
            <div>${member.password}</div>
        </div>
    </div>
    <div>
        <a href="/member/update/${member.email}">
            <button>수정</button>
        </a>
        <form action="/member/delete/${member.email}" method="post">
            <button type="submit">삭제</button>]
        </form>
    </div>
</body>
</html>