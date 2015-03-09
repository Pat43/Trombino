package trombino;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comment;
import model.User;
import model.UserAsso;
import util.AppConfig;
import dao.CommentDAO;
import dao.UserAssoDAO;
import dao.UserDAO;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
       
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	/**
	 * Function called either the form is sent by Get or Post method.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Java initialisations :
		request.setAttribute("page", "profile");
		
		User user = null;
		User member = null;
		// Check if a user is connected :
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("auth");
			return;
		}
				
		user = (User) request.getSession().getAttribute("user");
		UserDAO userDAO = new UserDAO();
		
		if(request.getParameter("member")==null){
			response.sendRedirect("profile?member="+user.getId());
			return;
		}
		
		try{
			member = userDAO.getById(Integer.parseInt(request.getParameter("member")));
		}catch(NumberFormatException e){
			response.sendRedirect("profile?member="+user.getId());
			return;
		}
		if(member==null){
			getServletContext().getRequestDispatcher("/errorNoMember.jsp").forward(request, response);
			return;
		}
		
		// Request Attributes :
		
		// Le membre dont la page de profil fait objet
		request.setAttribute("member", member);
		// bool pour savoir si l'user connecté et le membre sont la même personne (user consultant sa page)
		boolean userIsMember = user.getId() == member.getId();
		request.setAttribute("userIsMember", userIsMember);
		// liste des associations du membre
		UserAssoDAO assoDAO = new UserAssoDAO();
		List<UserAsso> ualist = assoDAO.getUserAssos(member);
		request.setAttribute("ualist", ualist);
		// commentaires du membre
		CommentDAO commentDAO = new CommentDAO();
		List<Comment> comments = commentDAO.getComments(member, true, false);
		request.setAttribute("comments", comments);
		// commentaires laissés (n'est affiché que si l'user est le membre)
		if(userIsMember){
			List<Comment> commentsLeft = commentDAO.getCommentsLeft(member, false, true);
			request.setAttribute("commentsLeft", commentsLeft);
		}
		// La photo a afficher
		String photoPath = "img/default_picture.png"; // default
		
		File photo = AppConfig.getImage(AppConfig.getConfigValue("data_dir")+"/"+AppConfig.getConfigValue("pictures_dir_1"), member.getId());
		
		if(photo != null)
			photoPath = request.getContextPath()+"/"+AppConfig.getConfigValue("pictures_dir_1")+"/"+photo.getName();
		
		request.setAttribute("photoPath", photoPath);
		
		// La blouse à afficher
		String blousePath = null; // default
		
		File blouse = AppConfig.getImage(AppConfig.getConfigValue("data_dir")+"/"+AppConfig.getConfigValue("pictures_dir_2"), member.getId());
		
		if(blouse != null)
			blousePath = request.getContextPath()+"/"+AppConfig.getConfigValue("pictures_dir_2")+"/"+blouse.getName();
		
		request.setAttribute("blousePath", blousePath);
		
		// Les logos des assos :
		int i=0;
		for (UserAsso ua : ualist){
			i++;
			File logo = AppConfig.getImage(AppConfig.getConfigValue("data_dir")+"/"+AppConfig.getConfigValue("logos_dir"), ua.getAsso());
			String logoPath = request.getContextPath()+"/"+AppConfig.getConfigValue("logos_dir")+"/"+logo.getName();
			request.setAttribute("logoasso"+i, logoPath);
		}
		
		// appel à la JSP avec tous les attributs
		getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
	}

}
