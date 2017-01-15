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
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.ActivatorLauncher;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.AdministratorLauncher;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.ComCompanyLauncher;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.MsrCreatorLauncher;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActMsrCreator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db.DbInitialize;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.designs.MsrCreatorDesign;

public class MsrCreatorView extends MsrCreatorDesign implements View, Serializable {

	private static final long serialVersionUID = 8241160607009293327L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public MsrCreatorView() {
		
		createSysEnvBtn.setClickShortcut(KeyCode.ENTER);
		
		createSysEnvBtn.addClickListener(event -> {
			IcrashSystem sys = IcrashSystem.getInstance();
			IcrashEnvironment env = IcrashEnvironment.getInstance();
			
			DbInitialize.initializeDatabase();
				
			// Messir initialization routines 
			ActMsrCreator theCreator = env.getActMsrCreator();
			theCreator.oeCreateSystemAndEnvironment(new PtInteger(new Integer(textField.getValue())));
			
			IcrashSystem.nrComCompanies = Integer.parseInt(textField.getValue());
			IcrashSystem.comCompanyViewNames = new ArrayList<String>();

			String currentURL = Page.getCurrent().getLocation().toString();
			int strIndexCreator = currentURL.lastIndexOf(MsrCreatorLauncher.creatorName); 
			String iCrashURL = currentURL.substring(0, strIndexCreator);  
			String googleShebang = "#!";
				
			String activatorURL = iCrashURL + ActivatorLauncher.activatorPageName;
			String comCompURL = iCrashURL + ComCompanyLauncher.comCompaniesName + googleShebang;
			String adminRootURL = iCrashURL + AdministratorLauncher.adminPageName;
				
			// open Activator console (tab)
			Page.getCurrent().open(activatorURL, "_blank");

			// open N tabs with N comcompanies
			// EXACTLY the same algorithm of assigning ID's here, as it was done in oeCreateSystemAndEnvironment:
			for (int i=0; i<IcrashSystem.nrComCompanies; i++) {
				IcrashSystem.comCompanyViewNames.add(String.format("%02d", i));
				Page.getCurrent().open(comCompURL + IcrashSystem.comCompanyViewNames.get(i), "_blank");
			}
				
			// open admin tab too
			Page.getCurrent().open(adminRootURL, "_blank");
			
			textField.focus();
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		textField.focus();
	}
}
