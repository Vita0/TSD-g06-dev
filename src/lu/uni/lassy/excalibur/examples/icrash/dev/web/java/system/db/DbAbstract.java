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

import java.sql.Connection;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.MySqlUtils;

public abstract class DbAbstract {
	public static Logger log = Log4JUtils.getInstance().getLogger();

	public static Connection conn = null;
	
	public static String url = MySqlUtils.getInstance().getURL();
	public static String dbName = MySqlUtils.getInstance().getDBName();
	public static String userName = MySqlUtils.getInstance().getDBUserName();
	public static String password = MySqlUtils.getInstance().getDBPassword();
	
}
