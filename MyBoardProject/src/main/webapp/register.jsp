<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	 <h2>회원가입 페이지 입니다.</h2>

    <%
        String msg = (String) request.getAttribute("msg");
        if (msg != null) {
    %>
        <p style="color:red;"><%= msg %></p>
    <%
        }
    %>

    <form action="register" method="post">
        아이디 : <input type="text" name="userid"><br>
        비밀번호 : <input type="password" name="userpwd"><br>
        이름 : <input type="text" name="username"><br>
        <input type="submit" value="회원가입">
    </form>
    <p><a href="login.jsp">로그인 페이지로 돌아가기</a></p>
</body>
</html>