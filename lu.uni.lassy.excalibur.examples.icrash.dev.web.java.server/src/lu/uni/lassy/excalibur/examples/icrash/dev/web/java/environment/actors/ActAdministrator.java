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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.ActorMessageBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class ActAdministrator extends ActAuthenticated {

	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public ActAdministrator(DtLogin aDtLogin) {
		super(aDtLogin);
	}
	
	public PtBoolean oeAddCoordinator(
			DtCoordinatorID aDtCoordinatorID, DtLogin aDtLogin,
			DtPassword aDtPassword) throws Exception {

		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeAddCoordinator sent to system");
		PtBoolean res = sys.oeAddCoordinator(aDtCoordinatorID, aDtLogin, aDtPassword);

		if (res.getValue() == true)
			log.info("operation oeAddCoordinator successfully executed by the system");

		return res;
	}

	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID) throws Exception {

		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeDeleteCoordinator sent to system");
		PtBoolean res = sys.oeDeleteCoordinator(aDtCoordinatorID);

		if (res.getValue() == true)
			log.info("operation oeDeleteCoordinator successfully executed by the system");

		return res;
	}
	
	public PtBoolean ieCoordinatorAdded() {
		getMessagesDataSource().addBean(new ActorMessageBean("ieCoordinatorAdded", ""));
		return new PtBoolean(true);
	}
	
	public PtBoolean ieCoordinatorDeleted() {
		getMessagesDataSource().addBean(new ActorMessageBean("ieCoordinatorDeleted", ""));
		return new PtBoolean(true);
	}
}