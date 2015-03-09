package trombino;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String message = "";
		
		if(request.getParameter("register") != null)
			message = "Un email vient de t'être envoyé pour confirmer ton inscription.";
		else if(request.getParameter("registerOk")!=null)
			message = "Confirmation prise en compte ! Tu peux maintenant te connecter au site.";
		else if(request.getParameter("resetPwd") != null)
			message = "Un email vient de t'être envoyé pour réinitialiser ton mot de passe.";
		else if(request.getParameter("resetPwdOk") != null)
			message = "Ton mot de passe a été réinitialisé. Tu peux maintenant te connecter au site.";
		
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/confirmation.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
