package trombino;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.User;
import model.UserAsso;
import util.AppConfig;
import util.Encryption;
import util.Validator;
import constants.Constants;
import dao.UserAssoDAO;
import dao.UserDAO;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Edit_Profile")
@MultipartConfig
public class Edit_Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit_Profile() {
        super();
    }

    /**
 	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 	 */
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
 		request.setAttribute("page", "profile");
 		
 		User user = null;
 		
 		if(request.getSession().getAttribute("user")==null){
 			response.sendRedirect("auth");
 			return;
 		}
 		
		user = (User) request.getSession().getAttribute("user");
		
		
		// *** Case Delete image :
		if(request.getParameter("deleteImg") != null){
			deleteUserPhoto(user, getServletContext());
			response.sendRedirect("edit_profile");
			return;
		}
		if(request.getParameter("deleteBlouse")!= null){
			deleteUserBlouse(user, getServletContext());
			response.sendRedirect("edit_profile");
			return;
		}
		// ******
		
		// Case update infos
		
		List<String> errors=new ArrayList<String>();
		String nickname="";
		String phone="";
		String email_hide="";
		String day="";
		String month="";
		String year="";
		String department="";
		String filiere="";
		String filiere2="";
		String godfathers="";
		String godsons="";
		String password="";
		int asso1=0;
		String role1="";
		int asso2=0;
		String role2="";
		int asso3=0;
		String role3="";
		int asso4=0;
		String role4="";
		String phrase="";
		
		if(request.getParameterMap().isEmpty()){
			if(user.getNickname()!=null)
				nickname= user.getNickname();
			if(user.getPhone()!=null)
				phone= user.getPhone();
	
			email_hide=user.isHide_email()?"ok":"";
			
			if(user.getBirthday()!=null){
	 			Calendar cal = Calendar.getInstance();
	 			cal.setTime(user.getBirthday());
	 			day=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	 			month=String.valueOf(cal.get(Calendar.MONTH)+1);
	 			year=String.valueOf(cal.get(Calendar.YEAR));
			}
			 
			if(user.getDepartment()!=null)
				department=user.getDepartment();
			if(user.getSpecialisation()!=null)
				filiere=user.getSpecialisation();
			if(user.getSecondSpecialisation()!=null)
				filiere2=user.getSecondSpecialisation();
			
			UserAssoDAO assoDAO = new UserAssoDAO();
			List<UserAsso> userAssos = assoDAO.getUserAssos(user);
			if(userAssos.size()>0){
				asso1 = userAssos.get(0).getAsso();
				role1 = userAssos.get(0).getRole();
			}
			if(userAssos.size()>1){
				asso2 = userAssos.get(1).getAsso();
				role2 = userAssos.get(1).getRole();
			}
			if(userAssos.size()>2){
				asso3 = userAssos.get(2).getAsso();
				role3 = userAssos.get(2).getRole();
			}
			if(userAssos.size()>3){
				asso4 = userAssos.get(3).getAsso();
				role4 = userAssos.get(3).getRole();
			}
			 			
			if(user.getGodfathers()!=null)
				godfathers= user.getGodfathers();
			if(user.getGodsons()!=null)
				godsons= user.getGodsons();
			 
			if(user.getPersonalPhrase()!=null)phrase=user.getPersonalPhrase();
		}

		else if(request.getParameter("htmlFormName") != null && request.getParameter("htmlFormName").equals("infos")){
	 			errors.addAll(Validator.checkEditParameterMap(request, user));
	 			
	 			if(!Validator.isNullorEmpty(request,"oldpassword") || !Validator.isNullorEmpty(request,"password") || !Validator.isNullorEmpty(request,"password2"))
		 			if(!Encryption.crypt(request.getParameter("oldpassword")).equals(user.getHashedPassword()))
		 				errors.add(Constants.WRONG_PASSWORD);
	 			/* SECURITY : VALIDATE FIELDS */
	 			if(request.getParameter("nickname") != null)
					nickname = Validator.validateString(request.getParameter("nickname"));
	 			if(request.getParameter("phone") != null)
	 				phone = Validator.validateString(request.getParameter("phone"));
	 			
				email_hide=request.getParameter("email_hide")!=null?"ok":""; // si autre chose que ok, tentative de pine -> deselectionner
				if(request.getParameter("day") != null)
					day= request.getParameter("day"); // pas besoin de valider, date testée dans checkParameterMap()
				if(request.getParameter("month") != null)
					month= request.getParameter("month"); // pas besoin de valider, date testée dans checkParameterMap()
				if(request.getParameter("year") != null)
					year= Validator.validate_year(request.getParameter("year"));
				
				if(request.getParameter("department") != null)
					department= Validator.validate_department(request.getParameter("department"));
				if(request.getParameter("filiere") != null)
					filiere= Validator.validate_filiere(department,request.getParameter("filiere"));
				if(request.getParameter("filiere2") != null)
					filiere2= Validator.validate_filiere(department,request.getParameter("filiere2"));
				if(filiere.equals(filiere2))
					filiere2=null;
				if(filiere.equals("")){
					filiere=filiere2;
					filiere2=null;
				}
				if(department.equals("")){
					department = null;
					filiere=null;
					filiere2=null;
				}
				
				try{
	 				asso1 = Integer.parseInt(request.getParameter("asso1"));
	 				asso2 = Integer.parseInt(request.getParameter("asso2"));
	 				asso3 = Integer.parseInt(request.getParameter("asso3"));
	 				asso4 = Integer.parseInt(request.getParameter("asso4"));
	 			}catch(NumberFormatException e){
	 				asso1 = 0;asso2 = 0;asso3 = 0;asso4 = 0;
	 			}
	 			if(asso1!=0)
	 				role1= Validator.validateString(request.getParameter("role1"));
	 			if(asso2!=0)
	 				role2= Validator.validateString(request.getParameter("role2"));
	 			if(asso3!=0)
	 				role3= Validator.validateString(request.getParameter("role3"));
	 			if(asso4!=0)
	 				role4= Validator.validateString(request.getParameter("role4"));
	 			
				if(request.getParameter("godfathers") != null)
					godfathers =  Validator.validateString(request.getParameter("godfathers"));
				if(request.getParameter("godsons") != null)
					godsons = Validator.validateString(request.getParameter("godsons"));
				
				if(request.getParameter("password") != null)
					password = request.getParameter("password");

				if(request.getParameter("phrase") != null)
					phrase= Validator.validateString(request.getParameter("phrase"));
			
				/* ****** */
	 			
	 			if(errors.isEmpty()){
	 			// Create User
					UserDAO userDAO = new UserDAO();
					
					if(nickname.equals(""))nickname=null;
					user.setNickname(nickname);
					user.setHide_email(email_hide.equals("ok")?true:false);

					if(phone.equals(""))phone=null;
					user.setPhone(phone);

					user.setDepartment(department);
					user.setSpecialisation(filiere);
					user.setSecondSpecialisation(filiere2);
					if(godfathers.equals(""))godfathers=null;
					user.setGodfathers(godfathers);
					if(godsons.equals(""))godsons=null;
					user.setGodsons(godsons);
					
					if(phrase.equals(""))phrase=null;
					user.setPersonalPhrase(phrase);
					
					if(!password.equals(""))
						user.setHashedPassword(util.Encryption.crypt(password));
					
					// Birthday :
					SimpleDateFormat parserSDF=new SimpleDateFormat("d/MM/yyyy");
					try {
						if(!year.equals("")){
							java.util.Date bDay = parserSDF.parse(day+"/"+month+"/"+year);
							user.setBirthday(bDay);
						}
					}catch (ParseException e) {
						e.printStackTrace();
					}
					
					userDAO.update(user);
					
					List<UserAsso> assosToSave = new ArrayList<UserAsso>();
					UserAssoDAO assoDAO2 = new UserAssoDAO();
	
					if(asso1 != 0)
						assosToSave.add(new UserAsso(user, asso1, role1));
					if(asso2 != 0)
						assosToSave.add(new UserAsso(user, asso2, role2));
					if(asso3 != 0)
						assosToSave.add(new UserAsso(user, asso3, role3));
					if(asso4 != 0)
						assosToSave.add(new UserAsso(user, asso4, role4));
					
					assoDAO2.editUserAssos(user, assosToSave);
					
					response.sendRedirect("profile?member="+user.getId());
					return;
				}
		}	
		
		//***
		
		// If errors or first time coming, send correct data to JSP
		
		request.setAttribute("errors", errors);
 		
 		// Images :
 		
 			// -> Photo
 		String path = AppConfig.getConfigValue("data_dir")+"/"+AppConfig.getConfigValue("pictures_dir_1");
		
		File photo = AppConfig.getImage(path, user.getId());
		if(photo != null)
			request.setAttribute("photoPath", request.getContextPath()+"/"+AppConfig.getConfigValue("pictures_dir_1")+"/"+photo.getName());
			
			// -> Blouse
		path = AppConfig.getConfigValue("data_dir")+"/"+AppConfig.getConfigValue("pictures_dir_2");
		
		File blouse = AppConfig.getImage(path, user.getId());
		if(blouse != null)
			request.setAttribute("blousePath", request.getContextPath()+"/"+AppConfig.getConfigValue("pictures_dir_2")+"/"+blouse.getName());
		
		
 		// Infos :
 		request.setAttribute("nickname", nickname);
 		request.setAttribute("phone", phone);
 		request.setAttribute("day", day);
 		request.setAttribute("month", month);
 		request.setAttribute("year", year);
 		request.setAttribute("phrase", phrase);
 		request.setAttribute("department", department);
 		request.setAttribute("filiere", filiere);
 		request.setAttribute("filiere2", filiere2);
 		request.setAttribute("godfathers", godfathers);
 		request.setAttribute("godsons", godsons);
 		request.setAttribute("asso1", asso1);
 		request.setAttribute("role1", role1);
 		request.setAttribute("asso2", asso2);
 		request.setAttribute("role2", role2);
 		request.setAttribute("asso3", asso3);
 		request.setAttribute("role3", role3);
 		request.setAttribute("asso4", asso4);
 		request.setAttribute("role4", role4);
 		request.setAttribute("email_hide", email_hide);
 		

 		
 		getServletContext().getRequestDispatcher("/edit_profile.jsp").forward(request, response);
		
 	}

 	/**
 	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 	 */
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
 		User user = null;
 		
 		if(request.getSession().getAttribute("user")==null){
 			response.sendRedirect("auth");
 			return;
 		}
 		
		user = (User) request.getSession().getAttribute("user");
		
 		List<String> errors=new ArrayList<String>();
			
 		// get access to file that is uploaded from client
		List<Part> parts = new ArrayList<Part>();
		parts.add(request.getPart("newImg"));
		parts.add(request.getPart("newBlouse"));
		
		int partIndex = 0;
		for(Part file : parts){
	        if(file!=null && file.getSize() !=0){
		       
		        String filename = getFilename(file);
		        String extension = "";
		        if(filename.contains("."))
		        	extension = filename.substring(filename.lastIndexOf('.')+1, filename.length());
		        
		        if(file.getSize()>Constants.MAX_FILE_SIZE)
		        	errors.add(Constants.FILE_TOO_BIG);
		        if(!Arrays.asList(Constants.ACCEPTED_EXTENSIONS).contains(extension.toLowerCase()))
		        	errors.add(Constants.FILE_BAD_EXTENSION);
		        
		        if(errors.isEmpty()){
		      
			        String relativePath;
			        if(partIndex == 0)
			        	relativePath = AppConfig.getConfigValue("pictures_dir_1")+"/";
			        else if (partIndex == 1)
			        	relativePath = AppConfig.getConfigValue("pictures_dir_2")+"/";
			        else return;
			        
			        deleteUserImage(user, getServletContext(), relativePath);
			        
			        String outputfile = AppConfig.getConfigValue("data_dir")+"/"+relativePath+user.getId()+"."+extension;
			        FileOutputStream os = new FileOutputStream (outputfile);
			        
			        // write bytes taken from uploaded file to target file
			        InputStream is = file.getInputStream();
			        int ch = is.read();
			        while (ch != -1) {
			             os.write(ch);
			             ch = is.read();
			        }
			        os.close();	
		        }
	        }
	        partIndex++;
		}
		if(errors.isEmpty()){ 
			response.sendRedirect("profile?member="+user.getId());
			return;
        }
		
		// else send errors to JSP
		request.setAttribute("errors", errors);
		getServletContext().getRequestDispatcher("/edit_profile.jsp").forward(request, response);
		
 	}
 	

 	
 	private static String getFilename(Part part) {
 	    for (String cd : part.getHeader("content-disposition").split(";")) {
 	        if (cd.trim().startsWith("filename")) {
 	            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
 	            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
 	        }
 	    }
 	    return null;
 	}
 	private static void deleteUserPhoto(User user, ServletContext servletContext){
 		deleteUserImage(user, servletContext, AppConfig.getConfigValue("pictures_dir_1")+"/");
 	}
 	private static void deleteUserBlouse(User user, ServletContext servletContext){
 		deleteUserImage(user, servletContext, AppConfig.getConfigValue("pictures_dir_2")+"/");
 	}
 	private static void deleteUserImage(User user, ServletContext servletContext, String relativePath){
 		String path = AppConfig.getConfigValue("data_dir")+"/"+relativePath;
		File dir = new File(path);
		final int id = user.getId(); // needs to be final so the anonymous class can use it
		File[] matchingFiles = dir.listFiles(new FileFilter() {
		    public boolean accept(File pathname) {
		        return pathname.getName().startsWith(id+".");
		    }
		});
		for(File f : matchingFiles)
			f.delete();
 	}

}
