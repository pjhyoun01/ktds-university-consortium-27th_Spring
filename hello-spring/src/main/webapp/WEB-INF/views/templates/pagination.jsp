<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="search-box">
    <select id="list-size">
        <option value="10" ${pagination.listSize eq "10" ? "selected": ""}>10개씩</option>
        <option value="20" ${pagination.listSize eq "20" ? "selected": ""}>20개씩</option>
        <option value="50" ${pagination.listSize eq "50" ? "selected": ""}>50개씩</option>
        <option value="100" ${pagination.listSize eq "100" ? "selected": ""}>100개씩</option>
    </select>
    <div>
        <select id="search-type">
            <option value="email" ${pagination.searchType eq "email" ? "selected" : ""}>
                Email로 검색</option>
            <option value="name" ${pagination.searchType eq "name" ? "selected" : ""}>
                작성자 이름으로 검색</option>
            <option value="subject" ${pagination.searchType eq "subject" ? "selected" : ""}>
                제목으로 검색</option>
            <option value="content" ${pagination.searchType eq "content" ? "selected" : ""}>
                내용으로 검색</option>
            <option value="subject+content"
            ${pagination.searchType eq "subject content" ? "selected" : ""}>
                제목 + 내용으로 검색</option>
        </select>
        <input type="text" id="search-keyword" placeholder="검색어를 입력하세요."
               value="${pagination.searchKeyword}" />
        <button type="button" class="search-button">검색!</button>
    </div>
</div>

<c:if test="${pagination.pageCount > 0}">
    <ul class="page-navigator">
        <c:if test="${pagination.hasPrevPageGroup}">
            <li>
                <a data-page-no="0" href="javascript:void(-1);">처음</a>
            </li>
            <li>
                <a data-page-no="${pagination.prevPageGroupStartPageNo}" href="javascript:void(-1);">이전</a>
            </li>
        </c:if>
        <c:forEach begin="${pagination.groupStartPageNo}"
                   end="${pagination.groupEndPageNo}"
                   step="1"
                   var="page">
            <li class="${page eq pagination.pageNo ? 'active': ''}">
                <a data-page-no="${page}" href="javascript:void(-1);">${page + 1}</a>
            </li>
        </c:forEach>
        <c:if test="${pagination.hasNextPageGroup}">
            <li>
                <a data-page-no="${pagination.nextPageGroupStartPageNo}" href="javascript:void(-1);">다음</a>
            </li>
            <li>
                <a data-page-no="${pagination.pageCount - 1}" href="javascript:void(-1);">마지막</a>
            </li>
        </c:if>
    </ul>
</c:if>