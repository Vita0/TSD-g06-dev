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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.Iterator;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActActivator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db.DbAlerts;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db.DbComCompanies;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db.DbCoordinators;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db.DbCrises;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db.DbHumans;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.AlertBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.CrisisBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtState;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtFamilyPhoneNumbers;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.ComCompaniesInLux;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class IcrashSystem implements Serializable {

	private static final long serialVersionUID = 5641668314501186132L;

	private ActAuthenticated currentRequestingAuthenticatedActor;
	private CtAuthenticated ctAuthenticatedInstance;
	private ActComCompany currentConnectedComCompany;

	CtState ctState = new CtState();
	
	//Eager singleton pattern
	private static volatile IcrashSystem instance = null;

	IcrashEnvironment env = IcrashEnvironment.getInstance();	
	
	public static int nrComCompanies = 0;
	public static ArrayList<String> comCompanyViewNames;
	
	public static IcrashSystem getInstance() {
		if (instance == null) {
			instance = new IcrashSystem();
		}
		return instance;
	}

	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	// Messir compositions
	//Key is the admin's login's name
// in this implementation there is no real server, but instead a logical part of the webapp represents such server (or The iCrash system)
// all connected clients (browsers) will read values from the system properties
// and those system properties should be THE SAME for all of them!
// that's why I did those variables static. It's the simplest way to share data between clients
// it is OK that way for an educative program... but surely not OK for a realworld production one!
	static Hashtable<DtLogin, CtAdministrator> cmpSystemCtAdministrator = new Hashtable<DtLogin, CtAdministrator>();
	
	//Key is the login name
    static Hashtable<DtLogin, CtAuthenticated> cmpSystemCtAuthenticated = new Hashtable<DtLogin, CtAuthenticated>();
	
	// Messir associations
	// assCtAuthenticatedActAuthenticated is mainly used to determine
	// if the login request of an authenticated actor can be granted
	// based on the given credentials and the registered ones
	public static Hashtable<CtAuthenticated, ActAuthenticated> assCtAuthenticatedActAuthenticated = new Hashtable<CtAuthenticated, ActAuthenticated>();
	public static Hashtable<CtCoordinator, ActCoordinator> assCtCoordinatorActCoordinator = new Hashtable<CtCoordinator, ActCoordinator>();
	
	// Messir associations	
	/**  A hashtable of the joint alerts and crises in the system, stored by their alert as a key. */
	static Hashtable<CtAlert, CtCrisis> assCtAlertCtCrisis = new Hashtable<CtAlert, CtCrisis>();

	/**  A hashtable of the joint humans and Actor com companies in the system, stored by the human as a key. */
	static Hashtable<CtHuman, ActComCompany> assCtHumanActComCompany = new Hashtable<CtHuman, ActComCompany>();
	
	/**  A hashtable of the joint crises and coordinators in the system, stored by their crisis as a key. */
	static Hashtable<CtCrisis, CtCoordinator> assCtCrisisCtCoordinator = new Hashtable<CtCrisis, CtCoordinator>();

	/**  A hashtable of the crises in the system, stored by their ID as a key. */
	static Hashtable<String, CtCrisis> cmpSystemCtCrisis = new Hashtable<String, CtCrisis>();

	/**  A hashtable of the humans in the system, stored by their phone number as a key. */
	public static Hashtable<String, CtHuman> cmpSystemCtHuman = new Hashtable<String, CtHuman>();

	/**  A hashtable of the joint alerts and humans in the system, stored by their alert as a key. */
	static Hashtable<CtAlert, CtHuman> assCtAlertCtHuman = new Hashtable<CtAlert, CtHuman>();

	/**  A hashtable of the alerts in the system, stored by their ID as a key. */
	public static Hashtable<String, CtAlert> cmpSystemCtAlert = new Hashtable<String, CtAlert>();
	
	/**  A hashtable of the actor com companies in the system, stored by their name as a key. */
	public static Hashtable<String, ActComCompany> cmpSystemActComCompany = new Hashtable<String, ActComCompany>();
	
	public PtBoolean oeCreateSystemAndEnvironment(PtInteger aQtyComCompanies) {

		log.debug("in IcrashSystem.oeCreateSystemAndEnvironment...");
		
		/*
		 * 	PostF 1:
		 * 
		 *  the ctState instance is initialised with
		 *  the integer 1 for the crisis and alert counters used for their	identifications,
		 *  clock = current time 
		 *  the crisis reminder period is set to 300 seconds, 
		 *  the maximum crisis reminder period	is fixed to 1200 seconds (i.e. 20 minutes) and 
		 *  the system is considered in a started state.
		 *  aVpLastReminder = current time
		 *  Those predicates must be satisfied first since all the other depend on the existence of a
		 *  ctState instance !
			
		*/

		int nextValueForAlertID = DbAlerts.getMaxAlertID() + 1;
		
		DtInteger aNextValueForAlertID = new DtInteger(new PtInteger(
				nextValueForAlertID));

	    int nextValueForCrisisID = DbCrises.getMaxCrisisID() + 1;
		
		DtInteger aNextValueForCrisisID = new DtInteger(new PtInteger(
				nextValueForCrisisID));

		DtDateAndTime aClock = ICrashUtils.getCurrentDateAndTime();

		DtSecond aCrisisReminderPeriod = new DtSecond(new PtInteger(300)); 
		DtSecond aMaxCrisisReminderPeriod = new DtSecond(
				new PtInteger(1200));
		PtBoolean aVpStarted = new PtBoolean(true);

		// attention: temporary NON COMPLIANCE!!!
		// the spec says that init method should assign values to a new instance of ctState
		// but here instead it assigns values to existing static variables of ONE and ONLY STATIC "instance" of ctState
		// I'll just leave it like that for the moment, since I am now relying to statics for that
		// but I'll have to find a way to make it compliant later!!!
		CtState.init(aNextValueForAlertID, aNextValueForCrisisID, aClock,
				aCrisisReminderPeriod, aMaxCrisisReminderPeriod, aClock,
				aVpStarted);
		
		/*	ENV
		PostF 3 the environment for communication company actors, in the post state, is made of
		AqtyComCompanies instances allowing for receiving and sending messages to humans.
		*/

		IcrashEnvironment env = IcrashEnvironment.getInstance();
		
		for (int i = 0; i < aQtyComCompanies.getValue(); i++) {
			String aActComCompanyName = ComCompaniesInLux.values[i].name();
			DbComCompanies.insertComCompany(i + "", aActComCompanyName);
			ActComCompany aActComCompany = new ActComCompany(
					aActComCompanyName);
			env.setComCompany(aActComCompanyName, aActComCompany);
			IcrashSystem.cmpSystemActComCompany.put(aActComCompanyName, aActComCompany);
		} 
		
		/*	ENV
		PostF 4 the environment for administrator actors, in the post state, is made of one instance.
		*/
		String adminName = AdminActors.values[0].name();
		ActAdministrator aActAdministrator = new ActAdministrator(new DtLogin(new PtString(adminName)));
		env.setActAdministrator(new DtLogin(new PtString(adminName)), aActAdministrator);
		
		/* ENV
		PostF 5 the environment for activator actors, in the post state, is made of one instance allowing for automatic
		message sending based on current systemâ€™s and environment stateâ€™.
		*/
		ActActivator aActActivator = new ActActivator();
		env.setActActivator(aActActivator);
		
		/*
		PostF 6 the set of ctAdministrator instances at post is made of one instance initialized with 
		’icrashadmin’ (resp. ’7WXC1359’) for login (resp. password) values.
		*/
		CtAdministrator ctAdmin = new CtAdministrator();
		DtLogin aLogin = new DtLogin(new PtString(adminName));
		DtPassword aPwd = new DtPassword(new PtString("7WXC1359"));
		ctAdmin.init(aLogin, aPwd);
		
		/*
		PostF 7 the association between ctAdministrator and actAdministrator is made of 
		one couple made of the jointly specified instances.
		*/
		assCtAuthenticatedActAuthenticated.put(ctAdmin, aActAdministrator);
		
		//set up Messir compositions		
		cmpSystemCtAdministrator.put(ctAdmin.login, ctAdmin);
		cmpSystemCtAuthenticated.put(ctAdmin.login,	ctAdmin);

		// initialize relationships taking information from the DB
		cmpSystemCtAlert = DbAlerts.getSystemAlerts();
		cmpSystemCtCrisis = DbCrises.getSystemCrises();
		cmpSystemCtHuman = DbHumans.getSystemHumans();
		Hashtable<String, CtCoordinator> cmpSystemCtCoordinator = DbCoordinators.getSystemCoordinators();
		for(CtCoordinator ctCoord: cmpSystemCtCoordinator.values()){
			cmpSystemCtAuthenticated.put(ctCoord.login, ctCoord);
			ActCoordinator actCoord = new ActCoordinator(ctCoord.login);
			env.setActCoordinator(ctCoord.login, actCoord);
			assCtAuthenticatedActAuthenticated.put(ctCoord, actCoord);
			assCtCoordinatorActCoordinator.put(ctCoord, actCoord);
		}
		
		assCtAlertCtCrisis = DbAlerts.getAssCtAlertCtCrisis();
		assCtAlertCtHuman = DbAlerts.getAssCtAlertCtHuman();
		assCtCrisisCtCoordinator = DbCrises.getAssCtCrisisCtCoordinator();
		assCtHumanActComCompany = DbHumans.getAssCtHumanActComCompany(env.getActComCompanies());
		
		/*
		*Creating a thread to auto check if handling delay has passed and if so run oeSollicitateCrisisHandling 
		*/
		Thread checkingForDelayPassed = new Thread(){
			public void run(){
				while(true){
					try{
						hasHandlingDelayPassed();
						log.debug("Something has passed handling delay, running oeSollicitateCrisisHandling");
						oeSollicitateCrisisHandling();
						Thread.sleep(50000);
					}
					catch (Exception e){
						try {
							Thread.sleep(0);
						} catch (InterruptedException e1) {
							//DO NOTHING
						}
					}
				}
			}
		};
		checkingForDelayPassed.start(); 
		
		// TODO: real error checking! 
		return new PtBoolean(true);
	}
	
	// actAuthenticated Actor
	public PtBoolean oeLogin(DtLogin aDtLogin, DtPassword aDtPassword) throws Exception {
			
		// PreP 1 The system is started 
		if (!isSystemStartedCheck())
			return new PtBoolean(false);
			
		// check whether the credentials corresponds to an existing user
		// this is done by checking if there exists an instance with
		// such credential in the ctAuthenticatedInstances data structure
		// ctAuthenticatedInstance = cmpSystemCtAuthenticated.get(aDtLogin);
		ctAuthenticatedInstance = getCtAuthenticated(currentRequestingAuthenticatedActor);	
		
		if (ctAuthenticatedInstance != null) {

			// PreP 2
			if(ctAuthenticatedInstance.vpIsLogged.getValue()) {
				log.debug("oeLogin: The actor is already logged in !");
				return new PtBoolean(false);
			}	
			
			PtBoolean pwdCheck = ctAuthenticatedInstance.pwd.eq(aDtPassword);
			if(pwdCheck.getValue()) {
					
				// PostP 1 - auth info is correct, so the actor will now be known as logged in
				currentRequestingAuthenticatedActor = assCtAuthenticatedActAuthenticated.get(ctAuthenticatedInstance);
				ctAuthenticatedInstance.vpIsLogged = new PtBoolean(true);
			
				// PostF 1 - the actor gave correct data
				PtString aMessage = new PtString("You are logged ! Welcome ...");
				currentRequestingAuthenticatedActor.ieMessage(aMessage);
				return new PtBoolean(true);
			}
		}	
			
		// PostF 1 - the actor gave incorrect data
		PtString aMessage = new PtString(
				"Wrong identification information! Please try again ...");
		currentRequestingAuthenticatedActor.ieMessage(aMessage);

		IcrashEnvironment env = IcrashEnvironment.getInstance();
			
		//notify to all administrators that exist in the environment
		for (DtLogin adminKey : env.getAdministrators().keySet()) {
			ActAdministrator admin = env.getActAdministrator(adminKey);
			
			log.debug("INSIDE oeLogin CHECK, actor's UI is "+admin.getActorUI());
			
			aMessage = new PtString("Intrusion tentative !");
			admin.ieMessage(aMessage);
		}
		return new PtBoolean(false);
	}
		
	public PtBoolean oeLogout() throws Exception {

		// PreP 1 The system is started 
		if (!isSystemStartedCheck())
			return new PtBoolean(false);
		
		// PreP 2 The actor is currently logged in
		if (!isActorLoggedInCheck()) {
			log.debug("Inside oeLogout: the actor is not logged in, so epic fail here");
			return new PtBoolean(false);
		}
		
		CtAuthenticated ctAuth = getCtAuthenticated(currentRequestingAuthenticatedActor);
		log.debug("oeLogout: current Associated CtAuthenticated Instance is " + ctAuth);
		
		if (ctAuth != null) {
			DtLogin key = ctAuth.login;
			CtAuthenticated user = cmpSystemCtAuthenticated.get(key);
			
			// PostP 1
			user.vpIsLogged = new PtBoolean(false);
			
			// PostF 1
			PtString aMessage = new PtString("You are logged out ! Good Bye ...");
			currentRequestingAuthenticatedActor.ieMessage(aMessage);
		}
		currentRequestingAuthenticatedActor = new ActAuthenticated(new DtLogin(new PtString("")));
		
		return new PtBoolean(true);
	}
	
	public void setCurrentRequestingAuthenticatedActor(ActAuthenticated aActAuthenticated) {
		currentRequestingAuthenticatedActor = aActAuthenticated;
	}
	public ActAuthenticated getCurrentRequestingAuthenticatedActor(){
		return currentRequestingAuthenticatedActor;
	}
	
	public List<ActAdministrator> getAllActAdministrators() {
		List<ActAdministrator> listAdmins = new ArrayList<ActAdministrator>();
		for (CtAuthenticated ctAuth : assCtAuthenticatedActAuthenticated.
				keySet()) {
			if (ctAuth instanceof CtAdministrator)
				listAdmins
						.add((ActAdministrator) assCtAuthenticatedActAuthenticated
								.get(ctAuth));
		}
		return listAdmins;
	}
	
	public ArrayList<CtAdministrator> getAllCtAdministrators(){
		ArrayList<CtAdministrator> listAdmins = new ArrayList<CtAdministrator>();
		for (CtAuthenticated ctAuth : cmpSystemCtAuthenticated
				.values()) {
			if (ctAuth instanceof CtAdministrator)
				listAdmins.add((CtAdministrator)ctAuth);
		}
		return listAdmins;
	}
	
	public Boolean isSystemStartedCheck() throws Exception {
		
		Boolean systemIsStarted = CtState.vpStarted.getValue(); 
		
		if (!systemIsStarted) {
			log.debug("Error, the system is not started!");
			return false;
		
		} else if (systemIsStarted) {
			return true;
		
		} else {
			throw new Exception("Houston, we have a problem - CtState.vpIsLogged is null");
		} 
	}
	
	public Boolean isActorLoggedInCheck() {
		
		if (currentRequestingAuthenticatedActor instanceof ActAdministrator) {
			
			ActAdministrator theActAdministrator = (ActAdministrator) currentRequestingAuthenticatedActor;
			CtAuthenticated ctAuthenticatedInstance = cmpSystemCtAuthenticated
					.get(theActAdministrator.getName());
			
			if (ctAuthenticatedInstance == null || !ctAuthenticatedInstance.vpIsLogged.getValue()) {
				log.debug("The actor is currently not logged in!");
				return false;
			}

			else 
				return true;
			
		} else if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
			
			ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
			CtAuthenticated ctAuthenticatedInstance = cmpSystemCtAuthenticated
					.get(theActCoordinator.getName());
			
			if (ctAuthenticatedInstance == null || !ctAuthenticatedInstance.vpIsLogged.getValue()) {
				log.debug("The actor is currently not logged in!");
				return false;
			}
			
			else
				return true;
		} 
		
		return false;
	}
	
	public Boolean isAdminLoggedInCheck() {
		return true;
	}
		
	public CtAuthenticated getCtAuthenticated(ActAuthenticated aActAuthenticated) {

		String ActAuthname = aActAuthenticated.getName().value.getValue();
		for (CtAuthenticated ctAuth : assCtAuthenticatedActAuthenticated.keySet()) {
			ActAuthenticated currActAuth = assCtAuthenticatedActAuthenticated.get(ctAuth);
			if (currActAuth.getName().value.getValue().equals(ActAuthname) && !currActAuth.getName().value.getValue().isEmpty())
			return ctAuth;
		}
		return null;
	}
	
	private List<CtAlert> getAlertsByCrisis(CtCrisis aCtCrisis) {

		List<CtAlert> listAlerts = new ArrayList<CtAlert>();

		for (CtAlert ctAlert : assCtAlertCtCrisis.keySet()) {
			if (assCtAlertCtCrisis.get(ctAlert).id.value.getValue().equals(
					aCtCrisis.id.value.getValue()))
				listAlerts.add(ctAlert);
		}

		return listAlerts;
	}
	
	public PtBoolean oeSetClock(DtDateAndTime aCurrentClock) {
	
		try {
			// PreP 1
			if (!isSystemStartedCheck())
				return new PtBoolean(false);
			
			// PreP 1
			if (aCurrentClock.toSecondsQty().getValue() <= CtState.clock.toSecondsQty().getValue())
			{
				log.error("The clock of " + aCurrentClock.toString() + " is less than the current clock of "+ CtState.clock.toString());
				return new PtBoolean(false);
			}
			
			// PostF 1
			CtState.clock = aCurrentClock;
			CtState.clock.show();
			return new PtBoolean(true);
			
		} catch (Exception ex) {
			log.error("Exception in oeSetClock..." + ex);
			return new PtBoolean(false);
		}
	}
	
	public CtAuthenticated getCtCoordinator(DtCoordinatorID aDtCoordinatorID) {

		for (CtAuthenticated ctAuth : assCtAuthenticatedActAuthenticated
				.keySet()) {
			if (ctAuth instanceof CtCoordinator) {
				PtBoolean res = ((CtCoordinator) ctAuth).id
						.eq(aDtCoordinatorID);
				if (res.getValue())
					return ctAuth;
			}
		}
		return null;
	}

	public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID, DtLogin aDtLogin, DtPassword aDtPassword) throws Exception {

		// PreP 1
		if (!isSystemStartedCheck()) {
			log.debug("oeAddCoordinator: System is not started");
			return new PtBoolean(false);
		}
		// PreP 2
		if (!isAdminLoggedInCheck()) {
			log.debug("oeAddCoordinator: Admin is not logged in");
			return new PtBoolean(false);
		}
		IcrashEnvironment env = IcrashEnvironment.getInstance();

		// PostF 1
		ActCoordinator actCoordinator = new ActCoordinator(aDtLogin);
		env.setActCoordinator(aDtLogin, actCoordinator);

		// PostF 2
		CtCoordinator ctCoordinator = new CtCoordinator();
		ctCoordinator.init(aDtCoordinatorID, aDtLogin, aDtPassword);

		// DEBUG
		DbCoordinators.insertCoordinator(ctCoordinator);
		
		// PostF 3 and PostF 4 done at once w.r.t. our implementation
		assCtAuthenticatedActAuthenticated.put(ctCoordinator, actCoordinator);

		// Update composition relationships
		cmpSystemCtAuthenticated.put(aDtLogin, ctCoordinator);
		assCtCoordinatorActCoordinator.put(ctCoordinator, actCoordinator);

		// PostF 5
		ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
		actAdmin.ieCoordinatorAdded();
		
		return new PtBoolean(true);
	}
	
	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID) throws Exception {
	
		// PreP 1
		if (!isSystemStartedCheck()) {
			log.info("Inside oeDeleteCoordinator: the system is not started! Aborting...");
			return new PtBoolean(false);
		}
		
		// PreP 2
		if (!isAdminLoggedInCheck()) {
			log.info("Inside oeDeleteCoordinator: the actor is not logged in! Aborting...");
			return new PtBoolean(false);
		}
		
		// PreF 1
		CtAuthenticated ctAuth = getCtCoordinator(aDtCoordinatorID);
		
		if (ctAuth != null && ctAuth instanceof CtCoordinator) {
			CtCoordinator aCtCoordinator = (CtCoordinator)ctAuth;

			DbCoordinators.deleteCoordinator(aCtCoordinator);
				
			// PostF 1
			assCtAuthenticatedActAuthenticated.remove(ctAuth);
			cmpSystemCtAuthenticated.remove(ctAuth.login);
			ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
			
			// PostF 2
			actAdmin.ieCoordinatorDeleted();
			return new PtBoolean(true);
		}
		return new PtBoolean(false);
	}

	public void setCurrentConnectedComCompany(ActComCompany aComCompany) {
		currentConnectedComCompany = aComCompany;
	}

	public PtBoolean oeAlert(EtHumanKind aEtHumanKind, DtDate aDtDate, DtTime aDtTime,
			DtPhoneNumber aDtPhoneNumber, DtGPSLocation aDtGPSLocation, DtComment aDtComment) {
		
		try {
			
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeAlert: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
			
			DtDateAndTime aInstant = new DtDateAndTime(aDtDate, aDtTime);
			
			int nextValueForAlertID_at_pre = CtState.nextValueForAlertID.value
					.getValue();
			int nextValueForCrisisID_at_pre = CtState.nextValueForCrisisID.value
					.getValue();
			
			// PostF 1				
			CtState.nextValueForAlertID.value = new PtInteger(
					CtState.nextValueForAlertID.value.getValue() + 1);
	
			// PostF 2
			CtAlert aCtAlert = new CtAlert();
			DtAlertID aId = new DtAlertID(new PtString(""
					+ nextValueForAlertID_at_pre));
			EtAlertStatus aStatus = EtAlertStatus.pending;
			aCtAlert.init(aId, aStatus, aDtGPSLocation, aInstant, aDtComment);

			// DB: insert alert in the database
			DbAlerts.insertAlert(aCtAlert);
	
			// PostF 3
			boolean existsNear = false;
			CtCrisis aCtCrisis = new CtCrisis();
			
			//check if there already exists a reported Alert that is closer than 100 m. 
			for (CtAlert existingCtAlert : assCtAlertCtCrisis.keySet()) {
				existsNear = existingCtAlert.location.isNearTo(
						aDtGPSLocation.latitude, aDtGPSLocation.longitude)
						.getValue();
				if (existsNear) {
					aCtCrisis = assCtAlertCtCrisis.get(existingCtAlert);
					break;
				}
			}
	
			//if there no exits a near alert, then we need to initialise the just created crisis instance
			if (!existsNear) {
				DtCrisisID acId = new DtCrisisID(new PtString(""
						+ nextValueForCrisisID_at_pre));
				CtState.nextValueForCrisisID.value = new PtInteger(
						CtState.nextValueForCrisisID.value.getValue() + 1);
				EtCrisisType acType = EtCrisisType.small;
				EtCrisisStatus acStatus = EtCrisisStatus.pending;
				DtComment acComment = new DtComment(new PtString(
						"no report defined, yet"));
				aCtCrisis.init(acId, acType, acStatus, aDtGPSLocation, aInstant,
						new DtFamilyPhoneNumbers(), acComment);
	
				//DB: insert crisis in the database
				DbCrises.insertCrisis(aCtCrisis);
				
				//update Messir composition
				cmpSystemCtCrisis.put(aCtCrisis.id.value.getValue(), aCtCrisis);
	
			}
	
			//PostF4
			assCtAlertCtCrisis.put(aCtAlert, aCtCrisis);
			//DB: update just inserted alert with its corresponding associated (near) crisis
			DbAlerts.bindAlertCrisis(aCtAlert, aCtCrisis);
	
			//PostF5
			CtHuman aCtHuman = new CtHuman();
			boolean existsHuman = false;
	
			//check if there already exists a human who reported an Alert 
			for (CtHuman existingHuman : assCtHumanActComCompany.keySet()) {
				String exPhoneNumber = existingHuman.id.value.getValue();
	
				if (exPhoneNumber.equals(aDtPhoneNumber.value.getValue())) {
					aCtHuman = existingHuman;
					existsHuman = true;
					break;
				}
			}
	
			//if there no exists human, then we need (1) to initialise the just created instance
			// and (2) to add it to the assCtHumanActComCompany relationship 
			if (!existsHuman) {
				aCtHuman.init(aDtPhoneNumber, aEtHumanKind);
				assCtHumanActComCompany.put(aCtHuman, currentConnectedComCompany);
	
				//update Messir composition
				cmpSystemCtHuman.put(aCtHuman.id.value.getValue(), aCtHuman);
	
				//DB: get currentConnectedComCompany's id
				String idComCompany = DbComCompanies
						.getComCompanyID(currentConnectedComCompany.getName());
	
				//DB: insert human in the database
				DbHumans.insertHuman(aCtHuman, idComCompany);
	
			}
	
			// PostF 6
			DtSMS sms = new DtSMS(new PtString(	"Your alert has been registered. We will handle it and keep you informed"));
			currentConnectedComCompany.ieSmsSend(aDtPhoneNumber, sms);

			// bind human with alert
			assCtAlertCtHuman.put(aCtAlert, aCtHuman);
			//DB: update just inserted alert with reporting human
			DbAlerts.bindAlertHuman(aCtAlert, aCtHuman);
			//update Messir composition
			cmpSystemCtAlert.put(aCtAlert.id.value.getValue(), aCtAlert);
			return new PtBoolean(true);
		}
		catch(Exception e){
			log.error("Exception in oeAlert... " + e);
			e.printStackTrace();
		}
		return new PtBoolean(false);
	}

	public CtCoordinator getRandomCtCoordinator() {
		
		List<CtCoordinator> collCtCoor = assCtCoordinatorActCoordinator.keySet().stream().filter(t -> t != null).collect(Collectors.toList());
		
		int max = collCtCoor.size();
		int min = 0;
		int randomNum = 0;

		if (max > 1) {
			Random rand = new Random();
			// nextInt is exclusive of max, and inclusive of min,
			randomNum = rand.nextInt(max - min) + min;
		}
		if (max != 0){
			return collCtCoor.get(randomNum);
		}
		return null;
	}
	
	public ActCoordinator getActCoordinator(CtCoordinator keyCtCoordinator) {
		return assCtCoordinatorActCoordinator.get(keyCtCoordinator);
	}

	public void bindCtCrisisCtCoordinator(CtCrisis ctCrisis, CtCoordinator theCoordinator) {
		
	}

	public ActComCompany getActComCompany(CtHuman aHuman) {
		return assCtHumanActComCompany.get(aHuman);
	}

	public PtBoolean oeValidateAlert(DtAlertID aDtAlertID) {
		try {
			
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeValidateAlert: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
			
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			CtAlert theAlert = cmpSystemCtAlert.get(aDtAlertID.value.getValue());
			
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
					ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
					// PostF 1
					theAlert.status = EtAlertStatus.valid;
					DbAlerts.updateAlert(theAlert);
					PtString aMessage = new PtString("The alert "
							//+ "with ID '"
							//+ aDtAlertID.value.getValue()
							//+ "' "
							+ "is now declared as valid !");
					theActCoordinator.ieMessage(aMessage);
					return new PtBoolean(true);
				}
			}
		
			catch(Exception e){
				log.error("Exception in oeValidateAlert..." + e);
			}
			return new PtBoolean(false);
		}

	public PtBoolean oeInvalidateAlert(DtAlertID aDtAlertID) {
	
		try{
			
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeInvalidateAlert: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
						
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			CtAlert theAlert = cmpSystemCtAlert.get(aDtAlertID.value.getValue());
				if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
					ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
					//PostF1
					theAlert.status = EtAlertStatus.invalid;
					DbAlerts.updateAlert(theAlert);
					PtString aMessage = new PtString("The alert "
							//+ "with ID '"
							//+ aDtAlertID.value.getValue()
							//+ "' "
							+ "is now declared as invalid !");
					theActCoordinator.ieMessage(aMessage);
		
					return new PtBoolean(true);
				}
			}
			catch(Exception e){
				log.error("Exception in oeInvalidateAlert..." + e);
			}
			return new PtBoolean(false);
		}	
	
	public PtBoolean oeSetCrisisType(DtCrisisID aDtCrisisID, EtCrisisType aEtCrisisType) {
		try{
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeSetCrisisType: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
									
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;

				// PostF 1
				theCrisis.type = aEtCrisisType;
				DbCrises.updateCrisis(theCrisis);
				PtString aMessage = new PtString("The crisis with ID '"
						+ aDtCrisisID.value.getValue() + "' is now of type '"
						+ aEtCrisisType.toString() + "' !");
				theActCoordinator.ieMessage(aMessage);
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeSetCrisisType..." + e);
		}
		return new PtBoolean(false);
	}

	public PtBoolean oeSetCrisisStatus(DtCrisisID aDtCrisisID, EtCrisisStatus aEtCrisisStatus) {
		try {

			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeSetCrisisStatus: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
												
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				
				// PostF 1
				theCrisis.status = aEtCrisisStatus;
				DbCrises.updateCrisis(theCrisis);
				PtString aMessage = new PtString("The crisis status has been updated !");
				theActCoordinator.ieMessage(aMessage);
	
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeSetCrisisStatus..." + e);
		}
		return new PtBoolean(false);
	}

	public PtBoolean oeSetCrisisHandler(DtCrisisID aDtCrisisID) {
		
		try {
			
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeSetCrisisHandler: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
															
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value
					.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				CtCoordinator theCtCoordinator = (CtCoordinator) getCtAuthenticated(theActCoordinator);
				
				CtCoordinator theCtCoordinatorAtPre = assCtCrisisCtCoordinator.get(theCrisis);
			
			/*	if (theCtCoordinatorAtPre == null)
					log.debug("theCtCoordinatorAtPre is none");
				else
					log.debug("theCtCoordinatorAtPre is "
								+ assCtCrisisCtCoordinator.get(theCrisis).toString());
			*/
				//PostF1
				theCrisis.status = EtCrisisStatus.handled;
				DbCrises.updateCrisis(theCrisis);
				
				assCtCrisisCtCoordinator.put(theCrisis, theCtCoordinator);
				DbCrises.bindCrisisCoordinator(theCrisis, theCtCoordinator);
				PtString aMessage = new PtString(
						"You are now considered as handling the crisis !");
				theActCoordinator.ieMessage(aMessage);
	
				//PostF2
				for (CtAlert theAlert : getAlertsByCrisis(theCrisis)) {
					theAlert.isSentToCoordinator(theActCoordinator);
				}
				// PostF 3
				if (theCtCoordinatorAtPre != null) {
					ActCoordinator theActCoordinatorAtPre = (ActCoordinator) assCtAuthenticatedActAuthenticated
							.get(theCtCoordinatorAtPre);
					log.info("One of the crisis you were handling is now handled by one of your colleagues!");
					PtString aMessage2 = new PtString(
							"One of the crisis you were handling is now handled by one of your colleagues!");
					theActCoordinatorAtPre.ieMessage(aMessage2);
				}
	
				// PostF 4
				for (CtAlert theAlert : getAlertsByCrisis(theCrisis))
				{
					Enumeration<CtAlert> enumKey = assCtAlertCtHuman.keys();
					while(enumKey.hasMoreElements()){
						CtAlert aCtAlert = enumKey.nextElement();
						if (aCtAlert.equals(theAlert)){
							CtHuman theHuman = assCtAlertCtHuman.get(aCtAlert);
							if (!theHuman.isAcknowledged().getValue())
								log.error("Unable to message a communication company about the crisis update");
						}
					}
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeSetCrisisHandler..." + e);
		}

		return new PtBoolean(false);
	}

	public PtBoolean oeReportOnCrisis(DtCrisisID aDtCrisisID, DtComment aDtComment, PtBoolean aDtCrisisSendSmsFamily) {
		try {
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeReportOnCrisis: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
																	
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;

				// PostF 1
				theCrisis.comment = aDtComment;
				DbCrises.updateCrisis(theCrisis);
				PtString aMessage = new PtString("The crisis comment has been updated !");
				theActCoordinator.ieMessage(aMessage);

				if (aDtCrisisSendSmsFamily.getValue() == true)
				{
					for (DtPhoneNumber familyNumber: theCrisis.familyNumbers.listDtPhoneNumbers)
					{
						//PostF2
						CtHuman aCtFamilyHuman = new CtHuman();
						boolean existsHuman = false;
				
						//check if there already exists a human with such phone number
						for (CtHuman existingHuman : assCtHumanActComCompany.keySet()) {
							String exPhoneNumber = existingHuman.id.value.getValue();
				
							if (exPhoneNumber.equals(familyNumber.value.getValue())) {
								aCtFamilyHuman = existingHuman;
								existsHuman = true;
								break;
							}
						}
			
						//if there no exists human, then we need (1) to initialise the just created instance
						// and (2) to add it to the assCtHumanActComCompany relationship 
				
						if (!existsHuman) {
							EtHumanKind aEtHumanKind = EtHumanKind.familyMember;
							aCtFamilyHuman.init(familyNumber, aEtHumanKind);
							assCtHumanActComCompany.put(aCtFamilyHuman, currentConnectedComCompany);
				
							//update Messir composition
							cmpSystemCtHuman.put(aCtFamilyHuman.id.value.getValue(), aCtFamilyHuman);
				
							//DB: get currentConnectedComCompany's id
							String idComCompany = DbComCompanies
									.getComCompanyID(currentConnectedComCompany.getName());
				
							//DB: insert human in the database
							DbHumans.insertHuman(aCtFamilyHuman, idComCompany);
				
						}
						//PostF3
						ActComCompany currentActComCompany = assCtHumanActComCompany.get(aCtFamilyHuman);
						DtSMS sms = new DtSMS(new PtString("The crisis report changed! New Report: " + theCrisis.comment.value.getValue()));
						currentActComCompany.ieSmsSend(familyNumber, sms);
					}
				}

				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeReportOnCrisis..." + e);
		}
		return new PtBoolean(true);
	}

	public PtBoolean oeAddFamilyNumbersOnCrisis(DtCrisisID aDtCrisisID,  DtFamilyPhoneNumbers aDtFamilyPhoneNumbers) {
		try {
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeAddFamilyNumberOnCrisis: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
																	
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value.getValue());
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;

				// PostF 1
				theCrisis.familyNumbers = aDtFamilyPhoneNumbers;
				DbCrises.updateCrisis(theCrisis);
				PtString aMessage = new PtString("The crisis family phone number has been added!");
				theActCoordinator.ieMessage(aMessage);

				//PostF2
				Iterator<Entry<String, DtPhoneNumber>> it = theCrisis.familyNumbers.assComCompanyViewNamesDtPhoneNumber.entrySet().iterator();
				while (it.hasNext())
				{
					Entry<String, DtPhoneNumber> entry = (Entry<String, DtPhoneNumber>) it.next();
					String comCompanyViewName = entry.getKey();
					DtPhoneNumber familyNumber = entry.getValue();

					
					CtHuman aCtFamilyHuman = new CtHuman();
					boolean existsHuman = false;
	
					//check if there already exists a human with such phone number
					for (CtHuman existingHuman : assCtHumanActComCompany.keySet()) {
						String exPhoneNumber = existingHuman.id.value.getValue();
						if (exPhoneNumber.equals(familyNumber.value.getValue())) {
							existsHuman = true;
							break;
						}
					}

					//if there no exists human, then we need (1) to initialise the just created instance
					// and (2) to add it to the assCtHumanActComCompany relationship 
					if (!existsHuman) {
						EtHumanKind aEtHumanKind = EtHumanKind.familyMember;
						aCtFamilyHuman.init(familyNumber, aEtHumanKind);
						ActComCompany familyComCompany = cmpSystemActComCompany.get(DbComCompanies.getComCompanyName(comCompanyViewName));
						if (familyComCompany != null) {
							assCtHumanActComCompany.put(aCtFamilyHuman, familyComCompany);
						}
						else {
							familyComCompany = currentConnectedComCompany;
							assCtHumanActComCompany.put(aCtFamilyHuman, currentConnectedComCompany);
						}

						//update Messir composition
						cmpSystemCtHuman.put(aCtFamilyHuman.id.value.getValue(), aCtFamilyHuman);

						//DB: get currentConnectedComCompany's id
						String idComCompany = DbComCompanies
								.getComCompanyID(familyComCompany.getName());
			
						//DB: insert human in the database
						DbHumans.insertHuman(aCtFamilyHuman, idComCompany);
					}
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeAddFamilyNumberOnCrisis..." + e);
		}
		return new PtBoolean(true);
	}

	public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) {
		try{
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeGetCrisisSet: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
																				
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
			
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator aActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
				
				// erase this Coordinator's crises table datasource, before populating it with new data
				// i.e. with new (actual) set of alerts (of a given status)
				aActCoordinator.getCrisesContainer().removeAllItems();
				
				//go through all existing crises
				for (String crisisKey : cmpSystemCtCrisis.keySet()) {
					CtCrisis crisis = cmpSystemCtCrisis.get(crisisKey);
					if (crisis.status.toString().equals(aEtCrisisStatus.toString())) {
						
						//PostF1
						ArrayList<String> familyNumbers = new ArrayList<String>();
						for (DtPhoneNumber phone: crisis.familyNumbers.listDtPhoneNumbers) { 
							familyNumbers.add(phone.toString());
						} 

						aActCoordinator.getCrisesContainer().addBean(new CrisisBean(
								Integer.parseInt(crisis.id.toString()),
								crisis.instant.date.toString(),
								crisis.instant.time.toString(),
								crisis.type.toString(),
								crisis.location.longitude.value.getValue(),
								crisis.location.latitude.value.getValue(),
								familyNumbers,
								crisis.comment.toString(),
								crisis.status.toString()));
						
						crisis.isSentToCoordinator(aActCoordinator);
					}	
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeGetCrisisSet..." + e);
			e.printStackTrace();
		}
		return new PtBoolean(false);
	}

	public PtBoolean oeGetAlertsSet(EtAlertStatus aEtAlertStatus) {
		try{
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeGetAlertsSet: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
																							
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
						
			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator aActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;
	
				// erase this Coordinator's alerts table datasource, before populating it with new data
				// i.e. with new (actual) set of alerts (of a given status)
				aActCoordinator.getAlertsContainer().removeAllItems();
				
				// PostF 1
				for (String alertKey : cmpSystemCtAlert.keySet()) {
					CtAlert theCtAlert = cmpSystemCtAlert.get(alertKey);
					if (theCtAlert.status.equals(aEtAlertStatus)) {
						
						aActCoordinator.getAlertsContainer().addBean(new AlertBean(
								Integer.parseInt(theCtAlert.id.toString()),
								theCtAlert.instant.date.toString(),
								theCtAlert.instant.time.toString(),
								theCtAlert.location.longitude.value.getValue(),
								theCtAlert.location.latitude.value.getValue(),
								theCtAlert.comment.toString(),
								theCtAlert.status.toString()));
						
						theCtAlert.isSentToCoordinator(aActCoordinator);
					}	
				}
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeGetAlertsSet..." + e);
		}
		return new PtBoolean(false);
	}

	public PtBoolean oeCloseCrisis(DtCrisisID aDtCrisisID) {
		try{
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeCloseCrisis: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
																									
			// PreP 2
			if (!isActorLoggedInCheck())
				return new PtBoolean(false);
						
			CtCrisis theCrisis = cmpSystemCtCrisis.get(aDtCrisisID.value.getValue());

			if (currentRequestingAuthenticatedActor instanceof ActCoordinator) {
				ActCoordinator theActCoordinator = (ActCoordinator) currentRequestingAuthenticatedActor;

				// PostF 1
				theCrisis.status = EtCrisisStatus.closed;
				DbCrises.updateCrisis(theCrisis);

				// PostF 2
				assCtCrisisCtCoordinator.remove(theCrisis);
			
				// PostF 3
				Collection<CtAlert> keys = assCtAlertCtCrisis.keySet();
				CtAlert[] alertkeys = keys.toArray(new CtAlert[0]);
				for (int i = 0; i < alertkeys.length; i++) {
					CtAlert theAlert = alertkeys[i];
					if (assCtAlertCtCrisis.get(theAlert) == theCrisis) {
						DbAlerts.deleteAlert(theAlert);
						assCtAlertCtCrisis.remove(theAlert);
						cmpSystemCtAlert.remove(theAlert.id.value.getClass());
						if (!assCtAlertCtCrisis.contains(theCrisis))
							break;
					}
				}
	
				// PostF 4	
				PtString aMessage = new PtString("The crisis "
						//+ "with ID '"
						//+ aDtCrisisID.value.getValue() + "' "
								+ "is now closed !");
				theActCoordinator.ieMessage(aMessage);
				
				return new PtBoolean(true);
			}
		}
		catch (Exception e){
			log.error("Exception in oeCloseCrisis..." + e);
		}

		return new PtBoolean(true);
	}
	
	public ArrayList<CtCrisis> getAllCtCrises() {
		ArrayList<CtCrisis> result = new ArrayList<CtCrisis>();
		if (cmpSystemCtCrisis != null){
			for(CtCrisis crisis : cmpSystemCtCrisis.values())
				result.add(crisis);
		}
		return result;
	}

	public PtBoolean oeSollicitateCrisisHandling() throws RemoteException {
		try {
			// PreP 1
			if (!isSystemStartedCheck()) {
				log.debug("Inside oeSollicitateCrisisHandling: the system is not started! Aborting...");
				return new PtBoolean(false);
			}
			
			// PreP 2
			hasHandlingDelayPassed();
			
			//go through all existing crises
			for (String crisisKey : cmpSystemCtCrisis.keySet()) {
				CtCrisis crisis = cmpSystemCtCrisis.get(crisisKey);
				
				// PostF 1
				if (crisis.maxHandlingDelayPassed().getValue()){
					crisis.isAllocatedIfPossible();
					DbCrises.updateCrisis(crisis);
				}

				// PostF 2
				else if (crisis.handlingDelayPassed().getValue()) {
					PtString aMessageForCrisisHandlers = new PtString(
							"There are alerts pending since more than the defined delay. Please REACT !");
					//notify all ActAuthenticated about crises not being handled
					for(ActAuthenticated actAuth : assCtAuthenticatedActAuthenticated.values())
						actAuth.ieMessage(aMessageForCrisisHandlers);
				}
			}
		} catch (Exception ex) {
			log.error("Exception in oeSollicitateCrisisHandling..." + ex);
			return new PtBoolean(false);
		}

		// PostP 1
		CtState.vpLastReminder = CtState.clock;
		return new PtBoolean(true);
	}
	
	/**
	 * Creates a wrapper around an exception to prevent Java compile errors when attempting to catch an exception inside a lambda expression.
	 *
	 * @param e The exception to throw to the surrounding method
	 * @return Returns a surrounding runtimeexception for the exception of e
	 */
    private static RuntimeException runtime(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException)e;
        }
        return new RuntimeException(e);
    }
	
	/**
	 * If an exception is thrown inside a lambda stream, it must be caught during the actual stream. This is a method to catch said issue
	 * and then propagate it up to the calling method. See here for more information: http://stackoverflow.com/questions/19757300/java-8-lambda-streams-filter-by-method-with-exception
	 *
	 * @param <V> the value type
	 * @param callable the lambda stream that will cause the exception. It's called like this a -&gt; propagate(a::isActive) where isActive is a method inside the class of a
	 * @return Returns the result if completed, otherwise it will throw the exception up to the calling method
	 */
    private static <V> V propagate(Callable<V> callable){
        try {
            return callable.call();
        } catch (Exception e) {
            throw runtime(e);
        }
    }
	
	/**
	 * A check to see if the handling delay has passed the standard set in CtState.
	 *
	 * @throws Exception the exception that was encountered when trying to run this method
	 */
	private synchronized void hasHandlingDelayPassed() throws Exception {
		//Interesting read: http://stackoverflow.com/questions/19757300/java-8-lambda-streams-filter-by-method-with-exception
		if (cmpSystemCtCrisis.values().stream().filter(t -> t.status == EtCrisisStatus.pending && propagate(t::handlingDelayPassed).getValue()).count() == 0)
			throw new Exception("There are no unhandled crisises that have exceeded the handling delay");
	}

}