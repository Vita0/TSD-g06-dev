/*******************************************************************************
 * Copyright (c) 2014 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.testcases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db.DbAbstract;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class TestCase_db_alive extends DbAbstract {

	static Logger log = Log4JUtils.getInstance().getLogger();
	
	
	public static void main(String[] args) {

		//Step 1
		log.info("----Step 1-------");

		log.info("Check MySQL connection...");
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			log.info("----Step 2-INSERT------");
			try{
				Statement st = conn.createStatement();
				int val = st.executeUpdate("INSERT "+ dbName+ ".comcompanies VALUES('1','tango')");
				val += st.executeUpdate("INSERT "+ dbName+ ".comcompanies VALUES('2','orange')");
				val += st.executeUpdate("INSERT "+ dbName+ ".comcompanies VALUES('3','post')");
				log.debug(val + " row(s) affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed " + s);
			}


			/********************/
			//Select
			log.info("----Step 3-SELECT------");
			try{
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery("SELECT * FROM  "+ dbName+ ".comcompanies");
				log.info("ComCompany's ID" + "\t" + "ComCompany's Name");

				while (res.next()) {
					String name = res.getString("id");
					String pass = res.getString("name");
					
					log.info(name + "\t" + pass);
				}

			}
			catch (SQLException s){
				log.error("SQL code does not execute " + s);
			}  



			/********************/
			log.info("----Step 4-DELETE------");
			//Delete
			try{
				//Delete latest inserted element
				String sql = "DELETE FROM comcompanies WHERE id = ?";
				String id = "1";

				PreparedStatement statement = conn.prepareStatement(sql);

				 // Pass a value of a name that will tell the database which
	            // records in the database to be deleted. Remember that when
	            // using a statement object the index parameter is started from
	            // 1 not 0 as in the Java array data type index.
	            statement.setString(1, id);
	             
	            // Tell the statement to execute the command. The executeUpdate()
	            // method for a delete command returns number of records deleted
	            // as the command executed in the database. If no records was
	            // deleted it will simply return 0
	            int rows = statement.executeUpdate();
				log.debug(rows +" row(s) affected");

				//Repet for id = 2 and 3
				statement.setString(1, "2");
				rows = statement.executeUpdate();
				log.debug(rows +" row(s) affected");

				statement.setString(1, "3");
				rows = statement.executeUpdate();
				log.debug(rows +" row(s) affected");

			}
			catch (SQLException s){
				log.error("SQL statement is not executed "+s);
			}



			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
