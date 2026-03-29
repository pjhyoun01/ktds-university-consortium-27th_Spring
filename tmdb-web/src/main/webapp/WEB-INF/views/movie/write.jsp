<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 등록</title>
<link type="text/css" rel="stylesheet" href="/css/common.css">
<link type="text/css" rel="stylesheet" href="/css/movie/write.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <div class="content">
        <div class="form-container">
    <h1>영화 등록</h1>
    <form action="/write" method="post">
        <div>
            <label>제목</label>
            <input type="text" placeholder="제목을 입력해 주세요" name="title">
        </div>
        <div>
            <label>원제목</label>
            <input type="text" placeholder="원제목을 입력해 주세요" name="orignalTitle">
        </div>
        <div>
            <label>포스터 경로</label>
            <input type="text" placeholder="포스터 경로를 입력해 주세요" name="posterUrl">
        </div>
        <div>
            <label>관람가</label>
            <input type="text" placeholder="all, 12, 15, 19" name="movieRating">
        </div>
        <div>
            <label>개봉일</label>
            <input type="text" placeholder="YYYY-MM-DD" name="openDate">
        </div>
        <div>
            <label>출시 국가</label>
            <input type="text" placeholder="출시 국가를 입력해 주세요" name="openCountry">
        </div>
        <div>
            <label>상영시간</label>
            <input type="text" placeholder="120분 -> 120" name="runningTime">
        </div>
        <div>
            <label>언어</label>
            <input type="text" placeholder="언어를 입력해 주세요" name="language">
        </div>
        <div>
            <label>제작비</label>
            <input type="text" placeholder="제작비를 입력해 주세요" name="budget">
        </div>
        <div>
            <label>수익</label>
            <input type="text" placeholder="수익을 입력해 주세요" name="profit">
        </div>
        <div style="grid-column: span 2;">
            <label>개요</label>
            <textarea type="text" placeholder="개요를 입력해 주세요" name="introduce"></textarea>
        </div>
        <div style="grid-column: span 2;">
            <label>시놉시스</label>
            <textarea type="text" placeholder="시놉시스를 입력해 주세요" name="synopsis"></textarea>
        </div>

        <div class="button-group">
            <button type="submit">등록</button>
            <a href="/movie/list" class="btn-cancel">취소</a>
        </div>
    </form>
</div>
    </div>
</body>
</html>