package trombino;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comment;
import model.User;
import dao.CommentDAO;

/**
 * Servlet implementation class Add_Comment
 */
@WebServlet("/Move_Remove_Comment")
public class Move_Remove_Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Move_Remove_Comment() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		User toUser = null;
		// Check if a user is connected :
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("auth");
			return;
		}
		else{
			String action = request.getParameter("action");
			int id;
			try{
				id = Integer.parseInt(request.getParameter("id"));
			}catch(NumberFormatException e){
				id=0;
			}
			System.out.println(id);
			System.out.println(action);
			if(id!=0 && action!=null){
				user = (User) request.getSession().getAttribute("user");
				CommentDAO commentDAO = new CommentDAO();
				Comment comment  = commentDAO.getById(id, true, true);
				toUser = comment.getToUser();
				System.out.println(toUser);
				if(user.isAdmin()){
					List<Comment> commentList = commentDAO.getComments(toUser);
					if(comment!=null && action.equals("up") && comment.getIndex()>0){
						commentList.remove(comment.getIndex());
						comment.setIndex(comment.getIndex()-1);
						commentList.add(comment.getIndex(), comment);
						commentDAO.updateIndexes(commentList);
					}
					if(comment!=null && action.equals("down") && comment.getIndex()<commentDAO.getMemberCommentsCount(toUser)){
						commentList.remove(comment.getIndex());
						comment.setIndex(comment.getIndex()+1);
						commentList.add(comment.getIndex(), comment);
						commentDAO.updateIndexes(commentList);
					}
				}
				if(user.getId()== comment.getFromUser().getId() || user.isAdmin())
					if(comment!=null && action.equals("remove"))
						commentDAO.delete(comment);
			}
			
		}
		
		response.sendRedirect("profile?member="+toUser.getId());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
