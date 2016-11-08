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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment;

import java.io.Serializable;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActActivator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActMsrCreator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;

public class IcrashEnvironment implements Serializable {
	private static final long serialVersionUID = 8618361443594318413L;
	
	static ActMsrCreator actMsrCreator;
	static ActActivator actActivator;
	
	static Hashtable<DtLogin, ActAdministrator> admins = new Hashtable<DtLogin, ActAdministrator>();
	static Hashtable<DtLogin, ActCoordinator> coordinators = new Hashtable<DtLogin, ActCoordinator>();
	static Hashtable<String, ActComCompany> comCompanies = new Hashtable<String, ActComCompany>();
	
	//Eager singleton pattern
	private static volatile IcrashEnvironment instance = null;
	
	private IcrashEnvironment() {
     	actMsrCreator = new ActMsrCreator();
    }
	
	public static IcrashEnvironment getInstance() {
     	if(instance == null)
        	instance = new IcrashEnvironment();
        return instance;
    }
	
	public ActMsrCreator getActMsrCreator(){
		return actMsrCreator;
	}
	
	public void setActAdministrator(DtLogin keyName,ActAdministrator aActAdministrator){
		admins.put(keyName, aActAdministrator);
	}

	public ActAdministrator getActAdministrator(DtLogin keyName){
		return admins.get(keyName);
	}
	
	public Hashtable<DtLogin, ActAdministrator> getAdministrators(){
		return admins;
	}
	
	public void setActActivator(ActActivator aActActivator){
		actActivator = aActActivator;
	}
	
	public ActActivator getActActivator(){
		return actActivator;
	}
	
	public void setActCoordinator(DtLogin keyName, ActCoordinator aActCoordinator){
		coordinators.put(keyName, aActCoordinator);
	}
	
	public ActCoordinator getActCoordinator(DtLogin keyName){
		return coordinators.get(keyName);
	}

	public void setComCompany(String keyName, ActComCompany aActComCompany) {
		comCompanies.put(keyName, aActComCompany);
	}
	
	public ActComCompany getComCompany(String keyName){
		return comCompanies.get(keyName);
	}
	
	public Hashtable<String, ActComCompany> getActComCompanies(){
		return comCompanies;
	}
}