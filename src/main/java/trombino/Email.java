package trombino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.mailsHashedCodes.RegisterCode;
import model.mailsHashedCodes.ResetPwdCode;

import org.apache.commons.lang.StringEscapeUtils;

import util.Encryption;
import util.Validator;
import constants.Constants;
import dao.RegisterCodeDAO;
import dao.ResetPwdCodeDAO;
import dao.TempUserDAO;
import dao.UserDAO;

/**
 * Servlet implementation class Email
 */
@WebServlet("/Email")
public class Email extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Email() {
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
	 * Fonction appelée lorsque l'utilisateur clique sur un lien reçu par email.
	 * 2 cas possibles : confirmation d'inscription, reset de password.
	 * Check si le code (chiffré) correspond à celui en base.
	 * @author loic
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getParameter("register")!=null){
			RegisterCodeDAO registerCodeDAO = new RegisterCodeDAO();
			RegisterCode code = registerCodeDAO.getById(request.getParameter("register"));
			
			if(code != null){
				// Save new User
				User user = new User(code.getUser());
				UserDAO userDAO = new UserDAO();
				userDAO.save(user);
				// Delete code
				registerCodeDAO.delete(code);
				// Delete Temp user
				TempUserDAO tempUserDAO = new TempUserDAO();
				tempUserDAO.delete(code.getUser());
			
				response.sendRedirect("confirmation?registerOk=");
				return;
			}
		}
		
		
		else if (request.getParameter("resetpassword")!=null){
			ResetPwdCodeDAO dao = new ResetPwdCodeDAO();
			ResetPwdCode code = dao.getById(request.getParameter("resetpassword"));
			
			if(code != null){
				List<String> errors = new ArrayList<String>();
				if(request.getParameter("password") != null){
					
					if (request.getParameter("password").equals(""))
						errors.add(Constants.PASSWORD_MISSING_STRING);
					else if(request.getParameter("password").length()<Constants.PASSWORD_MIN_LENGTH)
						errors.add(Constants.PASSWORD_TOO_SHORT_STRING);
					else if (request.getParameter("password").length()>Constants.PASSWORD_MAX_LENGTH)
						errors.add(Constants.PASSWORD_TOO_LONG_STRING);
					// is matching ?
					if(Validator.isNullorEmpty(request, "password2") 
							|| !request.getParameter("password").equals(request.getParameter("password2")))
						errors.add(Constants.PASSWORDS_MISSMATCH_STRING); // Password mismatch
					
					if(errors.isEmpty()){
						User user = code.getUser();
						UserDAO userDAO = new UserDAO();
						user.setHashedPassword(StringEscapeUtils.escapeSql(Encryption.crypt(request.getParameter("password"))));
						userDAO.update(user);
						dao.delete(code);
						
						response.sendRedirect("confirmation?resetPwdOk=");
						return;
					}
				}
				// si pas passé par le return : des erreurs.
				// si pas entré dans le if : vient de cliquer sur le lien. 
				request.setAttribute("errors", errors);
				getServletContext().getRequestDispatcher("/email_reset_password.jsp").forward(request, response);
				return;
			}
		}
		
		// si on arrive jusque là : les codes de vérification ne sont pas corrects, ou le paramètre passé n'est pas correct.
		getServletContext().getRequestDispatcher("/linkexpired.jsp").forward(request, response);
		
	}
}