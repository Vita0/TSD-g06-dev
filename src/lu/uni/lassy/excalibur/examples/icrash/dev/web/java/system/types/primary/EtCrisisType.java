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
 * The Enum EtCrisisType, which dictates the size of the crisis. How big was the accident?
 */
public enum EtCrisisType implements JIntIs {
	
	/** A small crisis. */
	small, 
	/** A medium crisis. */
	medium, 
	/** A huge crisis. */
	huge;
	
	public PtBoolean is(){
		return new PtBoolean(this.name() == EtCrisisType.small.name() ||
				this.name() == EtCrisisType.medium.name()|| this.name() == EtCrisisType.huge.name());
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the alert status is to be one of the following:\n" +
				EtCrisisType.small.name() + "\n" + EtCrisisType.medium.name() + "\n" + EtCrisisType.huge.name());
	}
}
