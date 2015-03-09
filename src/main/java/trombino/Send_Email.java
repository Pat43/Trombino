package trombino;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TempUser;
import model.User;
import model.mailsHashedCodes.RegisterCode;
import model.mailsHashedCodes.ResetPwdCode;
import util.AppConfig;
import util.Encryption;
import util.SendMail;
import dao.RegisterCodeDAO;
import dao.ResetPwdCodeDAO;

/**
 * Servlet implementation class SendEmail
 */
@WebServlet("/SendEmail")
public class Send_Email extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Send_Email() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getAttribute("action") != null && request.getAttribute("action").equals("register")){
			// passer le userID de la table temp.
			TempUser user = (TempUser) request.getAttribute("user");
			// genérer un code avec l'id une chaine, et la date
			String code = Encryption.crypt("registertrombi11"+user.getId()+ (new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss")).format(new Date()));
			// enregistrer un record dans la table registercode
			RegisterCode registerCode = new RegisterCode(code, user);
			RegisterCodeDAO dao = new RegisterCodeDAO();
			dao.save(registerCode);
			// envoyer un email avec le lien.
			
			String url = request.getRequestURL().toString();
			url = url.substring(0, url.lastIndexOf("/"));
			
			String to = user.getEmail();
			String subject = "[Trombino 11] Confirmation d'inscription\n";
			String message =  "Merci de t'être inscrit sur le site du trombino de la promo 11 !\n"
					+ "Pour confirmer ton inscription, clique sur le lien ci dessous :\n"
					+ url+"/email?register="+code+"\n\n"
					+ "L'équipe du trombino.";
			String from = AppConfig.getConfigValue("email");
			String pass = AppConfig.getConfigValue("email_password");
			SendMail.send(to,subject, message, from, pass);
			
			response.sendRedirect("confirmation?register");
		}
		else if(request.getAttribute("action") != null && request.getAttribute("action").equals("resetPwd")){
			
			// passer le userID de la table temp.
			User user = (User) request.getAttribute("user");
			// genérer un code avec l'id une chaine, et la date
			String code = Encryption.crypt("resetemailtrombi"+user.getId()+ (new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss")).format(new Date()));
			// enregistrer un record dans la table registercode
			ResetPwdCode resetPwdCode = new ResetPwdCode(code, user);
			ResetPwdCodeDAO dao = new ResetPwdCodeDAO();
			dao.save(resetPwdCode);
			// envoyer un email avec le lien.
			
			String url = request.getRequestURL().toString();
			url = url.substring(0, url.lastIndexOf("/"));
			
			String to = user.getEmail();
			String subject = "[Trombino 11] Réinitialisation du mot de passe.\n";
			String message =  "Tu as demandé une réinitialisation de ton mot de passe.\n"
					+ "Pour ce faire, clique sur le lien ci dessous :\n"
					+ url+"/email?resetpassword="+code+"\n"
					+ "Attention, ce lien n'est valide que 24H.\n\n"
					+ "L'équipe du trombino.";
			String from = AppConfig.getConfigValue("email");
			String pass = AppConfig.getConfigValue("email_password");
			SendMail.send(to,subject, message, from, pass);
			
			response.sendRedirect("confirmation?resetPwd");
			
		}
	   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
