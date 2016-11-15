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
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDay;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtHour;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtMinute;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtMonth;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtSecond;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtYear;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.ComCompaniesInLux;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.designs.ComCompanyDesign;

public class ComCompanyView extends ComCompanyDesign implements View, Serializable {

	private static final long serialVersionUID = 6645318977327753735L;
	
	transient Logger log = Log4JUtils.getInstance().getLogger();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
	
	public ComCompanyView(String viewName) {

		String thisComCompanyName = ComCompaniesInLux.values[Integer.parseInt(viewName)].name();
		ActComCompany currentComCompany = env.getComCompany(thisComCompanyName);
		
		currentComCompany.setActorUI(UI.getCurrent());
		env.setComCompany(thisComCompanyName, currentComCompany);

		IcrashSystem.cmpSystemActComCompany.replace(thisComCompanyName, currentComCompany);
		
		comCompanyMessages.setContainerDataSource(currentComCompany.getMessagesDataSource());
	 // comCompanyMessages.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
	
		comCompanyMessages.setColumnExpandRatio("inputEvent", 20);
		comCompanyMessages.setColumnExpandRatio("phoneNumber", 20);
		comCompanyMessages.setColumnExpandRatio("message", 80);
		comCompanyMessages.setVisibleColumns("inputEvent", "phoneNumber", "message");
		comCompanyMessages.setSizeFull();
		
		messagesPanel.setContent(comCompanyMessages);
		
		welcomeText.setValue("<h1>Welcome to the iCrash Communication Company "+viewName+" console!</h1>"); 
		//comCompanyNameToShow.setValue("<h1><b>"+viewName+" - "+thisComCompanyName+"</b></h1>");
		comCompanyNameToShow.setVisible(false);
		
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
		
		sendAlertBtn.setClickShortcut(KeyCode.ENTER);
		
		sendAlertBtn.addClickListener(event -> {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date.getValue());
			
			log.info(typeOfPerson.getValue().toString());
			log.info(phoneNr.getValue());
			log.info(latitude.getValue());
			log.info(longitude.getValue());
			log.info(comment.getValue());
				
			EtHumanKind _typeOfPerson = null;

			if (typeOfPerson.getValue().toString().equals("witness"))
				_typeOfPerson = EtHumanKind.witness;
			if (typeOfPerson.getValue().toString().equals("victim"))
				_typeOfPerson = EtHumanKind.victim;
			if (typeOfPerson.getValue().toString().equals("anonym"))
				_typeOfPerson = EtHumanKind.anonym;
			
			DtDate aDtDate = new DtDate(
					new DtYear(new PtInteger(cal.get(Calendar.YEAR))),
					new DtMonth(new PtInteger(cal.get(Calendar.MONTH)+1)),
					new DtDay(new PtInteger(cal.get(Calendar.DAY_OF_MONTH))));
				
			DtTime aDtTime = new DtTime(
					new DtHour(new PtInteger(Integer.parseInt(inputHour.getValue()))),
					new DtMinute(new PtInteger(Integer.parseInt(inputMinute.getValue()))),
					new DtSecond(new PtInteger(Integer.parseInt(inputSecond.getValue()))));
				
			DtPhoneNumber aDtPhoneNumber = new DtPhoneNumber(new PtString(phoneNr.getValue()));
				
			DtGPSLocation aDtGPSLocation = new DtGPSLocation(
					new DtLatitude(new PtReal(Double.parseDouble(latitude.getValue()))),
					new DtLongitude(new PtReal(Double.parseDouble(longitude.getValue())))
					);
				
			DtComment aDtComment = new DtComment(new PtString(comment.getValue()));
			
			currentComCompany.oeAlert(_typeOfPerson, aDtDate, aDtTime, aDtPhoneNumber, aDtGPSLocation, aDtComment);
		});
		
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
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}
