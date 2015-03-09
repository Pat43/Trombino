package trombino;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TempUser;
import util.Validator;
import dao.TempUserDAO;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }

    /*
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
 		
 		request.setAttribute("page", "register");
 		
 		// Java initialisations :
 		 		
 		if(request.getSession().getAttribute("user")!=null){
 			response.sendRedirect("trombino");
 			return;
 		}
 		
 		if( ! request.getParameterMap().isEmpty()){
 			
 			List<String> errors = Validator.checkRegisterParameterMap(request);
 			
 			/* SECURITY : VALIDATE FIELDS */
 			String firstName = Validator.validateString(request.getParameter("firstName"));
 			String lastName = Validator.validateString(request.getParameter("lastName"));
 			String nickname = Validator.validateString(request.getParameter("nickname"));
 			String email = Validator.validateString(request.getParameter("email"));
 			String email_hide=request.getParameter("email_hide")!=null?"ok":"";			
		
 			if(errors.isEmpty()){
	 			
				// Create User
				TempUserDAO userDAO = new TempUserDAO();
				TempUser user = new TempUser();
				
				user.setFirstName(firstName);
				user.setLastName(lastName);
				if(!nickname.equals(""))
					user.setNickname(nickname);
				user.setEmail(email);
				user.setHide_email(email_hide.equals("ok")?true:false);

				String password = request.getParameter("password");
				user.setHashedPassword(util.Encryption.crypt(password));
				
				user = userDAO.save(user);
				
				if(user != null){
					request.setAttribute("user",user);
					request.setAttribute("action","register");
					RequestDispatcher dis=request.getRequestDispatcher("send_email");
					dis.forward(request,response);
					return;
				}
			}
 			else{
 				request.setAttribute("errors", errors);
 		 		request.setAttribute("firstName", firstName);
 				request.setAttribute("lastName", lastName);
 				request.setAttribute("nickname", nickname);
 				request.setAttribute("email", email);
 				request.setAttribute("email_hide", email_hide);
 			}
 		}
 		
 		getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
 	}

	

}
