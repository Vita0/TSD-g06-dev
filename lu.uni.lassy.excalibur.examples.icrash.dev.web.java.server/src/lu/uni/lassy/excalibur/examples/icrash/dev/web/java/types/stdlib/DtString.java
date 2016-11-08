/*******************************************************************************
 * Copyright (c) 2014 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Anton Nikonienkov - iCrash HTML5 API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib;

import java.io.Serializable;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class DtString implements Serializable {

		@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DtString other = (DtString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
		private static final long serialVersionUID = 227L;
		transient Logger log = Log4JUtils.getInstance().getLogger();
		
		public PtString value;
		
		public DtString(PtString s){
			value = s;
		}

		// and what if DtString were null?
		public PtBoolean eq(DtString s){
			
	     // ??? do I have right to introduce this (dirty) null check and still be compliant ???
		 // if I don't have this right, then I leave it here as a temporary workaround for spec bug
		 // I'll remove it later when this issue will be fully resolved	
					
			log.debug("Inside DtString.eq: s.value.getValue()"+s.value.getValue());
			if (s.value.getValue() == null) {
				boolean res = "".equals(this.value.getValue());
				if(res)
					return new PtBoolean(true);
				else
					return new PtBoolean(false);
			} else {
			
				boolean res = s.value.getValue().equals(this.value.getValue());
		
				if(res)
					return new PtBoolean(true);
				else
					return new PtBoolean(false);
			}	
		}
		@Override
		public String toString(){
			return value.getValue();
		}
}
