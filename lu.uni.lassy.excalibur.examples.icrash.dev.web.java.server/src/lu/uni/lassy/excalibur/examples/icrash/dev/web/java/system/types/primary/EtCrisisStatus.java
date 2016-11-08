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
 * The Enum EtCrisisStatus which holds the different options that could be set as a crisis status.
 */
public enum EtCrisisStatus implements JIntIs {
	
	/** A crisis is set as pending when it is first initialised. It is also in this status when no coordinator is currently handling */
	pending, 
	/** A crisis is set as handled when it gets assigned to a coordinator. */
	handled, 
	
	/**  A crisis is set as solved when a coordinator sets it as being solved. */
	solved, 
	
	/**  A crisis is set as closed when a coordinator sets it as being closed. */
	closed;
	
	public PtBoolean is(){
		return new PtBoolean(this.name() == EtCrisisStatus.pending.name() ||
				this.name() == EtCrisisStatus.handled.name()|| this.name() == EtCrisisStatus.solved.name() ||
				this.name() == EtCrisisStatus.closed.name() );
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the alert status is to be one of the following:\n" +
	EtCrisisStatus.handled.name() + "\n" + EtCrisisStatus.solved.name() + "\n" + EtCrisisStatus.pending.name() + "\n" + EtCrisisStatus.closed.name());
	}
	
	
}
