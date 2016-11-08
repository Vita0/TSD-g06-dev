/*******************************************************************************
 * Copyright (c) 2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Anton Nikonienkov - iCrash HTML5 API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDay;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtHour;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtMinute;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtMonth;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtYear;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.designs.ActivatorDesign;

public class ActivatorView extends ActivatorDesign implements View, Serializable {

	private static final long serialVersionUID = -637344237113875270L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public ActivatorView() {
		
		IcrashEnvironment env = IcrashEnvironment.getInstance();
		
		hourSlider.setMin(0);
		hourSlider.setMax(23);
			
		minuteSlider.setMin(0);
		minuteSlider.setMax(59);
				
		secondSlider.setMin(0);
		secondSlider.setMax(59);
		
		date.setDateFormat("dd/MM/yyyy");
		
		hourSlider.addValueChangeListener(event -> {
			Double d = (double) (event.getProperty().getValue());
			Integer i = d.intValue();
			inputHour.setValue(i.toString());
		});
		
		minuteSlider.addValueChangeListener(event -> {
			Double d = (double) (event.getProperty().getValue());
			Integer i = d.intValue();
			inputMinute.setValue(i.toString());
		});
				
		secondSlider.addValueChangeListener(event -> {
			Double d = (double) (event.getProperty().getValue());
			Integer i = d.intValue();
			inputSecond.setValue(i.toString());
		});
		
		inputHour.addBlurListener(event -> {
			if (!inputHour.getValue().isEmpty()) {
				String inputValue = new String(inputHour.getValue());
				Double doubleValue = Double.valueOf(inputValue);
				if (doubleValue >= 0 && doubleValue <= 23)
					hourSlider.setValue(doubleValue);
			}
		});
		
		inputMinute.addBlurListener(event -> {
			if (!inputMinute.getValue().isEmpty()) {
				String inputValue = new String(inputMinute.getValue());
				Double doubleValue = Double.valueOf(inputValue);
				if (doubleValue >= 0 && doubleValue <= 59)
					minuteSlider.setValue(doubleValue);
			}
		});
		
		inputSecond.addBlurListener(event -> {
			if (!inputSecond.getValue().isEmpty()) {
				String inputValue = new String(inputSecond.getValue());
				Double doubleValue = Double.valueOf(inputValue);
				if (doubleValue >= 0 && doubleValue <= 59)
					secondSlider.setValue(doubleValue);
			}
		});
		
		setTimeBtn.setClickShortcut(KeyCode.ENTER);
		
		setTimeBtn.addClickListener(event -> {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date.getValue());
			env.getActActivator().oeSetClock(
					new DtDateAndTime(
							new DtDate(
									new DtYear(
											new PtInteger(cal.get(Calendar.YEAR))),
					new DtMonth(
							new PtInteger((int)(cal.get(Calendar.MONTH)+1))),
					new DtDay(
							new PtInteger(cal.get(Calendar.DAY_OF_MONTH)))),
					new DtTime(
							new DtHour(
									new PtInteger(Integer.parseInt(inputHour.getValue()))),
							new DtMinute(
									new PtInteger(Integer.parseInt(inputMinute.getValue()))),
							new DtSecond(
									new PtInteger(Integer.parseInt(inputSecond.getValue())))))); 
		});  
		
		nowBtn.addClickListener(e -> {
			DtTime nowTime = ICrashUtils.getCurrentTime();
			date.setValue(new Date());

			int nowHourInInt = nowTime.hour.value.getValue();
			Double nowHourInDouble = Double.valueOf((double)nowHourInInt);
			hourSlider.setValue(nowHourInDouble);
			
			int nowMinuteInInt = nowTime.minute.value.getValue();
			Double nowMinuteInDouble = Double.valueOf((double)nowMinuteInInt);
			minuteSlider.setValue(nowMinuteInDouble);
			
			int nowSecondInInt = nowTime.second.value.getValue();
			Double nowSecondInDouble = Double.valueOf((double)nowSecondInInt);
			secondSlider.setValue(nowSecondInDouble);
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
}
