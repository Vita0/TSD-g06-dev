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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib;

import java.io.Serializable;

public class DtDate implements Serializable {

	private static final long serialVersionUID = -7027825135490849598L;
	
		public DtYear year;
		public DtMonth month;
		public DtDay day;


		public DtDate(DtYear aYear,DtMonth aMonth,DtDay aDay){
				year = aYear;
				month = aMonth;
				day = aDay;
		}
		@Override
		public String toString(){
			String test =year.toString() + "/" + month.toString() + "/" + day.toString(); 
			return  test;
		}
}
