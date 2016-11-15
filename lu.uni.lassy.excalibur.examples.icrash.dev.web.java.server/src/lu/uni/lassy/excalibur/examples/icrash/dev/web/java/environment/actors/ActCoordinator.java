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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors;

import org.apache.log4j.Logger;
import java.util.ArrayList;

import com.vaadin.data.util.BeanItemContainer;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.ActorMessageBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.AlertBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.CrisisBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCrisisID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtFamilyPhoneNumbers;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class ActCoordinator extends ActAuthenticated {

	private static final long serialVersionUID = 7357534472408305825L;
	private BeanItemContainer<AlertBean> alertsContainer = new BeanItemContainer<AlertBean>(AlertBean.class);
	private BeanItemContainer<CrisisBean> crisesContainer = new BeanItemContainer<CrisisBean>(CrisisBean.class);

	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public ActCoordinator(DtLogin aDtLogin) {
		super(aDtLogin);
	}
	
	public PtBoolean oeValidateAlert(DtAlertID aDtAlertID) {
		
		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeValidateAlert sent to system");
		PtBoolean res = sys.oeValidateAlert(aDtAlertID);
			
		if(res.getValue() == true)
			log.info("operation oeValidateAlert successfully executed by the system");

		return res;
	}

	public PtBoolean oeInvalidateAlert(DtAlertID aDtAlertID) {

		IcrashSystem sys = IcrashSystem.getInstance();

		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeInvalidateAlert sent to system");
		PtBoolean res = sys.oeInvalidateAlert(aDtAlertID);

		if(res.getValue() == true)
			log.info("operation oeInvalidateAlert successfully executed by the system");

		return res;
	}
	
	public PtBoolean oeGetCrisisSet(EtCrisisStatus aEtCrisisStatus) {
		
		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeGetCrisisSet sent to system");
		PtBoolean res = sys.oeGetCrisisSet(aEtCrisisStatus);
			
		if(res.getValue() == true)
			log.info("operation oeGetCrisisSet successfully executed by the system");

		return res;
	}

	public PtBoolean oeSetCrisisHandler(DtCrisisID aDtCrisisID) {
	
		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSetCrisisHandler sent to system");
		PtBoolean res = sys.oeSetCrisisHandler(aDtCrisisID);
			
		if(res.getValue() == true)
			log.info("operation oeSetCrisisHandler successfully executed by the system");

		return res;
	}

	public PtBoolean oeSetCrisisStatus(DtCrisisID aDtCrisisID, EtCrisisStatus aEtCrisisStatus) {
		
		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSetCrisisStatus sent to system");
		PtBoolean res = sys.oeSetCrisisStatus(aDtCrisisID, aEtCrisisStatus);
			
		if(res.getValue() == true)
			log.info("operation oeSetCrisisStatus successfully executed by the system");

		return res;
	}

	synchronized public PtBoolean oeSetCrisisType(DtCrisisID aDtCrisisID, EtCrisisType aEtCrisisType) {

		IcrashSystem sys = IcrashSystem.getInstance();

		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSetCrisisType sent to system");
		PtBoolean res = sys.oeSetCrisisType(aDtCrisisID, aEtCrisisType);

		if(res.getValue() == true)
			log.info("operation oeSetCrisisType successfully executed by the system");

		return res;
	}

	public PtBoolean oeReportOnCrisis(DtCrisisID aDtCrisisID,DtComment aDtComment, PtBoolean aDtCrisisSendSmsFamily) {
	
		IcrashSystem sys = IcrashSystem.getInstance();

		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeReportOnCrisis sent to system");
		PtBoolean res = sys.oeReportOnCrisis(aDtCrisisID, aDtComment, aDtCrisisSendSmsFamily);
			
		if(res.getValue() == true && aDtCrisisSendSmsFamily.getValue() == false) {
			log.info("operation oeReportOnCrisis successfully executed by the system");
		}
		else if (res.getValue() == true) {
			log.info("operation oeReportOnCrisis successfully executed by the system. Report was sent to family members.");
		}

		return res;

	}

	public PtBoolean oeAddFamilyNumbersOnCrisis(DtCrisisID aDtCrisisID, DtFamilyPhoneNumbers aDtFamilyPhoneNumbers) {
		
		IcrashSystem sys = IcrashSystem.getInstance();

		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeAddFamilyNumberOnCrisis sent to system");
		PtBoolean res = sys.oeAddFamilyNumbersOnCrisis(aDtCrisisID, aDtFamilyPhoneNumbers);
			
		if(res.getValue() == true)
			log.info("operation oeAddFamilyNumberOnCrisis successfully executed by the system");

		return res;
	}

	public PtBoolean oeCloseCrisis(DtCrisisID aDtCrisisID) {
	
		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeCloseCrisis sent to system");
		PtBoolean res = sys.oeCloseCrisis(aDtCrisisID);
			
		if(res.getValue() == true)
			log.info("operation oeCloseCrisis successfully executed by the system");

		return res;
	}

	public PtBoolean oeGetAlertsSet(EtAlertStatus aEtAlertStatus) {
		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);
	
		log.info("message ActCoordinator.oeGetAlertsSet sent to system");
		PtBoolean res = sys.oeGetAlertsSet(aEtAlertStatus);
		
		if(res.getValue() == true)
			log.info("operation oeGetAlertsSet successfully executed by the system");
		return res;
	}

	public PtBoolean ieSendAnAlert(CtAlert aCtAlert) {

		log.info("message ActCoordinator.ieSendAnAlert received from system");
		log.info("alert id '"	+ aCtAlert.id.value.getValue().toString() + "' "+
				" with comment '"+ aCtAlert.comment.value.getValue() +"'"+
				" in status '"+ aCtAlert.status.toString()+"'");
		
		String alertStatus = null;
		if (aCtAlert.status.equals(EtAlertStatus.pending))
			alertStatus = "Pending";
		if (aCtAlert.status.equals(EtAlertStatus.valid))
			alertStatus = "Valid";
		if (aCtAlert.status.equals(EtAlertStatus.invalid))
			alertStatus = "Invalid";
		
		getActorUI().access(() -> 
			getMessagesDataSource().addBean(new ActorMessageBean("ieSendAnAlert", "Alert "+aCtAlert.id.value.getValue()+" was sent"))
		);
		
		return new PtBoolean(true);
	}
	
	public PtBoolean ieSendACrisis(CtCrisis aCtCrisis) {

		log.info("message ActCoordinator.ieSendACrisis received from system");
		log.info("crisis id '"	+ aCtCrisis.id.value.getValue().toString() + "' "+
				"in status '"+ aCtCrisis.status.toString()+"'");
		
		String crisisStatus = null;
		if (aCtCrisis.status.equals(EtCrisisStatus.closed))
			crisisStatus = "Closed";
		if (aCtCrisis.status.equals(EtCrisisStatus.handled))
			crisisStatus = "Handled";
		if (aCtCrisis.status.equals(EtCrisisStatus.solved))
			crisisStatus = "Solved";
		if (aCtCrisis.status.equals(EtCrisisStatus.pending))
			crisisStatus = "Pending";
		
		String crisisType = null;
		if (aCtCrisis.type.equals(EtCrisisType.small))
			crisisType = "Small";
		if (aCtCrisis.type.equals(EtCrisisType.medium))
			crisisType = "Medium";
		if (aCtCrisis.type.equals(EtCrisisType.huge))
			crisisType = "Huge";
		
		getActorUI().access(() -> 
			getMessagesDataSource().addBean(new ActorMessageBean("ieSendACrisis", "Crisis "+aCtCrisis.id.value.getValue()+" was sent"))
		);
		
		return new PtBoolean(true);
	}

	public BeanItemContainer<AlertBean> getAlertsContainer() {
		return alertsContainer;
	}

	public BeanItemContainer<CrisisBean> getCrisesContainer() {
		return crisesContainer;
	}
}
