<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board Write</title>
    <link type="text/css" rel="stylesheet" href="/css/write.css">
    <script type="text/javascript" src="/js/jquery-4.0.0.slim.min.js"></script>
    <script type="text/javascript" src="/js/write.js"></script>
</head>
<body>
    <h1>게시글 작성</h1>
    <div>
        <form action="/board/write" method="post">
            <div>
                제목
                <input type="text" name="subject">
            </div>
            <div>
                내용
                <textarea name="content" ></textarea>
            </div>
            <div>
                이메일
                <input type="text" name="email">
            </div>

            <button type="submit">저장</button>
            <button type="reset">다시 쓰기</button>
        </form>
    </div>
</body>
</html>