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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Loic Teyssier
 *
 */
public class Lists {
	
		
		/**
		 * List of all departments.
		 * 0: Info
		 * 1: EE
		 * 2: IMSI
		 * 3: MC
		 * 4: EDIM
		 */
		public static ArrayList<String> departments = new ArrayList<String>(
					Arrays.asList("Info","EE","IMSI", "MC","EDIM")
				);
		
		
		/**
		 * List of all filières per department
		 */
		@SuppressWarnings("unchecked")
		public static ArrayList<ArrayList<String>> filieres = new ArrayList<ArrayList<String>>(
				Arrays.asList(
						/* Info */
						new ArrayList<String>(Arrays.asList("ILC","I2RV", "LEIM", "R&D", "ISI (Apprentissage)")),
						/* EE */
						new ArrayList<String>(Arrays.asList("BEE", "PE", "RCS", "TSEE", "GE (Apprentissage)")),
						/* IMSI */
						new ArrayList<String>(Arrays.asList("ICP", "INP", "LOI", "QPI", "Apprentissage")),
						/* MC */
						new ArrayList<String>(Arrays.asList("CDP", "CIM", "CSM", "MOM", "THE")),
						/*EDIM*/
						new ArrayList<String>(Arrays.asList("DIC", "EcIM", "EIC"))
						)
				);
		
		
		
		
		public static ArrayList<String> assos = new ArrayList<String>(
				Arrays.asList("", "AE","BDF","BDS", "CETU","Promo 11", "Promo 10", "Ski-Ut",
						"Congrès Industriel",
						"Gala de Prestige",
						"Intégration 2010",
						"Intégration 2011",
						"Intégration 2012",
						"Intégration 2013",
						"Semaine de Mars 2010",
						"Semaine de Mars 2011",
						"Semaine de Mars 2012",
						"Semaine de Mars 2013",
						"Semaine de Mars 2014",
						"Week-end à l'UTBM",
						"FF1J",
						"Club Welcome",
						"Petit Géni",
						"Solidar'UT",
						"Eco'UT",
						"Laverie",
						"Aquariums",
						"Club Astro",
						"Club DDR",
						"La Bohème",
						"Troll Penché",
						"UT Toons",
						"Seven'Art",
						"Super Flux",
						"Club Cuisine",
						"Airsoft",
						"Kara'UT",
						"OEnologie",
						"Club Tupperware",
						"Bigband",
						"Club Dessin",
						"Club Graff",
						"Club Jonglage",
						"Club Mix",
						"Club Reflex",
						"Club Zik",
						"Club Lumières",
						"Club Théâtre",
						"Club MAO",
						"Orchestre",
						"Prom'Art",
						"UTBM Productions",
						"Club 4L",
						"Club Kart",
						"Club Solex",
						"Lolut",
						"Unitec",
						"Zénith",
						"Club Danse",
						"Club Poker",
						"Ut-Gaming",
						"ReZoMe",
						"Club Billard")
			);

	
	/**
	 * List of all month.
	 */
	public static ArrayList<String> monthes = new ArrayList<String>(
				Arrays.asList("","Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre")
			);
	
}
