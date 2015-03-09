/*
 * Copyright (C) 2014, Teyssier Loic

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.User;

import org.apache.commons.lang.StringEscapeUtils;

import constants.Constants;
import constants.Lists;
import dao.UserDAO;

/**
 * @author Loic Teyssier
 *
 */
public class Validator {
	
	HttpServletRequest request; 
	
	/**
	 * @author Loic Teyssier
	 * @param request
	 * Checks if the given parameters are correct.
	 * @return 0 if all parameters are correct. A list of error codes if some have to be changed by the user.
	 */
	public static List<String> checkRegisterParameterMap(HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		
		/* CHECK REQUIRED FIELDS */
		if(isNullorEmpty(request, "firstName"))
			errors.add(Constants.FIRSTNAME_MISSING_STRING);
		if(isNullorEmpty(request, "lastName"))
			errors.add(Constants.LASTNAME_MISSING_STRING);
		if(isNullorEmpty(request, "email"))
			errors.add(Constants.EMAIL_MISSING_STRING);
		if(isNullorEmpty(request, "password"))
			errors.add(Constants.PASSWORD_MISSING_STRING);
		else if(isNullorEmpty(request, "password2"))
			errors.add(Constants.PASSWORD2_MISSING_STRING);
		
		
		/* CHECK EMAIL AVAILABILITY */
		UserDAO userDAO = new UserDAO();
		
		if(!isNullorEmpty(request, "email")){
			if(!userDAO.isEmailAvailable(StringEscapeUtils.escapeSql(request.getParameter("email")),null))
				errors.add(Constants.EMAIL_NOT_AVAILABLE);
			else if(!request.getParameter("email").contains("@")
					|| !request.getParameter("email").substring(request.getParameter("email").indexOf("@")).contains("."))
				errors.add(Constants.EMAIL_INVALID);
		}
		
		/* CHECK PASSWORDS */
		// size
		if(!isNullorEmpty(request, "password") && request.getParameter("password").length()<Constants.PASSWORD_MIN_LENGTH)
			errors.add(Constants.PASSWORD_TOO_SHORT_STRING);
		else if (!isNullorEmpty(request, "password") && request.getParameter("password").length()>Constants.PASSWORD_MAX_LENGTH)
			errors.add(Constants.PASSWORD_TOO_LONG_STRING);
		// matching
		if(!isNullorEmpty(request, "password") && !isNullorEmpty(request, "password2") && !request.getParameter("password").equals(request.getParameter("password2")))
			errors.add(Constants.PASSWORDS_MISSMATCH_STRING);								// Password mismatch

		if(isNullorEmpty(request, "condition"))
			errors.add(Constants.CONDITION_MISSING_STRING);
		
		return errors;
	}
	
	
	public static List<String> checkEditParameterMap(HttpServletRequest request, User user){
		List<String> errors = new ArrayList<String>();
		
		/* CHECK REQUIRED FIELDS */
		if(!isNullorEmpty(request, "oldpassword") || !isNullorEmpty(request, "password") || !isNullorEmpty(request, "password2")){
			if(request.getParameter("password").equals(""))
				errors.add(Constants.PASSWORD_MISSING_STRING);
			else if(request.getParameter("password2").equals(""))
				errors.add(Constants.PASSWORD2_MISSING_STRING);
		}
		
		if(!isNullorEmpty(request, "phone") && (!request.getParameter("phone").matches("[+]?[0-9]+") || request.getParameter("phone").length() < 2))
			errors.add(Constants.PHONE_INCORRECT_STRING);
				
		/* CHECK BIRTHDAY */
		if(!isNullorEmpty(request, "day") && !isNullorEmpty(request, "month") && !request.getParameter("month").equals("0") && !isNullorEmpty(request, "year")){ //If all date fields are completed
			SimpleDateFormat parserSDF=new SimpleDateFormat("d/MM/yyyy");
			parserSDF.setLenient(false);
			try {
				parserSDF.parse(request.getParameter("day")+"/"+request.getParameter("month")+"/"+request.getParameter("year")); // check if date is correct
			} catch (ParseException e) {
				errors.add(Constants.BIRTHDAY_INCORRECT_STRING);
			}
		}
		else if(!isNullorEmpty(request, "day") || (!isNullorEmpty(request, "month") && !request.getParameter("month").equals("0")) || !isNullorEmpty(request, "year")) // else if at least 1 is completed (but not all)
			errors.add(Constants.BIRTHDAY_INCORRECT_STRING);
		
		// otherwise, no fields are completed, so the date will be set to null.
		
		/* CHECK PASSWORDS */
		// size
		if(!isNullorEmpty(request, "oldpassword") || !isNullorEmpty(request, "password") || !isNullorEmpty(request, "oldpassword2")){
			if(request.getParameter("password").length()<Constants.PASSWORD_MIN_LENGTH)
				errors.add(Constants.PASSWORD_TOO_SHORT_STRING);
			else if (request.getParameter("password").length()>Constants.PASSWORD_MAX_LENGTH)
				errors.add(Constants.PASSWORD_TOO_LONG_STRING);
			// matching
			if(!request.getParameter("password").equals(request.getParameter("password2")))
				errors.add(Constants.PASSWORDS_MISSMATCH_STRING);								// Password mismatch
		}
		/* CHECK FILIERES */
		
		//TODO: Ajouter un test pour la filière : votre filière ne correspond pas à votre branche.
		
		return errors;
	}
	
	public static String validate_department(String department){
		
		for(String dept : Lists.departments)
			if(dept.equals(department))
				return department;
		return "";
	}
	
	public static String validate_filiere(String department, String filiere){
		
		int i=0;
		for(List<String> dept : Lists.filieres){
				for(String fil : dept)
					if(fil.equals(filiere) && Lists.departments.get(i).equals(department))
						return filiere;
				i++;
		}
		return "";
	}
	
	public static String validate_year(String year){	
		try{
			if(Integer.parseInt(year) < Constants.CALENDAR_REFERENCE_YEAR-Constants.CALENDAR_DOWN_OFFSET
					&& Integer.parseInt(year) > Constants.CALENDAR_REFERENCE_YEAR-Constants.CALENDAR_UP_OFFSET)
				year = "";
		}catch(NumberFormatException nfe){
			year = "";
		}
		return year;
	}
	
	public static boolean isNullorEmpty(HttpServletRequest request, String parameter){
		if(request.getParameter(parameter) == null || request.getParameter(parameter).equals(""))
			return true;
		
		return false;
	}
	
	public static String validateString(String string){
		
		if(string == null)
			return "";
		
		string = string.replace("<", "").replace("&lt;", "").replace("&#60;", "").replace("javascript", "").replace("script", "");
	
		return string;
	}

}