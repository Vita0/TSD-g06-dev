/*******************************************************************************
 * Copyright (c) 2014 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.DtIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public class DtCoordinatorID extends DtString implements DtIs{

		private static final long serialVersionUID = 227L;

		public DtCoordinatorID(PtString s) {
			super(s);
		}
		private int _minLength = 0;
		private int _maxLength = 5;
		public PtBoolean is(){
			return new PtBoolean(this.value.getValue().length() > _minLength &&
					this.value.getValue().length() <= _maxLength);
		}
		public PtString getExpectedDataStructure(){
			return new PtString("Expected structure of the coordinator ID is to have a minimum length of " + _minLength + " and a maximum length of " + _maxLength); 
		}
}
