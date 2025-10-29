package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.PostDAO;
import model.PostDTO;

@WebServlet("/write")
public class BoardWrtieServlet extends HttpServlet {
	
	private PostDAO postDAO;
	
	@Override
	public void init() throws ServletException {
		postDAO = new PostDAO();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		HttpSession s = req.getSession();
		String userId = (String) s.getAttribute("userid");
		String userName = (String) s.getAttribute("username");
		
		if (userId == null) {
			req.setAttribute("msg", "로그인 후 작성 가능합니다.");
			RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
			rd.forward(req, resp);
			return; // 포워딩 후 메서드 종료
		}
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		PostDTO post = new PostDTO();
		post.setTitle(title);
		post.setContent(content);
		post.setUserid(userId);
		post.setAuthor(userName);
		
		boolean success = postDAO.insertPost(post);
		
		if (success) {
			resp.sendRedirect("list");
		} else {
			req.setAttribute("msg", "글 작성 실패. 다시 시도해주세요.");
			RequestDispatcher rd = req.getRequestDispatcher("write.jsp");
			rd.forward(req, resp);
		}
	}
}
