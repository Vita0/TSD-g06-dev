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
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * The Class of the datatype Alert ID.
 */
public class DtAlertID  extends DtString implements JIntIs{

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;
	
		/**
		 * Instantiates a new datatype alert id.
		 *
		 * @param s The string to use to create the alert ID
		 */
		public DtAlertID(PtString s) {
			super(s);
		}
		
		/** The minimum length of a alert ID that is not acceptable. */
		private int _minLength = 0;
		
		/** The maximum length of a alert ID that is acceptable. */
		private int _maxLength = 20;

		public PtBoolean is(){
			return new PtBoolean(this.value.getValue().length() > _minLength && this.value.getValue().length() <= _maxLength);
		}

		@Override
		public PtString getExpectedDataStructure() {
			return new PtString("Expected strucutre of the Alert ID is to have a minimum length greater than " + _minLength + " and a maximum length of " + _maxLength); 
		}
}
