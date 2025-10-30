package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserDAO;
import model.UserDTO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 한글 깨짐 처리
        req.setCharacterEncoding("UTF-8");

        // 파라미터 수집
        String userid = req.getParameter("userid");
        String userpwd = req.getParameter("userpwd");

        // 유효성 체크
        if (userid == null || userid.isBlank() || userpwd == null || userpwd.isBlank()) {
            req.setAttribute("msg", "아이디와 비밀번호를 모두 입력하세요.");
            RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
            rd.forward(req, resp);
            return;
        }

        // DAO 호출, login 메서드는 일치하는 사용자가 있으면 UserDTO 반환, 없으면 null 반환
        UserDTO user = userDAO.login(userid, userpwd);

        // 로그인 결과 처리
        if (user != null) {
            // 로그인 성공시 세션에 필요한 정보 저장
            HttpSession s = req.getSession();
            s.setAttribute("userid", user.getUserid());   // 로그인 ID
            s.setAttribute("username", user.getUsername()); // 표시용 이름

            // 로그인 후 목록 페이지로 리다이렉트
            resp.sendRedirect("list");
            
        } else {
            // 로그인 실패시 메시지를 request 에 담아 로그인 페이지로 포워드
            req.setAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
            rd.forward(req, resp);
        }
	}
}
