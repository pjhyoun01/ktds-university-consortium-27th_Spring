<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>게시물 조회</h1>
    <div>
        <p>아이디: ${board.id}</p>
    </div>
    <div>
        <p>제목  : ${board.subject}</p>
    </div>
    <div>
        <p>컨텐츠: ${board.content}</p>
    </div>
    <div>
        <p>이메일: ${board.email}</p>
    </div>
    <div>
        <p>조회수: ${board.viewCnt}</p>
    </div>
    <div>
        <p>작성일: ${board.crtDt}</p>
    </div>
    <div>
        <p>수정일: ${board.mdfyDt}</p>
    </div>
    <div>
        <p>파일명: ${board.fileName}</p>
    </div>
    <div>
        <p>확장명: ${board.originFileName}</p>
    </div>
</body>
</html>