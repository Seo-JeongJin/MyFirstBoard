package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserDAO;
import model.UserDTO;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String userid = req.getParameter("userid");
		String userpwd = req.getParameter("userpwd");
		String username = req.getParameter("username");
		
		UserDTO user = new UserDTO();
		user.setUserid(userid);
		user.setUserpwd(userpwd);
		user.setUsername(username);
		
		boolean success = userDAO.register(user);
		
		if (success) {
			resp.sendRedirect("login.jsp");
		} else {
			req.setAttribute("msg", "회원가입 실패. 다시 시도 해주세요.");
			RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
			rd.forward(req, resp);
		}
	}
}
