package trombino;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.apache.commons.lang.StringEscapeUtils;

import dao.UserDAO;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
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
		
		request.setAttribute("page", "search");
		
		String search = "";
		// Check if a user is connected :
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("auth");
			return;
		}
		
		if(request.getParameter("search")!=null)
				search = StringEscapeUtils.escapeSql(request.getParameter("search"));

			
		request.setAttribute("search", search);
		
		if(!request.getParameterMap().isEmpty()){
			UserDAO userDAO = new UserDAO();
			List<User> results = userDAO.filterUsers(search);
			request.setAttribute("results", results);
		}
		
		getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);
			
	}

}
