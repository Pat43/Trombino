package trombino;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import dao.UserDAO;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/Auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auth() {
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
			
		request.setAttribute("page", "auth");
		
		User user = null;
		request.setAttribute("wrong", false);
		
		if(request.getParameter("logout") != null)
			request.getSession().invalidate();
		
		else if(request.getSession().getAttribute("user")!=null){
			response.sendRedirect("profile");
			return;
		}
			
		else if( ! request.getParameterMap().isEmpty()){
			
			String p_login = request.getParameter("email");
			String p_password = request.getParameter("password");
			UserDAO userDAO = new UserDAO();
			user = userDAO.connectUser(p_login, p_password);
			if(user == null)
				request.setAttribute("wrong", true);
			else{
				request.getSession().setMaxInactiveInterval(15*60);
				request.getSession().setAttribute("user", user);
				response.sendRedirect("index");
				return;
			}
		}
		
		getServletContext().getRequestDispatcher("/auth.jsp").forward(request, response);	
		
	}

}
