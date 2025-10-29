package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDAO;
import model.PostDTO;

@WebServlet("/view")
public class BoardDetailServlet extends HttpServlet {
	
	private PostDAO postDAO;
	
	@Override
	public void init() throws ServletException {
		postDAO = new PostDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		PostDTO post = postDAO.getPostById(id);
		req.setAttribute("post", post);
		
		RequestDispatcher rd = req.getRequestDispatcher("view.jsp");
		rd.forward(req, resp);
		
	}
}
