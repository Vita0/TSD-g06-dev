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
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class ActActivator {

	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public ActActivator() {
	}
	
	public PtBoolean oeSetClock(DtDateAndTime aDtDateAndTime) {
		
		IcrashSystem sys = IcrashSystem.getInstance();
		
		log.info("message ActActivator.oeSetClock sent to system");
		PtBoolean res = sys.oeSetClock(aDtDateAndTime);

		if (res.getValue() == true)
			log.info("operation oeSetClock successfully executed by the system");

		return res;
	}
}
