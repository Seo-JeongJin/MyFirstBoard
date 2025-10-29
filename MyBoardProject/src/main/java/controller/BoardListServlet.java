package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDAO;
import model.PostDTO;

@WebServlet("/list")
public class BoardListServlet extends HttpServlet {
	
	private PostDAO postDAO;
	
	@Override
	public void init() throws ServletException {
		postDAO = new PostDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<PostDTO> posts = postDAO.getAllPosts();
		req.setAttribute("posts", posts);
		
		RequestDispatcher rd = req.getRequestDispatcher("list.jsp");
		rd.forward(req, resp);
		
	}
}
