<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.PostDTO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 보기</title>
</head>
<body>
	<!-- 삭제 실패나 권한 없음 등의 메시지 표시 -->
    <%
        String msg = (String) request.getAttribute("msg");
        if (msg != null) {
    %>
        <p style="color:red;"><%= msg %></p>    
    <%
        }
    %>
    
    
    
	<%
        PostDTO post = (PostDTO) request.getAttribute("post");
        if (post == null) {
    %>
    
        <a href="list">목록으로</a>
    
    <%
        } else {
    %>
    
        <p>번호: <%= post.getId() %></p>
        <p>제목: <%= post.getTitle() %></p>
        <p>작성자: <%= post.getUserid() %></p>
        <p>작성일: <%= post.getCreatedAt() %></p>
        <hr>
        <p><%= post.getContent() %></p>
        <hr>
        <a href="edit?id=<%= post.getId() %>">수정</a> |
        <a href="delete?id=<%= post.getId() %>">삭제</a> |
        <a href="list">목록으로</a>
    
    <%
        }
    %>
</body>
</html>