<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<h2>로그인 화면 입니다.</h2>

    <!-- 로그인 실패 시 메시지 출력 -->
    <% 
        String msg = (String) request.getAttribute("msg");
        if (msg != null) { 
    %>
        <p style="color:red;"><%= msg %></p>
    <% 
        } 
    %>

    <!-- 로그인 폼 -->
    <form action="login" method="post">
        아이디 : <input type="text" name="userid"><br>
        비밀번호 : <input type="password" name="userpwd"><br>
        <input type="submit" value="로그인">
    </form>
    <p><a href="register.jsp">회원가입</a></p>
    
    
</body>
</html>