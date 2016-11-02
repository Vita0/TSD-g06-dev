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
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * The Class DtLongitude, which holds a datatype of the longitude.
 */
public class DtLongitude extends DtReal implements JIntIs{

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 227L;

		/**
		 * Instantiates a new datatype longitude.
		 *
		 * @param r The primitive type real (Double) to become the datatype longitude
		 */
		public DtLongitude(PtReal r) {
			super(r);
		}
		
		/** The minimum number a longitude value could be. */
		private double _minNumber = -180;
		
		/** The maximum number a longitude value could be. */
		private double _maxNumber = 180;
		
		public PtBoolean is(){
			//http://www.geomidpoint.com/latlon.html
			return new PtBoolean(this.value.getValue() >= _minNumber && this.value.getValue() <= _maxNumber);
		}
		
		public PtString getExpectedDataStructure(){
			return new PtString("Expected structure of the longitude is to have a minimum size of " + _minNumber + " and a maximum size of " + _maxNumber); 
		}
}
