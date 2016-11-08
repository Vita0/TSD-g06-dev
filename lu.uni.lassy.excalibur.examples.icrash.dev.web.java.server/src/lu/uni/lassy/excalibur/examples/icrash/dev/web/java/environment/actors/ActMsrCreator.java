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

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.*;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class ActMsrCreator {

	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public PtBoolean oeCreateSystemAndEnvironment(PtInteger aQtyComCompanies) {
		
		IcrashSystem sys = IcrashSystem.getInstance();
		
		log.info("message ActMsrCreator.oeCreateSystemAndEnvironment is sent to system");
	    PtBoolean res = sys.oeCreateSystemAndEnvironment(aQtyComCompanies);
	    
	    if(res.getValue() == true) 
			log.info("operation oeCreateSystemAndEnvironment successfully executed by the system");
		else 
			log.info("operation oeCreateSystemAndEnvironment failed! Some of its postconditions were not satisfied");
	    
	    return res;
	}    
}