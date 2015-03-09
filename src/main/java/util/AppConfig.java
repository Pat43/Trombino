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

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Loic Teyssier
 *
 */
public class AppConfig {
	//TODO : Write a version that can return a list of Strings (to set several parameters with only one call)
	public static String getConfigValue(String property){
		String value = "";
		Properties prop = new Properties();
        try
        {
            InputStream is = AppConfig.class.getResourceAsStream("/config/trombino.conf");
            prop.load(is);
            value = prop.getProperty(property,"");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        // Retrieve Environment Variables
        if(value.startsWith("$"))
        	value = System.getenv(value.substring(1));
        
		return value;
	}
	
	/**
	 * @author Loic Teyssier
	 * @param path
	 * @param id
	 * @return
	 */
	public static File getImage(String path, final int id) { //FIXME : Nullpointerexception quand l'image n'est pas trouvée
		File dir = new File(path);
		File[] matchingFiles = dir.listFiles(new FileFilter() {
		    public boolean accept(File pathname) {
		        return pathname.getName().startsWith(id+".");
		    }
		});
		if(matchingFiles!=null && matchingFiles.length != 0)
			return matchingFiles[0];
		else
			return null;
	}

}
