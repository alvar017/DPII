/*
 * � * PopulateDatabase.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import utilities.internal.DatabasePopulator;

public class PopulateDatabase {

	public static void main(final String[] args) {
		DatabasePopulator.run("PopulateDatabase 1.18.2", "classpath:PopulateDatabase.xml");
	}

}
