package trombino;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comment;
import model.User;

import org.apache.commons.lang.StringEscapeUtils;

import constants.Constants;
import dao.CommentDAO;
import dao.UserDAO;

/**
 * Servlet implementation class Add_Comment
 */
@WebServlet("/Add_Comment")
public class Add_Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add_Comment() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = null;
		User member = null;
		String com = null;
		// Check if a user is connected :
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("auth");
			return;
		}
		else{
			user = (User) request.getSession().getAttribute("user");
			com = request.getParameter("comment");
			
			UserDAO userDAO = new UserDAO();
			if(request.getParameter("member")==null){
				response.sendRedirect("profile?member="+user.getId());
				return;
			}
			else if(String.valueOf(user.getId()).equals(request.getParameter("member")) // un user ne peut pas se commenter lui même
					|| com == null 
					|| StringEscapeUtils.escapeSql(com).equals("")
					|| StringEscapeUtils.escapeSql(com).length()>Constants.COMMENT_MAX_LENGTH){
				response.sendRedirect("profile?member="+request.getParameter("member"));
				return;
			}
			else{
				try{
					member = userDAO.getById(Integer.parseInt(request.getParameter("member")));
				}catch(NumberFormatException e){
					response.sendRedirect("profile?member="+user.getId());
				}
				if(member==null){
					response.sendRedirect("profile?member="+request.getParameter("member"));
					return;
				}
				
			}
		}
		if(com.length()>150)
			com = com.substring(0, 149);
		CommentDAO commentDAO = new CommentDAO();
		Comment comment = new Comment();
		comment.setFromUser(user);
		comment.setToUser(member);
		comment.setText(com);
		comment.setIndex(commentDAO.getMemberCommentsCount(member));
		
		commentDAO.save(comment);
		
		response.sendRedirect("profile?member="+member.getId());
		
	}

}
