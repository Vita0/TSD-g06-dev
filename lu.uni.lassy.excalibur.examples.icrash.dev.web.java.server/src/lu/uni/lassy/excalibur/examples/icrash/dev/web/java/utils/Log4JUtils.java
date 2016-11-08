/*******************************************************************************
 * Copyright (c) 2014 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Anton Nikonienkov - iCrash HTML5 API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils;

import java.net.URL;
//import java.util.Properties;

//import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4JUtils {

	// our log4j category reference
	static Logger log = Logger.getLogger(Log4JUtils.class);
	final String LOG_PROPERTIES_FILE = "Log4J.properties";

	//Eager singleton pattern
	private static volatile Log4JUtils instance = new Log4JUtils();
 
    // private constructor
    private Log4JUtils() {
    	initializeLogger();
    	log.debug("Log4JUtils - leaving the constructor ...");
    }
 
    public static Log4JUtils getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return log;
    }

	private void initializeLogger() {
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(LOG_PROPERTIES_FILE);
		
		PropertyConfigurator.configure(url);
		log.debug("Logging initialized."); 
	}

	public static void main(String[] args) {

		// Log4J is now loaded; try it
		log.info("leaving the main method of Log4JUtils");

		// Log4J is now loaded; try it
		log.debug("and here in DEBUG mode message: leaving the main method of Log4JUtils");
	}
}
