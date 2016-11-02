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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils;

import java.util.Calendar;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDay;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtHour;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtMinute;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtMonth;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtYear;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public class ICrashUtils {

		public static final String ICRASH_PROPERTIES_FILE = "iCrashState.properties";
		public static final String ICRASH_PROPERTIES_FILE_EXT = "./iCrashState.properties";
			
		static public DtDateAndTime getCurrentDateAndTime(){
			Calendar cal = Calendar.getInstance();
			
			DtDay currDay = new DtDay(new PtInteger(cal.get(Calendar.DAY_OF_MONTH))); 
			DtMonth currMonth = new DtMonth(new PtInteger(cal.get(Calendar.MONTH)+1));
			DtYear currYear = new DtYear(new PtInteger(cal.get(Calendar.YEAR)));
			DtDate currDate = new DtDate(currYear,currMonth,currDay);
			
			DtHour currHour = new DtHour(new PtInteger(cal.get(Calendar.HOUR_OF_DAY))); 
			DtMinute currMinute = new DtMinute(new PtInteger(cal.get(Calendar.MINUTE)));
			DtSecond currSecond = new DtSecond(new PtInteger(cal.get(Calendar.SECOND)));
			DtTime currTime = new DtTime(currHour,currMinute,currSecond);
			
			return new DtDateAndTime(currDate,currTime);
			
		}
	
	
		static public DtDate getCurrentDate(){
			Calendar cal = Calendar.getInstance();
			
			DtDay currDay = new DtDay(new PtInteger(cal.get(Calendar.DAY_OF_MONTH))); 
			DtMonth currMonth = new DtMonth(new PtInteger(cal.get(Calendar.MONTH)));
			DtYear currYear = new DtYear(new PtInteger(cal.get(Calendar.YEAR)));
			
			return new DtDate(currYear,currMonth,currDay);
			
		}
	
	
	
		static public DtTime getCurrentTime(){
			Calendar cal = Calendar.getInstance();
			
			DtHour currHour = new DtHour(new PtInteger(cal.get(Calendar.HOUR_OF_DAY))); 
			DtMinute currMinute = new DtMinute(new PtInteger(cal.get(Calendar.MINUTE)));
			DtSecond currSecond = new DtSecond(new PtInteger(cal.get(Calendar.SECOND)));
			
			return new DtTime(currHour,currMinute,currSecond);
			
		}
	
	
	
		static public DtDateAndTime setDateAndTime(int y, int m, int d, int h, int min, int sec){
			
			DtDay currDay = new DtDay(new PtInteger(d)); 
			DtMonth currMonth = new DtMonth(new PtInteger(m));
			DtYear currYear = new DtYear(new PtInteger(y));
			DtDate currDate = new DtDate(currYear,currMonth,currDay);
			
			DtHour currHour = new DtHour(new PtInteger(h)); 
			DtMinute currMinute = new DtMinute(new PtInteger(min));
			DtSecond currSecond = new DtSecond(new PtInteger(sec));
			DtTime currTime = new DtTime(currHour,currMinute,currSecond);
			
			return new DtDateAndTime(currDate,currTime);
			
		}


		static public DtDate setDate(int y, int m, int d){
			
			DtDay currDay = new DtDay(new PtInteger(d)); 
			DtMonth currMonth = new DtMonth(new PtInteger(m));
			DtYear currYear = new DtYear(new PtInteger(y));
			
			return new DtDate(currYear,currMonth,currDay);
			
		}


		static public DtTime setTime(int h, int min, int sec){
			
			DtHour currHour = new DtHour(new PtInteger(h)); 
			DtMinute currMinute = new DtMinute(new PtInteger(min));
			DtSecond currSecond = new DtSecond(new PtInteger(sec));
			
			return new DtTime(currHour,currMinute,currSecond);
			
		}
		
		static public PtBoolean isEmpty(PtBoolean aPtBoolean){
			return isObjEmpty(aPtBoolean);
		}
		
		static public PtBoolean isEmpty(DtLogin aDtLogin){
			if (isObjEmpty(aDtLogin).getValue())
				return new PtBoolean(true);
			return isEmpty(aDtLogin.value);
		}
		
		static public PtBoolean isEmpty(PtString aPtString){
			if (isObjEmpty(aPtString).getValue())
				return new PtBoolean(true);
			if (isObjEmpty(aPtString.getValue()).getValue())
				return new PtBoolean(true);
			if (aPtString.getValue().equals(""))
				return new PtBoolean(true);
			return new PtBoolean(false);
		}
		
		static private PtBoolean isObjEmpty(Object aObject){
			if (aObject == null)
				return new PtBoolean(true);
			return new PtBoolean(false);
		}
}
