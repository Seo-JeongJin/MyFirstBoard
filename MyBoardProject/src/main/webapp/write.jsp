<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성</title>
</head>
<body>
	<h2>게시글 작성</h2>

    <%
        String msg = (String) request.getAttribute("msg");
        if (msg != null) {
    %>
    
        <p style="color:red;"><%= msg %></p>
        
    <%
        }
    %>

    <form action="write" method="post">
        <input type="text" name="title" placeholder="제목" required><br><br>
        <textarea name="content" rows="10" cols="50" placeholder="내용" required></textarea><br><br>
        <input type="submit" value="작성">
    </form>

    <a href="list">목록으로</a>
    
</body>
</html>