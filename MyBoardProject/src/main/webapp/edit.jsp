<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.PostDTO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
	<%
        PostDTO post = (PostDTO) request.getAttribute("post");
    %>

    <h2>게시글 수정</h2>

    <form action="edit" method="post">
        <input type="hidden" name="id" value="<%= post.getId() %>">

        <input type="text" name="title" value="<%= post.getTitle() %>" required><br><br>
        <textarea name="content" rows="10" cols="50" required><%= post.getContent() %></textarea><br><br>

        <input type="submit" value="수정 완료">
    </form>

    <a href="view?id=<%= post.getId() %>">돌아가기</a>

    <% 
        String msg = (String) request.getAttribute("msg");
        if (msg != null) { 
    %>
    
        <p><%= msg %></p>
        
    <% } %>
</body>
</html>