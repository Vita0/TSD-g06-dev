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
import java.util.Locale;

import org.apache.log4j.Logger;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.AdministratorLauncher;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.misc.CoordinatorServlet;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class AdminAuthView extends VerticalLayout implements View, Serializable {

	private static final long serialVersionUID = -1119522327335514726L;
	static Logger log = Log4JUtils.getInstance().getLogger();

	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();	
	
	ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
	CtAdministrator ctAdmin =  (CtAdministrator) sys.getCtAuthenticated(actAdmin);
	
	Label welcomeText = new Label();
	
	// a handy Java 8 way to add a listener to a component
	Button logoutBtn = new Button("Logout", this::LogoutClick);
	public void LogoutClick(ClickEvent event) {
		ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
		try {
			actAdmin.oeLogout();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		// refresh this view, that will force redirection of the user
		// to the view, where he should go now, be it AdminLoginView or AdminAuthView
		Page.getCurrent().reload();
	}
	
	public AdminAuthView() {

		setSizeFull();
		
		VerticalLayout header = new VerticalLayout();
		header.setSizeFull();
		
		HorizontalLayout content = new HorizontalLayout();
		content.setSizeFull();
		
		addComponents(header, content);
		setExpandRatio(header, 1);
		setExpandRatio(content, 6);
		
		welcomeText.setValue("<h1>Welcome to the iCrash Administrator console!</h1>");
		welcomeText.setContentMode(ContentMode.HTML);
		welcomeText.setSizeUndefined();
		header.addComponent(welcomeText);
		header.setComponentAlignment(welcomeText, Alignment.MIDDLE_CENTER);
		
		Panel controlPanel = new Panel("Administrator control panel");
		controlPanel.setSizeUndefined();
		
		Panel addCoordPanel = new Panel("Create a new coordinator");
		addCoordPanel.setSizeUndefined();
		
		Panel messagesPanel = new Panel("Administrator messages");
		messagesPanel.setWidth("580px");

		Table adminMessagesTable = new Table();
		
		adminMessagesTable.setContainerDataSource(actAdmin.getMessagesDataSource());
	
		adminMessagesTable.setColumnWidth("inputEvent", 180);
		adminMessagesTable.setSizeFull();
		adminMessagesTable.setVisibleColumns("inputEvent", "message");
	
		VerticalLayout controlLayout = new VerticalLayout(controlPanel);
		controlLayout.setSizeFull();
		controlLayout.setMargin(false);
		controlLayout.setComponentAlignment(controlPanel, Alignment.TOP_CENTER);
		
		VerticalLayout coordOperationsLayout = new VerticalLayout(addCoordPanel);
		coordOperationsLayout.setSizeFull();
		coordOperationsLayout.setMargin(false);
		coordOperationsLayout.setComponentAlignment(addCoordPanel, Alignment.TOP_CENTER);
		
		/******************************************/
		coordOperationsLayout.setVisible(true); // main layout in the middle
		addCoordPanel.setVisible(false); // ...which contains the panel "Create a new coordinator"
		/******************************************/
		
		HorizontalLayout messagesExternalLayout = new HorizontalLayout(messagesPanel);
		VerticalLayout messagesInternalLayout = new VerticalLayout(adminMessagesTable);
		
		messagesExternalLayout.setSizeFull();
		messagesExternalLayout.setMargin(false);
		messagesExternalLayout.setComponentAlignment(messagesPanel, Alignment.TOP_CENTER);
		
		messagesInternalLayout.setMargin(false);
		messagesInternalLayout.setSizeFull();
		messagesInternalLayout.setComponentAlignment(adminMessagesTable, Alignment.TOP_CENTER);
		
		messagesPanel.setContent(messagesInternalLayout);
		
		TextField idCoordAdd = new TextField();
		TextField loginCoord = new TextField();
		PasswordField pwdCoord = new PasswordField();		
		
		Label idCaptionAdd = new Label("ID");
		Label loginCaption = new Label("Login");
		Label pwdCaption = new Label("Password");
		
		idCaptionAdd.setSizeUndefined();
		idCoordAdd.setSizeUndefined();
		
		loginCaption.setSizeUndefined();
		loginCoord.setSizeUndefined();
		
		pwdCaption.setSizeUndefined();
		pwdCoord.setSizeUndefined();
		
		Button validateNewCoord = new Button("Validate");
		validateNewCoord.setClickShortcut(KeyCode.ENTER);
		validateNewCoord.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		GridLayout addCoordinatorLayout = new GridLayout(2, 4);
		addCoordinatorLayout.setSpacing(true);
		addCoordinatorLayout.setMargin(true);
		addCoordinatorLayout.setSizeFull();
		
		addCoordinatorLayout.addComponents(
				idCaptionAdd, idCoordAdd,
				loginCaption, loginCoord,
				pwdCaption, pwdCoord);
		
		addCoordinatorLayout.addComponent(validateNewCoord, 1, 3);

		addCoordinatorLayout.setComponentAlignment(idCaptionAdd, Alignment.MIDDLE_LEFT);
		addCoordinatorLayout.setComponentAlignment(idCoordAdd, Alignment.MIDDLE_LEFT);
		addCoordinatorLayout.setComponentAlignment(loginCaption, Alignment.MIDDLE_LEFT);
		addCoordinatorLayout.setComponentAlignment(loginCoord, Alignment.MIDDLE_LEFT);
		addCoordinatorLayout.setComponentAlignment(pwdCaption, Alignment.MIDDLE_LEFT);
		addCoordinatorLayout.setComponentAlignment(pwdCoord, Alignment.MIDDLE_LEFT);
		
		addCoordPanel.setContent(addCoordinatorLayout);
		
		content.addComponents(controlLayout, coordOperationsLayout, messagesExternalLayout);
		content.setComponentAlignment(messagesExternalLayout, Alignment.TOP_CENTER);
		content.setComponentAlignment(controlLayout, Alignment.TOP_CENTER);
		content.setComponentAlignment(messagesExternalLayout, Alignment.TOP_CENTER);
		
		content.setExpandRatio(controlLayout, 20);
		content.setExpandRatio(coordOperationsLayout, 10);
		content.setExpandRatio(messagesExternalLayout, 28);
		
		Button addCoordinator = new Button("Add coordinator");
		Button deleteCoordinator = new Button("Delete coordinator");
		
		addCoordinator.addStyleName(ValoTheme.BUTTON_HUGE);
		deleteCoordinator.addStyleName(ValoTheme.BUTTON_HUGE);
		logoutBtn.addStyleName(ValoTheme.BUTTON_HUGE);
		
		VerticalLayout buttons = new VerticalLayout();
		
		buttons.setMargin(true);
		buttons.setSpacing(true);
		buttons.setSizeFull();
		
		buttons.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		controlPanel.setContent(buttons);
		
		buttons.addComponents(addCoordinator, deleteCoordinator, logoutBtn);
		
		/******* DELETE COORDINATOR PANEL BEGIN *********/
		Label idCaptionDel = new Label("ID");
		TextField idCoordDel = new TextField();
		
		Panel delCoordPanel = new Panel("Delete a coordinator");
		
		coordOperationsLayout.addComponent(delCoordPanel);
		delCoordPanel.setVisible(false);
		
		coordOperationsLayout.setComponentAlignment(delCoordPanel, Alignment.TOP_CENTER);
		delCoordPanel.setSizeUndefined();
		
		GridLayout delCoordinatorLayout = new GridLayout(2, 2);
		delCoordinatorLayout.setSpacing(true);
		delCoordinatorLayout.setMargin(true);
		delCoordinatorLayout.setSizeFull();
		
		Button deleteCoordBtn = new Button("Delete");
		deleteCoordBtn.setClickShortcut(KeyCode.ENTER);
		deleteCoordBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		delCoordinatorLayout.addComponents(
				idCaptionDel, idCoordDel);
		
		delCoordinatorLayout.addComponent(deleteCoordBtn, 1, 1);

		delCoordinatorLayout.setComponentAlignment(idCaptionDel, Alignment.MIDDLE_LEFT);
		delCoordinatorLayout.setComponentAlignment(idCoordDel, Alignment.MIDDLE_LEFT);
		
		delCoordPanel.setContent(delCoordinatorLayout);
		/******* DELETE COORDINATOR PANEL END *********/
		
		
		/************************************************* MAIN BUTTONS LOGIC BEGIN *************************************************/
		
		addCoordinator.addClickListener(event -> {
			if (!addCoordPanel.isVisible()) {
				delCoordPanel.setVisible(false);
				addCoordPanel.setVisible(true);
				idCoordAdd.focus();
			} else
				addCoordPanel.setVisible(false);
		});
		
		deleteCoordinator.addClickListener(event -> {
			if (!delCoordPanel.isVisible()) {
				addCoordPanel.setVisible(false);
				delCoordPanel.setVisible(true);
				idCoordDel.focus();
			} else
				delCoordPanel.setVisible(false);
		});
		
		/************************************************* MAIN BUTTONS LOGIC END *************************************************/
		
		
		
		/************************************************* ADD COORDINATOR FORM LOGIC BEGIN *************************************************/
		validateNewCoord.addClickListener(event -> {
			
			String currentURL = Page.getCurrent().getLocation().toString();
			int strIndexCreator = currentURL.lastIndexOf(AdministratorLauncher.adminPageName);
			String iCrashURL = currentURL.substring(0, strIndexCreator);
			String googleShebang = "#!";
			String coordURL = iCrashURL + CoordinatorServlet.coordinatorsName + googleShebang;
			
			try {
				sys.oeAddCoordinator(
						new DtCoordinatorID(new PtString(idCoordAdd.getValue())),
						new DtLogin(new PtString(loginCoord.getValue())),
						new DtPassword(new PtString(pwdCoord.getValue()))
						);
					
				// open new browser tab with the newly created coordinator console...
				// "_blank" instructs the browser to open a new tab instead of a new window...
				// unhappily not all browsers interpret it correctly,
				// some versions of some browsers might still open a new window instead (notably Firefox)!
				Page.getCurrent().open(coordURL + idCoordAdd.getValue(), "_blank");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			idCoordAdd.setValue("");
			loginCoord.setValue("");
			pwdCoord.setValue("");
				
			idCoordAdd.focus();
		});
		/************************************************* ADD COORDINATOR FORM LOGIC END *************************************************/
		/************************************************* DELETE COORDINATOR FORM LOGIC BEGIN *************************************************/
		deleteCoordBtn.addClickListener(event -> {
			IcrashSystem sys = IcrashSystem.getInstance();

			try {
				sys.oeDeleteCoordinator(new DtCoordinatorID(new PtString(idCoordDel.getValue())));
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			idCoordDel.setValue("");
			idCoordDel.focus();
		});
		/************************************************* DELETE COORDINATOR FORM LOGIC END *************************************************/
    }
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
	
	// for demonstration only, as it is not in the spec! so should remove that for compliance
	public static void oeLogoutReturnedFalse() {
		log.info("oeLogout returned false! Some of its pre- or postconditions were not satisfied. Try again.");
	}
	
	public class StringToPtStringConverter implements Converter<String, PtString> {

		private static final long serialVersionUID = 8637366394272581738L;

		public Class<PtString> getModelType() {
	        return PtString.class;
	    }

	    public Class<String> getPresentationType() {
	        return String.class;
	    }

		@Override
		public PtString convertToModel(String value, Class<? extends PtString> targetType, Locale locale)
				throws com.vaadin.data.util.converter.Converter.ConversionException {
			if (value == null) {
	            return null;
	        }
	        return new PtString(value);
		}

		@Override
		public String convertToPresentation(PtString value, Class<? extends String> targetType, Locale locale)
				throws com.vaadin.data.util.converter.Converter.ConversionException {
			if (targetType == null) {
	            return null;
	        } else {
	            return value.getValue();
	        }
		}
	}
}