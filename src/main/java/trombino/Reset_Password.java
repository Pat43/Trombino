package trombino;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.apache.commons.lang.StringEscapeUtils;

import dao.UserDAO;

/**
 * Servlet implementation class Init_Password
 */
@WebServlet("/Init_Password")
public class Reset_Password extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reset_Password() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		boolean error = false;
		String email = "";
		if(request.getParameter("email")!= null){
			email = StringEscapeUtils.escapeSql(request.getParameter("email"));
			
			UserDAO userDAO = new UserDAO();
			User user = userDAO.getByEmail(email);
			if(user==null)
				error = true;
			else{
				request.setAttribute("user",user);
				request.setAttribute("action","resetPwd");
				RequestDispatcher dis=request.getRequestDispatcher("send_email");
				dis.forward(request,response);
				return;
			}
		}
		
		request.setAttribute("email",  email);
		request.setAttribute("error", error);
		getServletContext().getRequestDispatcher("/request_reset_password.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
