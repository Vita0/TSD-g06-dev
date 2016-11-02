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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils;

public class MySqlUtils {
	
	private MySqlUtils(){
		Log4JUtils log = Log4JUtils.getInstance();
		String strPort = PropertyUtils.getInstance().getProperty("database.port", String.valueOf(DB_PORT));
		try{
			DB_PORT = Integer.parseInt(strPort);
		} catch (NumberFormatException e){
			log.getLogger().error("Unable to parse port number");
			log.getLogger().error(e.getMessage());
		}			
		DB_HOST = PropertyUtils.getInstance().getProperty("database.host", DB_HOST);
		DB_NAME = PropertyUtils.getInstance().getProperty("database.name", DB_NAME);
		DB_USER_NAME = PropertyUtils.getInstance().getProperty("database.username", DB_USER_NAME);
		DB_USER_PWD = PropertyUtils.getInstance().getProperty("database.password", DB_USER_PWD);
	}
	
	private static MySqlUtils instance;
	
	public static MySqlUtils getInstance(){
		if (instance == null)
			instance = new MySqlUtils();
		return instance;
	}
	
	private int DB_PORT = 3306;
	private String DB_HOST = "localhost";
	private String DB_NAME = "icrash";
	private String DB_USER_NAME = "il2_icrash";
	private String DB_USER_PWD = "il2_icrash";
	
	
	public int getDBPort(){
		return this.DB_PORT;
	}
	
	public String getDBHost(){
		return this.DB_HOST;
	}
	
	public String getDBName(){
		return this.DB_NAME;
	}
	
	public String getDBUserName(){
		return this.DB_USER_NAME;
	}
	
	public String getDBPassword(){
		return this.DB_USER_PWD;
	}
	
	public String getURL(){
		return "jdbc:mysql://" + DB_HOST +":" + String.valueOf(DB_PORT) + "/";
	}
	
//	final static String DB_PORT = "3306";
	
//	final static String DB_HOST = "192.168.1.3";
//	final static String DB_HOST = "localhost";
	
//	public final static String URL = "jdbc:mysql://" + DB_HOST +":" + DB_PORT + "/";

//	public final static String DB_NAME = "icrash";
	
//	public final static String DB_USER_NAME = "il2_icrash";
//	public final static String DB_USER_PWD = "il2_icrash";
	
}
