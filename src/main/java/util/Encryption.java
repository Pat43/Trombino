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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Loic Teyssier
 *
 */
public class Encryption {
	
	public static String crypt(String string){
		
		if(string==null)
			return string;
		
		String hashedString = string;
		MessageDigest digest;
		try { 
			
			// Add salts
			string = AppConfig.getConfigValue("prefix_salt")+string+AppConfig.getConfigValue("suffix_salt");
			
			// Hash
			digest = MessageDigest.getInstance(AppConfig.getConfigValue("hash_algo"));
	        digest.update(string.getBytes(), 0, string.length());
	        hashedString = new BigInteger(1, digest.digest()).toString(16);
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	
		return hashedString;
	}

}
