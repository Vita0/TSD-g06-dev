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
import java.util.HashMap;

import org.apache.log4j.Logger;

//import com.google.gwt.dev.util.collect.HashMap;
import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
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
import com.vaadin.ui.CheckBox;

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
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPhoneNumber;
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
		crisesTable.setColumnOrder("ID", "date", "time", "type", "longitude", "latitude", "familyNumbers", "comment", "status");
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
		Button familyNumbersOnCrisisBtn = new Button("Family phone numbers");
		Button changeCrisisStatusBtn = new Button("Status");
		Button closeCrisisBtn = new Button("Close");
		Button getCrisesSetBtn = new Button("Get crises set");
		crisesStatus = new NativeSelect();
		
		handleCrisesBtn.setImmediate(true);
		reportOnCrisisBtn.setImmediate(true);
		familyNumbersOnCrisisBtn.setImmediate(true);
		changeCrisisStatusBtn.setImmediate(true);
		closeCrisisBtn.setImmediate(true);
		getCrisesSetBtn.setImmediate(true);
		crisesStatus.setImmediate(true);
		
		crisesStatus.addItems("Pending", "Handled", "Solved", "Closed");
		crisesStatus.setNullSelectionAllowed(false);
		crisesStatus.select("Pending");
		
		crisesButtons1.addComponents(handleCrisesBtn, reportOnCrisisBtn, changeCrisisStatusBtn, familyNumbersOnCrisisBtn);
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
		CheckBox sendSmsFamily = new CheckBox("Send SMS to family members");
		HorizontalLayout layoutSendSMS = new HorizontalLayout();
		layoutSendSMS.addComponent(sendSmsFamily);
		reportLayout.addComponents(crisisID, reportText, layoutSendSMS, buttonsLayout);

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
									reportText.getValue())),
					new PtBoolean(sendSmsFamily.getValue()));
				
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

		HashMap<Button, TextField> phoneList = new HashMap<Button, TextField>();

		Window familyNumbersCrisisSubWindow = new Window();
		familyNumbersCrisisSubWindow.setClosable(false);
		familyNumbersCrisisSubWindow.setResizable(false);
		familyNumbersCrisisSubWindow.setResponsive(true);
		familyNumbersCrisisSubWindow.setHeight("250px");
		VerticalLayout familyNumbersLayout = new VerticalLayout();
		familyNumbersLayout.setMargin(true);
		familyNumbersLayout.setSpacing(true);
		familyNumbersCrisisSubWindow.setContent(familyNumbersLayout);
		TextField crisisID2 = new TextField();
		HorizontalLayout buttonsLayout2 = new HorizontalLayout();
		Button familyNumbersCrisisBtn = new Button("Save numbers");
		familyNumbersCrisisBtn.setClickShortcut(KeyCode.ENTER);
		familyNumbersCrisisBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		Button cancelBtn2 = new Button("Cancel");
		Button addPhone = new Button();
		addPhone.setIcon(FontAwesome.PLUS);
		addPhone.addStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonsLayout2.addComponents(addPhone, familyNumbersCrisisBtn, cancelBtn2);
		buttonsLayout2.setSpacing(true);
		
		cancelBtn2.addClickListener(event -> {
			familyNumbersCrisisSubWindow.close();
		});

		addPhone.addClickListener(event -> {
			fillFamilyNumbersLayout("", familyNumbersLayout, phoneList, familyNumbersCrisisSubWindow, true);
		});

		familyNumbersCrisisBtn.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());
			ArrayList<DtPhoneNumber> familyPhoneNumbers = new ArrayList<DtPhoneNumber>();
			for (TextField field: phoneList.values())
			{
				if (field.getValue().length() > 0)
				{
					DtPhoneNumber familyNumber = new DtPhoneNumber(
													new PtString(
															field.getValue()));
					if (familyNumber.is().getValue() == true) {
						familyPhoneNumbers.add(familyNumber);
					}
				}
			}
			actCoordinator.oeAddFamilyNumbersOnCrisis(
					new DtCrisisID(
							new PtString(
									thisCrisisID.toString())),
					familyPhoneNumbers);
				
			familyNumbersCrisisSubWindow.close();
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
			sendSmsFamily.setValue(false);
			if (selectedCrisisBean.getFamilyNumbers().size() > 0) {
				layoutSendSMS.setVisible(true);
			}
			else {
				layoutSendSMS.setVisible(false);
			}
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

		familyNumbersOnCrisisBtn.addClickListener(event -> {
			CrisisBean selectedCrisisBean = (CrisisBean) crisesTable.getSelectedRow();
			Integer thisCrisisID = new Integer(selectedCrisisBean.getID());
			familyNumbersCrisisSubWindow.center();
			crisisID2.setValue(thisCrisisID.toString());
			crisisID2.setEnabled(false);
			familyNumbersLayout.removeAllComponents();
			familyNumbersLayout.addComponents(crisisID2);
			phoneList.clear();
			for (String phone: selectedCrisisBean.getFamilyNumbers())
			{
				fillFamilyNumbersLayout(phone, familyNumbersLayout, phoneList, familyNumbersCrisisSubWindow, false);
			}
			fillFamilyNumbersLayout("", familyNumbersLayout, phoneList, familyNumbersCrisisSubWindow, false);
			familyNumbersLayout.addComponents(buttonsLayout2);
			UI.getCurrent().addWindow(familyNumbersCrisisSubWindow);
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

	private void fillFamilyNumbersLayout(String phone, VerticalLayout familyNumbersLayout, HashMap<Button, TextField> phoneList, Window familyNumbersCrisisSubWindow, boolean insertBeforeButtons) {
		
		TextField familyNumber = new TextField();
		Button deletePhone = new Button();
		deletePhone.setIcon(FontAwesome.TRASH_O);
		HorizontalLayout numberLayout = new HorizontalLayout();
		numberLayout.setSpacing(true);
		numberLayout.addComponents(familyNumber, deletePhone);
		if (insertBeforeButtons) {
			familyNumbersLayout.addComponent(numberLayout, familyNumbersLayout.getComponentCount() - 1);
		}
		else {
			familyNumbersLayout.addComponent(numberLayout);
		}
		familyNumber.setValue(phone);
		phoneList.put(deletePhone, familyNumber);
		deletePhone.addClickListener(event1 -> {
			phoneList.remove(deletePhone);
			familyNumbersLayout.removeComponent(numberLayout);
		});
	}
}
