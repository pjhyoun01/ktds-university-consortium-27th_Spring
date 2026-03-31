<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 조회: 게시글 아이디</title>
    <link rel="stylesheet" type="text/css" href="/css/hello-spring.css" />
  </head>
  <body>
    <h1>게시글 내용 조회</h1>
    <div class="grid view">
      <span>아이디</span>
      <div>${board.id}</div>

      <span>제목</span>
      <div>${board.subject}</div>

      <span>이메일</span>
      <div>${board.email}</div>

      <span>조회수</span>
      <div>${board.viewCnt}</div>

      <span>작성일</span>
      <div>${board.crtDt}</div>

      <span>마지막 수정일</span>
      <div>${board.mdfyDt}</div>

      <span>첨부파일</span>
      <div>
        <ul class="vertical-list">
          <c:forEach items="${board.files}" var="file">
            <li>
              <a href="/file/${file.fileGroupId}/${file.fileNum}">
                ${file.displayName}
              </a>
            </li>
          </c:forEach>

        </ul>
      </div>

      <span>내용</span>
      <%-- <pre > ==> Presentation --%>
      <pre>${board.content}</pre>

      <div class="btn-group">
        <div class="right-align flex">
          <a href="/update/${board.id}">수정</a>
          <form action="/delete/${board.id}" method="post">
            <button type="submit">삭제</button>
          </form>
        </div>
      </div>
    </div>
  </body>
</html>
