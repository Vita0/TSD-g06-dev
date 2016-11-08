package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class DbComCompanies extends DbAbstract {


	static Logger log = Log4JUtils.getInstance().getLogger();
	
	static Connection conn = null;

	

	static public void insertComCompany(String idComCompany, String nameComCompany){
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Insert
			
			try{
				Statement st = conn.createStatement();
	
				log.debug("[DATABASE]-Insert ComCompany");
				int val = st.executeUpdate("INSERT INTO "+ dbName+ ".comcompanies" +
											"(id,name)" + 
											"VALUES("+"'"+idComCompany+"','"+nameComCompany+"')");
				
				log.debug(val + " row affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}

	
			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}
	
	
	static public String getComCompanyName(String idComCompany){
		
		String nameComCompany = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".comcompanies WHERE id = " + idComCompany;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) 				
					nameComCompany = res.getString("name");
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return nameComCompany;

	}
	
	
		static public String getComCompanyID(String nameComCompany){
		
		String idComCompany = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Select
			
			try{
				String sql = "SELECT * FROM "+ dbName + ".comcompanies WHERE name = '" + nameComCompany +"'";

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if(res.next()) 				
					idComCompany = res.getString("id");
								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return idComCompany;

	}
	
	
	
	
	static public void deleteComCompany(String idComCompany){
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName,userName,password);
			log.debug("Connected to the database");

			/********************/
			//Delete
			
			try{
				String sql = "DELETE FROM "+ dbName+ ".comcompanies WHERE id = ?";
				String id = idComCompany;

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows+" row deleted");				
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}


			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
