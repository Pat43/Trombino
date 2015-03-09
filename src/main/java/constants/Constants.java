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
package constants;


/**
 * @author Loic Teyssier
 *
 */
public class Constants {
	
	/**
	 * Config
	 */
	public static int PASSWORD_MIN_LENGTH = 6;
	public static int PASSWORD_MAX_LENGTH = 32;
	
	public static int USER_NORMAL_AGE = 22; // The normal age of the user, if has never double a class.
	public static int CALENDAR_REFERENCE_YEAR = 1991; // The year of birth of most users 
	public static int CALENDAR_DOWN_OFFSET = 7; // Offset to take care of persons who have double some classes. Particular cases (ie. more than 30 y.o.) will be treated manually.
	public static int CALENDAR_UP_OFFSET = 3; // Offset to take care of persons who have jumped some classes. Particular cases (ie. more than 30 y.o.) will be treated manually.
	
	public static int COMMENT_MAX_LENGTH = 500;
	
	public static int MAX_FILE_SIZE = 3145728; // bytes
	private static int byteSize = 1048576;
	public static String[] ACCEPTED_EXTENSIONS = {"jpg","jpeg", "png", "gif"};
	
	/**
	 * Errors Strings
	 */
	public static String FIRSTNAME_MISSING_STRING = "Veuillez entrer votre prénom.";
	public static String LASTNAME_MISSING_STRING = "Veuillez entrer votre nom.";
	public static String PASSWORD_MISSING_STRING = "Veuillez entrer un mot de passe.";
	public static String PASSWORD2_MISSING_STRING = "Veuillez confirmer votre mot de passe.";
	public static String GENDER_MISSING_STRING = "Veuillez renseigner votre sexe.";
	public static String CONDITION_MISSING_STRING = "Veuillez certifier que vous êtes en Promo 11.";
	public static String EMAIL_MISSING_STRING = "Veuillez entrer votre email.";
	public static String PHONE_INCORRECT_STRING = "Votre numéro de téléphone est incorect.";
	public static String BIRTHDAY_INCORRECT_STRING = "Votre date de naissance est incorecte.";
	public static String PASSWORDS_MISSMATCH_STRING = "Les mots de passe entrés ne correspondent pas.";
	public static String PASSWORD_TOO_SHORT_STRING = "Le mot de passe doit faire au moins "+PASSWORD_MIN_LENGTH+" caractères.";
	public static String PASSWORD_TOO_LONG_STRING = "Le mot de passe est trop long.";
	public static String EMAIL_NOT_AVAILABLE = "Cette adresse email est déjà utilisée.";
	public static String EMAIL_INVALID = "Cette adresse email n'est pas valide.";
	public static String WRONG_PASSWORD = "Mot de passe incorrect.";
	public static String FILE_TOO_BIG = "Le fichier est trop volumineux : "+ MAX_FILE_SIZE/byteSize +" Mo maximum.";
	public static String FILE_BAD_EXTENSION = "Formats de fichiers acceptés : jpg, png, gif.";
	

}
