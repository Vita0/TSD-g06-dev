/*******************************************************************************
 * Copyright (c) 2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Anton Nikonienkov - iCrash HTML5 API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.DriverManager;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.ScriptRunner;

/**
 * The Class used to create iCrash MySQL database tables or truncate them all if exist
 */
public class DbInitialize extends DbAbstract {
	
	static public void initializeDatabase() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url,userName,password);
			log.debug("Connected to the database");

			/********************/
			// Create database (truncate old one, if exists)

			// the following path means: read from the same package where this class is
			URL url = DbInitialize.class.getResource("icrash_db_create.sql");
			File file = new File(url.getPath());
			
			ScriptRunner runner = new ScriptRunner(conn, false, false);
			runner.runScript(new BufferedReader(new FileReader(file)));
			
			conn.close();
			log.debug("Disconnected from database");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
