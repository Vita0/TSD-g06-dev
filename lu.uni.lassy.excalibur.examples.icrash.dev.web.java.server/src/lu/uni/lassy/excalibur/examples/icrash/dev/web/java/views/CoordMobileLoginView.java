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

import org.apache.log4j.Logger;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class CoordMobileLoginView extends NavigationView implements View, Serializable {
	private static final long serialVersionUID = 1866773510048507541L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
	
	TextField login = new TextField("Login");
	PasswordField pwd = new PasswordField("Password");
	Button loginBtn = new Button("Login");
	
	public CoordMobileLoginView(String CoordID) {
		CtCoordinator ctCoordinator = (CtCoordinator) sys.getCtCoordinator(new DtCoordinatorID(new PtString(CoordID)));
		ActCoordinator actCoordinator = env.getActCoordinator(ctCoordinator.login);
		
		actCoordinator.setActorUI(UI.getCurrent());
		
		env.setActCoordinator(actCoordinator.getName(), actCoordinator);
		
		IcrashSystem.assCtAuthenticatedActAuthenticated.replace(ctCoordinator, actCoordinator);
		IcrashSystem.assCtCoordinatorActCoordinator.replace(ctCoordinator, actCoordinator);
		
		setCaption("Login to Coord " + ctCoordinator.login.toString());
		
		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		
		VerticalComponentGroup group = new VerticalComponentGroup();
		layout.addComponent(group);
		
		VerticalLayout loginExtLayout = new VerticalLayout();
		loginExtLayout.setSizeFull();
		group.addComponent(loginExtLayout);

		FormLayout loginIntLayout = new FormLayout();
		loginIntLayout.setSizeUndefined();
		loginExtLayout.addComponent(loginIntLayout);
		
		loginExtLayout.setComponentAlignment(loginIntLayout, Alignment.MIDDLE_CENTER);
		
		login.setInputPrompt("Coord login");
		pwd.setValue("");
		pwd.setNullRepresentation("");
		
		loginBtn.setClickShortcut(KeyCode.ENTER);
		
		loginIntLayout.addComponents(login, pwd, loginBtn);
		
		///////////////////////////////////////////////////////////////////////////////////
		
		Grid messagesTable = new Grid();
		messagesTable.setContainerDataSource(actCoordinator.getMessagesDataSource());
		messagesTable.setSizeUndefined();
		messagesTable.setColumns("inputEvent", "message");
		
		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setSizeFull();
		
		group.addComponent(tableLayout);
		tableLayout.addComponent(messagesTable);
		
		tableLayout.setComponentAlignment(messagesTable, Alignment.MIDDLE_CENTER);
		
		///////////////////////////////////////////////////////////////////////////////////
		
		loginBtn.addClickListener(event -> {
			PtBoolean res;
			try {
				res = actCoordinator.oeLogin(new DtLogin(new PtString(login.getValue())), new DtPassword(new PtString(pwd.getValue())));
				log.info("oeLogin returned "+res.getValue());
				
				if (res.getValue()) {
					log.debug("After actCoordinator.oeLogin: JUST LOGGED IN, so Coord's vpIsLogged = "+ctCoordinator.vpIsLogged.getValue());
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			// refreshing this view forces redirection of the user
			// to the view, where he should go now, be it CoordLoginView or CoordAuthView
			   Page.getCurrent().reload();
			   
			   actCoordinator.setActorUI(UI.getCurrent());
			   env.setActCoordinator(actCoordinator.getName(), actCoordinator);
				
			   IcrashSystem.assCtAuthenticatedActAuthenticated.replace(ctCoordinator, actCoordinator);
			   IcrashSystem.assCtCoordinatorActCoordinator.replace(ctCoordinator, actCoordinator);
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		login.setValue("");
		pwd.setValue(null);
		
		login.focus();
	}
}