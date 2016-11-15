/*******************************************************************************
 * Copyright (c) 2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 *     Anton Nikonienkov - iCrash HTML5 API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public enum EtHumanKind implements JIntIs {

	/** A witness to the accident. */
	witness,
	/** A victim of the accident. */
	victim,
	/** An anonymous reporter of an accident. */
	anonym,
	/** A victim's of the accident family member. */
	familyMember;

	public PtBoolean is(){
		return new PtBoolean(this.name() == EtHumanKind.witness.name() ||
				this.name() == EtHumanKind.victim.name()|| this.name() == EtHumanKind.anonym.name() || this.name() == EtHumanKind.familyMember.name());
	}
	
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the alert status is to be one of the following:\n" +
				EtHumanKind.witness.name() + "\n" + EtHumanKind.victim.name() + "\n" + EtHumanKind.anonym.name() + "\n" + EtHumanKind.familyMember.name());
	}
}
