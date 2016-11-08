/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 *     Anton Nikonienkov - iCrash HTML5 API and implementation     
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * The Enum EtAlertStatus which holds the different alert statuses.
 */
public enum EtAlertStatus implements JIntIs{
	
	/**  The alert is currently pending and has not been validated. */
	pending, 
	
	/**  The alert is considered to be valid. */
	valid, 
	
	/**  The alert is considered to be invalid. */
	invalid;
	
	public PtBoolean is(){
		return new PtBoolean(this.name() == EtAlertStatus.pending.name() || this.name() == EtAlertStatus.valid.name()||  this.name() == EtAlertStatus.invalid.name());
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the alert status is to be one of the following:\n" + EtAlertStatus.valid.name() + "\n" + EtAlertStatus.invalid.name() + "\n" + EtAlertStatus.pending.name());
	}	
}
