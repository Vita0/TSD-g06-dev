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

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

import com.vaadin.ui.FormLayout;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class AdminLoginView extends HorizontalLayout implements View, Button.ClickListener, Serializable {

	private static final long serialVersionUID = -3317915013312630958L;

	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
	
	ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
	CtAdministrator ctAdmin =  (CtAdministrator) sys.getCtAuthenticated(actAdmin);
	
	private Label welcomeText;
	private Label hintText;
	private TextField username;
	private PasswordField password;
	private Button loginButton;
	
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public AdminLoginView() {
		
		actAdmin.setActorUI(UI.getCurrent());
		env.setActAdministrator(actAdmin.getName(), actAdmin);
		IcrashSystem.assCtAuthenticatedActAuthenticated.replace(ctAdmin, actAdmin);
		
		log.debug("CHECK: ADMIN UI is "+actAdmin.getActorUI());
		
		welcomeText = new Label("Please login to access iCrash Administrator Console");
		hintText = new Label("(icrashadmin/7WXC1359)");
		welcomeText.setSizeUndefined();
		hintText.setSizeUndefined();
		
		// create the username input field
		username = new TextField("Login:");
		username.setWidth("120px");
		username.setInputPrompt("Admin login");
		username.setImmediate(true);

		// create the password input field
		password = new PasswordField("Password:");
		password.setWidth("120px");
		password.setValue("");
		password.setNullRepresentation("");
		password.setImmediate(true);
					
		// create the login button
		loginButton = new Button("Login", this);
		loginButton.setClickShortcut(KeyCode.ENTER);
		loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.setImmediate(true);
		
		/*********************************************************************************************/
		Table adminMessagesTable = new Table();
		adminMessagesTable.setContainerDataSource(actAdmin.getMessagesDataSource());
		adminMessagesTable.setCaption("Administrator messages");
	//	adminMessagesTable.setVisibleColumns("inputEvent", "message");
		/*********************************************************************************************/
		
		FormLayout FL = new FormLayout(username, password, loginButton);
		
		VerticalLayout welcomeLayout = new VerticalLayout(welcomeText, /*hintText,*/ FL);
		VerticalLayout leftVL = new VerticalLayout(welcomeLayout); 
		VerticalLayout rightVL = new VerticalLayout(adminMessagesTable);
		
		welcomeLayout.setComponentAlignment(welcomeText, Alignment.MIDDLE_CENTER);
	 // welcomeLayout.setComponentAlignment(hintText, Alignment.MIDDLE_CENTER);
		welcomeLayout.setComponentAlignment(FL, Alignment.MIDDLE_CENTER);
		
		setSizeFull();
		FL.setSizeUndefined();
		welcomeLayout.setSizeUndefined();
		leftVL.setSizeFull();
		rightVL.setSizeFull();
		
		leftVL.setComponentAlignment(welcomeLayout, Alignment.MIDDLE_CENTER);
		rightVL.setComponentAlignment(adminMessagesTable, Alignment.MIDDLE_CENTER);
		
		addComponent(leftVL);
		addComponent(rightVL);
		
		setMargin(true);
		setExpandRatio(leftVL, 6);
		setExpandRatio(rightVL, 4);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		username.setValue("");
		password.setValue(null);
		
		username.focus();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		ActAdministrator admin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
		
		try {
			admin.oeLogin(new DtLogin(new PtString(username.getValue())), new DtPassword(new PtString(password.getValue())));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// refreshing this view forces redirection of the user
		// to the view, where he should go now, be it AdminLoginView or AdminAuthView
		Page.getCurrent().reload();
	}
}
