<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.PostDTO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<body>
	  <h2>게시글 목록</h2>

    <%
        List<PostDTO> posts = (List<PostDTO>) request.getAttribute("posts");
        if (posts == null || posts.isEmpty()) {
    %>
        <p>등록된 게시글이 없습니다.</p>
    <%
        } else {
    %>
    
        <table>
        
            <tr>
                <th>게시글 번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
            
            <%
                for (PostDTO post : posts) {
            %>
            
	        <tr>
	        	<td><%= post.getId() %></td>
	            <td><a href="view?id=<%= post.getId() %>"><%= post.getTitle() %></a></td>
	            <td><%= post.getUserid() %></td>
	            <td><%= post.getCreatedAt() %></td>
	        </tr>
	            
    <%
        }
    %>
            
        </table>
        
    <%
        }
    %>

	<p><a href="write.jsp">글쓰기</a></p>
	
</body>
</html>