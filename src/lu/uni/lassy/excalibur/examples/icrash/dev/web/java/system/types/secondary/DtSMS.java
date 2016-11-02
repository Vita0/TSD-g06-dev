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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.secondary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * A datatype class that stores SMS messages that have been sent to humans.
 */
public class DtSMS implements Serializable, JIntIs {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/**  The message of the SMS sent out. */
		public PtString value;
		
		/** The maximum length a SMS could be. */
		private int _maxLength = 160;
		
		/**
		 * Instantiates a new datatype SMS with the message provided.
		 *
		 * @param s The primitive type of string to put into the datatype
		 */
		public DtSMS(PtString s){
			value = s;
		}
		
		public PtBoolean is(){
			return new PtBoolean(this.value.getValue().length() <= _maxLength);
		}

		@Override
		public PtString getExpectedDataStructure() {
			return new PtString("Expected structure of the SMS is to have a maximum length of " + _maxLength);
		}
}
