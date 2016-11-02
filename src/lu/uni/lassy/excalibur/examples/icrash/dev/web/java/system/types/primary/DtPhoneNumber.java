/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
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
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * The Class DtPhoneNumber, which holds a datatype of the phone number.
 */
public class DtPhoneNumber extends DtString implements JIntIs {

		private static final long serialVersionUID = 227L;

		/**
		 * Instantiates a new datatype phone number.
		 *
		 * @param s The primitive type of string to become the datatype of the phone number
		 */
		public DtPhoneNumber(PtString s) {
			super(s);
		}
		
		/** The minimum length that a phone number can't be. */
		private int _minLength = 4;
		
		/** The maximum length that a phone number can be. */
		private int _maxLength = 30;
		
		public PtBoolean is(){
			return new PtBoolean(this.value.getValue().length() > _minLength && this.value.getValue().length() <= _maxLength);
		}
		
		public PtString getExpectedDataStructure(){
			return new PtString("Expected structure of the phone number is to have a minimum length of " + _minLength + " and a maximum length of " + _maxLength); 
		}
}
