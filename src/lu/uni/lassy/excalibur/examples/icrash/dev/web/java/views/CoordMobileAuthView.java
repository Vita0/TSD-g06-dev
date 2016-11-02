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

import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.AlertBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.CrisisBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class CoordMobileAuthView extends TabBarView implements View, Serializable {
	private static final long serialVersionUID = -9066109329366283977L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
	
	Grid alertsTable;
	Grid crisesTable;
	
	NativeSelect alertStatus;
	NativeSelect crisesStatus;
	
	String thisCoordID = null;

	public CoordMobileAuthView(String CoordID) {

		CtCoordinator ctCoordinator = (CtCoordinator) sys.getCtCoordinator(new DtCoordinatorID(new PtString(CoordID)));
		ActCoordinator actCoordinator = sys.getActCoordinator(ctCoordinator);
		
		actCoordinator.setActorUI(UI.getCurrent());
		env.setActCoordinator(actCoordinator.getName(), actCoordinator);
			
		IcrashSystem.assCtAuthenticatedActAuthenticated.replace(ctCoordinator, actCoordinator);
		IcrashSystem.assCtCoordinatorActCoordinator.replace(ctCoordinator, actCoordinator);
		
		thisCoordID = CoordID;
		
		setResponsive(true);
		setWidth("100%");
		
		NavigationBar alertsBar = new NavigationBar();
		VerticalComponentGroup alertsContent = new VerticalComponentGroup();
		alertsContent.setWidth("100%");
		alertsContent.setResponsive(true);
		
		HorizontalLayout alertButtons1 = new HorizontalLayout();
		HorizontalLayout alertButtons2 = new HorizontalLayout();
		//alertButtons.setMargin(true);
		//alertButtons.setSpacing(true);
		
		alertsBar.setCaption("Coordinator "+ctCoordinator.login.toString());
		// NavigationButton logoutBtn1 = new NavigationButton("Logout");
		Button logoutBtn1 = new Button("Logout");
		alertsBar.setRightComponent(logoutBtn1);
		
		alertsTable = new Grid();
		alertsTable.setContainerDataSource(actCoordinator.getAlertsContainer());
		alertsTable.setColumnOrder("ID", "date", "time", "longitude", "latitude", "comment", "status");
		alertsTable.setSelectionMode(SelectionMode.SINGLE);

		alertsTable.setWidth("100%");
		alertsTable.setResponsive(true);
		//alertsTable.setSizeUndefined();
		
		alertsTable.setImmediate(true);

		Grid inputEventsTable1 = new Grid();
		inputEventsTable1.setContainerDataSource(actCoordinator.getMessagesDataSource());
		inputEventsTable1.setWidth("100%");
		inputEventsTable1.setResponsive(true);
		
		alertsContent.addComponents(alertsBar, alertButtons1, alertButtons2, alertsTable, inputEventsTable1);
		
		Tab alertsTab = this.addTab(alertsContent);
		alertsTab.setCaption("Alerts");
		
		alertStatus = new NativeSelect();
		alertStatus.setNullSelectionAllowed(false);
		alertStatus.addItems("Pending", "Valid", "Invalid");
		alertStatus.setImmediate(true);
		
		alertStatus.select("Pending");
		
		Button validateAlertBtn = new Button("Validate");
		Button invalidateAlertBtn = new Button("Invalidate");
		Button getAlertsSetBtn = new Button("Get alerts set");
		
		validateAlertBtn.setImmediate(true);
		invalidateAlertBtn.setImmediate(true);
		
		validateAlertBtn.addClickListener(event -> {
			AlertBean selectedAlertBean = (AlertBean) alertsTable.getSelectedRow();
				
			Integer thisAlertID = new Integer(selectedAlertBean.getID());
			PtBoolean res;
			res = sys.oeValidateAlert(new DtAlertID(new PtString(thisAlertID.toString())));
		});
		
		invalidateAlertBtn.addClickListener(event -> {
			AlertBean selectedAlertBean = (AlertBean) alertsTable.getSelectedRow();
			Integer thisAlertID = new Integer(selectedAlertBean.getID());
			PtBoolean res;
			res = sys.oeInvalidateAlert(new DtAlertID(new PtString(thisAlertID.toString())));
		});

		getAlertsSetBtn.addClickListener(event -> {
			if (alertStatus.getValue().toString().equals("Pending"))
				actCoordinator.oeGetAlertsSet(EtAlertStatus.pending);
			else if (alertStatus.getValue().toString().equals("Valid"))
				actCoordinator.oeGetAlertsSet(EtAlertStatus.valid);
			else if (alertStatus.getValue().toString().equals("Invalid"))
				actCoordinator.oeGetAlertsSet(EtAlertStatus.invalid); 
		});
		
		alertButtons1.addComponents(validateAlertBtn, invalidateAlertBtn);
		alertButtons2.addComponents(getAlertsSetBtn, alertStatus);

		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		NavigationBar crisesBar = new NavigationBar();
		VerticalComponentGroup crisesContent = new VerticalComponentGroup();
		crisesContent.setWidth("100%");
		crisesContent.setResponsive(true);
		
		HorizontalLayout crisesButtons1 = new HorizontalLayout();
		HorizontalLayout crisesButtons2 = new HorizontalLayout();
		crisesBar.setCaption("Coordinator "+ctCoordinator.login.toString());
		//NavigationButton logoutBtn2 = new NavigationButton("Logout");
		Button logoutBtn2 = new Button("Logout");
		crisesBar.setRightComponent(logoutBtn2);
		
		crisesTable = new Grid();
		crisesTable.setContainerDataSource(actCoordinator.getCrisesContainer());
		crisesTable.setColumnOrder("ID", "date", "time", "type", "longitude", "latitude", "comment", "status");
		crisesTable.setSelectionMode(SelectionMode.SINGLE);

		crisesTable.setWidth("100%");
		//crisesTable.setSizeUndefined();
		
		crisesTable.setImmediate(true);
		crisesTable.setResponsive(true);
		
		Grid inputEventsTable2 = new Grid();
		inputEventsTable2.setContainerDataSource(actCoordinator.getMessagesDataSource());
		inputEventsTable2.setWidth("100%");
		inputEventsTable2.setResponsive(true);
		
		crisesContent.addComponents(crisesBar, crisesButtons1, crisesButtons2, crisesTable, inputEventsTable2);
		
		Tab crisesTab = this.addTab(crisesContent);
		crisesTab.setCaption("Crises");
		
		Button handleCrisesBtn = new Button("Handle");
		Button reportOnCrisisBtn = new Button("Report");
		Button changeCrisisStatusBtn = new Button("Status");
		Button closeCrisisBtn = new Button("Close");
		Button getCrisesSetBtn = new Button("Get crises set");
		crisesStatus = new NativeSelect();
		
		handleCrisesBtn.setImmediate(true);
		reportOnCrisisBtn.setImmediate(true);
		changeCrisisStatusBtn.setImmediate(true);
		closeCrisisBtn.setImmediate(true);
		getCrisesSetBtn.setImmediate(true);
		crisesStatus.setImmediate(true);
		
		crisesStatus.addItems("Pending", "Handled", "Solved", "Closed");
		crisesStatus.setNullSelectionAllowed(false);
		crisesStatus.select("Pending");
		
		crisesButtons1.addComponents(handleCrisesBtn, reportOnCrisisBtn, changeCrisisStatusBtn);
		crisesButtons2.addComponents(closeCrisisBtn, getCrisesSetBtn, crisesStatus);
		
		////////////////////////////////////////

		Window reportCrisisSubWindow = new Window();
		reportCrisisSubWindow.setClosable(false);
		reportCrisisSubWindow.setResizable(false);
		reportCrisisSubWindow.setResponsive(true);
		VerticalLayout reportLayout = new VerticalLayout();
		reportLayout.setMargin(true);
		reportLayout.setSpacing(true);
		reportCrisisSubWindow.setContent(reportLayout);
		TextField crisisID = new TextField();
		TextField reportText = new TextField();
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		Button reportCrisisBtn = new Button("Report");
		reportCrisisBtn.setClickShortcut(KeyCode.ENTER);
		reportCrisisBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		Button cancelBtn = new Button("Cancel");
		buttonsLayout.addComponents(reportCrisisBtn, cancelBtn);
		buttonsLayout.setSpacing(true);
		reportLayout.addComponents(crisisID, reportText, buttonsLayout);

		cancelBtn.addClickListener(event -> {
			reportCrisisSubWindow.close();
			reportText.clear();
		});

		reportCrisisBtn.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());
			actCoordinator.oeReportOnCrisis(
					new DtCrisisID(
							new PtString(
									thisCrisisID.toString())),
					new DtComment(
							new PtString(
									reportText.getValue())));
				
			reportCrisisSubWindow.close();
			reportText.clear();
		});
		
		////////////////////////////////////////

		Window changeCrisisStatusSubWindow = new Window();
		changeCrisisStatusSubWindow.setClosable(false);
		changeCrisisStatusSubWindow.setResizable(false);
		changeCrisisStatusSubWindow.setResponsive(true);
		VerticalLayout statusLayout = new VerticalLayout();
		statusLayout.setMargin(true);
		statusLayout.setSpacing(true);
		changeCrisisStatusSubWindow.setContent(statusLayout);
		TextField crisisID1 = new TextField();
		
		NativeSelect crisisStatus = new NativeSelect("crisis status");
		crisisStatus.addItems("Pending", "Handled", "Solved", "Closed");
		crisisStatus.setNullSelectionAllowed(false);
		crisisStatus.select("Pending");
		
		HorizontalLayout buttonsLayout1 = new HorizontalLayout();
		Button changeCrisisStatusBtn1 = new Button("Change status");
		changeCrisisStatusBtn1.setClickShortcut(KeyCode.ENTER);
		changeCrisisStatusBtn1.addStyleName(ValoTheme.BUTTON_PRIMARY);
		Button cancelBtn1 = new Button("Cancel");
		buttonsLayout1.addComponents(changeCrisisStatusBtn1, cancelBtn1);
		buttonsLayout1.setSpacing(true);
		statusLayout.addComponents(crisisID1, crisisStatus, buttonsLayout1);
		
		cancelBtn1.addClickListener(event -> changeCrisisStatusSubWindow.close());
		
		changeCrisisStatusBtn1.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());

			EtCrisisStatus statusToPut = null;
				
			if (crisisStatus.getValue().toString().equals("Pending"))
				statusToPut = EtCrisisStatus.pending;
			if (crisisStatus.getValue().toString().equals("Handled"))
				statusToPut = EtCrisisStatus.handled;
			if (crisisStatus.getValue().toString().equals("Solved"))
				statusToPut = EtCrisisStatus.solved;
			if (crisisStatus.getValue().toString().equals("Closed"))
				statusToPut = EtCrisisStatus.closed;
				
			PtBoolean res = actCoordinator.oeSetCrisisStatus(
					new DtCrisisID(
							new PtString(
									thisCrisisID.toString())),
					statusToPut
							);
				
			changeCrisisStatusSubWindow.close();
		});
		
		////////////////////////////////////////
		
		handleCrisesBtn.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());
			PtBoolean res = actCoordinator.oeSetCrisisHandler(new DtCrisisID(new PtString(thisCrisisID.toString())));
		});
		
		reportOnCrisisBtn.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());
			reportCrisisSubWindow.center();
			crisisID.setValue(thisCrisisID.toString());
			crisisID.setEnabled(false);
			reportText.focus();
			UI.getCurrent().addWindow(reportCrisisSubWindow);
		});
		
		changeCrisisStatusBtn.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());
			changeCrisisStatusSubWindow.center();
			crisisID1.setValue(thisCrisisID.toString());
			crisisID1.setEnabled(false);
			crisisStatus.focus();
			UI.getCurrent().addWindow(changeCrisisStatusSubWindow);
		});

		closeCrisisBtn.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());
			PtBoolean res = actCoordinator.oeCloseCrisis(new DtCrisisID(new PtString(thisCrisisID.toString())));
		});
		
		getCrisesSetBtn.addClickListener(event -> {
			if (crisesStatus.getValue().toString().equals("Closed"))
				actCoordinator.oeGetCrisisSet(EtCrisisStatus.closed);
			if (crisesStatus.getValue().toString().equals("Handled"))
				actCoordinator.oeGetCrisisSet(EtCrisisStatus.handled);
			if (crisesStatus.getValue().toString().equals("Solved"))
				actCoordinator.oeGetCrisisSet(EtCrisisStatus.solved);
			if (crisesStatus.getValue().toString().equals("Pending"))
				actCoordinator.oeGetCrisisSet(EtCrisisStatus.pending);
		});
		
		ClickListener logoutAction = event -> {
			PtBoolean res;
			try {
				res = actCoordinator.oeLogout();
				if (res.getValue()) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Page.getCurrent().reload();
		};	
		
		logoutBtn1.addClickListener(logoutAction);
		logoutBtn2.addClickListener(logoutAction);
	} 
	
	@Override
	public void enter(ViewChangeEvent event) {
	}
}
