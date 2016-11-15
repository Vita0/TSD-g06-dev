/*******************************************************************************
 * Copyright (c) 2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	   Alfredo Capozucca - initial API and implementation
 *     Anton Nikonienkov - iCrash HTML5 API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.ActorMessageBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtComment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtHumanKind;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class ActComCompany implements Serializable {
	private static final long serialVersionUID = 300328391242489155L;

	/** The name of the communication company, this allows us to work out which actor belongs to which communication company.
	 * It should be a 1 to 1 relationship
	 */
	private String _name;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	// Holds actual data about input events and messagesDataSource for this actor
	// Serves as a data source for table to visualize that data
	private BeanItemContainer<ActorMessageBean> messagesDataSource = new BeanItemContainer<ActorMessageBean>(ActorMessageBean.class);
	private UI ui;
	
	/**
	 * Instantiates a new server side actor for the communication company. This is the implemented version of the actor
	 *
	 * @param n The name of the communication company this actor should be associated with
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActComCompany(String n) {
		super();
		_name = n;
	}

	public String getName() {
		return _name;
	}
	
	public PtBoolean oeAlert(EtHumanKind aEtHumanKind,
			DtDate aDtDate, DtTime aDtTime, DtPhoneNumber aDtPhoneNumber,
			DtGPSLocation aDtGPSLocation, DtComment aDtComment) {

		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ComCompany instance that performs the request
		sys.setCurrentConnectedComCompany(this);

		log.info("message ActComCompany.oeAlert sent to system");
		PtBoolean res = sys.oeAlert(aEtHumanKind, aDtDate,
				aDtTime, aDtPhoneNumber, aDtGPSLocation, aDtComment);

		if (res.getValue() == true) 
			log.info("operation oeAlert successfully executed by the system");

		return new PtBoolean(true);
	}

	public PtBoolean ieSmsSend(DtPhoneNumber aDtPhoneNumber, DtSMS aDtSMS) {

		log.info("message ActComCompany.ieSmsSend received from system");
		log.info("Phone number: " + aDtPhoneNumber.value.getValue());
		log.info("SMS: " + aDtSMS.value.getValue());

		getActorUI().access(() -> 
			getMessagesDataSource().addBean(new ActorMessageBean("ieSmsSend", aDtPhoneNumber.value.getValue(), aDtSMS.value.getValue()))
		);	
					
		return new PtBoolean(true);
	}

	public BeanItemContainer<ActorMessageBean> getMessagesDataSource() {
		return messagesDataSource;
	}

	public void setMessagesDataSource(BeanItemContainer<ActorMessageBean> messages) {
		this.messagesDataSource = messages;
	}

	public UI getActorUI() {
		return ui;
	}

	public void setActorUI(UI ui) {
		this.ui = ui;
	}
}
