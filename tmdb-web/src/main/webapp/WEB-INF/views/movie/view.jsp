<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${movie.title} (${movie.openYear}) - The Movie Database (TMDB)</title>
<link type="text/css" rel="stylesheet" href="/css/common.css">
<link type="text/css" rel="stylesheet" href="/css/movie/view.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
    <div class="header__sub">
        <div>개요</div>
        <div>미디어</div>
        <div>팬덤</div>
        <div>공유</div>
    </div>
    <div class="content">
        <div class="content__header">
            <div class="content__header__left">
                <img src="${movie.posterUrl}" alt="${movie.title} 이미지">
            </div>
            <div class="content__header__right">
                <div>
                    <div>
                        <div class="movie__title">${movie.title}</div>
                        <div class="movie__openyear">(${movie.openYear})</div>
                    </div>
                    <div>
                        <div class="movie__rate">${movie.movieRating}</div>
                        <div class="movie__opendate">${movie.openDate}
                            <span>(${movie.openCountry})</span>
                        </div>
                        <span>·</span>
                        <div class="movie__genre">장르, 장르, 장르</div>
                        <span>·</span>
                        <div class="movie__runningtime">${movie.runningTime}</div>
                    </div>
                </div>
                <div>
                    <div>
                        <div class="movie__score">회원 점수</div>
                        <div class="movie__yourvibe">당신의 바이브는 어떤가요?</div>
                    </div>
                    <div>
                        <div>
                        </div>
                        <div class="movie__trailer">트레일러 재생</div>
                    </div>
                </div>
                <div>
                    <div class="movie__introduce">${movie.introduce}</div>
                    <div class="synopsis">개요</div>
                    <div class="movie__synopsis">${movie.synopsis}</div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer"></div>
</body>
</html>