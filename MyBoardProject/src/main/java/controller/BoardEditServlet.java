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

@WebServlet("/edit")
public class BoardEditServlet extends HttpServlet {
	
	private PostDAO postDAO;
	
	@Override
	public void init() throws ServletException {
		postDAO = new PostDAO();
	}
	
	// 수정 폼 불러오기
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		HttpSession s = req.getSession();
		String userId = (String) s.getAttribute("userid");
		
		PostDTO post = postDAO.getPostById(id);
		
		// 작성자 아이디와 세션에 저장된 로그인 아이디가 동일하지 않을 때
		if (!post.getUserid().equals(userId)) {
			req.setAttribute("msg", "권한이 없습니다.");
			RequestDispatcher rd = req.getRequestDispatcher("view.jsp");
			rd.forward(req, resp);
			return; // 포워딩 후 메서드 종료
		} 
		
		req.setAttribute("post", post);
		RequestDispatcher rd = req.getRequestDispatcher("edit.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		int id = Integer.parseInt(req.getParameter("id"));
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		HttpSession s = req.getSession();
		String userId = (String) s.getAttribute("userid");
		
		PostDTO post = new PostDTO();
		post.setId(id);
		post.setTitle(title);
		post.setContent(content);
		post.setUserid(userId);
		
		String msg = postDAO.updatePost(post);
		
		if (msg.equals("게시글 수정 완료")) {
			resp.sendRedirect("view?id="+id);
		} else {
			req.setAttribute("msg", msg);
			req.setAttribute("post", postDAO.getPostById(id));
			RequestDispatcher rd = req.getRequestDispatcher("edit.jsp");
			rd.forward(req, resp);
		}
	}
}
