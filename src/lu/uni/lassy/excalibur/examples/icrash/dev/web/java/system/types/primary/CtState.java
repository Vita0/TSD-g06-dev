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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;

public class CtState implements Serializable {

		private static final long serialVersionUID = 227L;

		public static DtInteger nextValueForAlertID;
		public static DtInteger nextValueForCrisisID;
		public static DtDateAndTime clock;
		public static DtSecond crisisReminderPeriod;
		public static DtSecond maxCrisisReminderPeriod;
		public static DtDateAndTime vpLastReminder;
		public static PtBoolean vpStarted;
		
		
		
		
		public static PtBoolean init(DtInteger aNextValueForAlertID, DtInteger aNextValueForCrisisID, 
							DtDateAndTime aClock, DtSecond aCrisisReminderPeriod, 
							DtSecond aMaxCrisisReminderPeriod, DtDateAndTime aVpLastReminder, 
							PtBoolean aVpStarted){
		
		
					nextValueForAlertID = aNextValueForAlertID;
					nextValueForCrisisID = aNextValueForCrisisID;
					clock = aClock;
					crisisReminderPeriod = aCrisisReminderPeriod;
					maxCrisisReminderPeriod = aMaxCrisisReminderPeriod;
					vpLastReminder = aVpLastReminder;
					vpStarted = aVpStarted;
					
					return new PtBoolean(true);
		}
		
	

}
