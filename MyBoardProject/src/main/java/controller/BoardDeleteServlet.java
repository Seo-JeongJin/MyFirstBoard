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

@WebServlet("/delete")
public class BoardDeleteServlet extends HttpServlet {
	
	private PostDAO postDAO;
	
	@Override
	public void init() throws ServletException {
		postDAO = new PostDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		HttpSession s = req.getSession();
		String userId = (String) s.getAttribute("userid");
		
		String msg = postDAO.deletePost(id, userId);
		
		if (msg.equals("게시글 삭제")) {
			resp.sendRedirect("list");
		} else {
			req.setAttribute("msg", msg);
			req.setAttribute("post", postDAO.getPostById(id));
			RequestDispatcher rd = req.getRequestDispatcher("view.jsp");
			rd.forward(req, resp);
		}
		
	}
}
