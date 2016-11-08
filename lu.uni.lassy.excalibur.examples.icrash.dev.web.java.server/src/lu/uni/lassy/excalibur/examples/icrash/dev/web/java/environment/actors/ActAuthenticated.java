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

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.ActorMessageBean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class ActAuthenticated implements Serializable {
	
	// Holds actual data about input events and messagesDataSource for this actor
	// Serves as a data source for table to visualize that data
	private BeanItemContainer<ActorMessageBean> messagesDataSource = new BeanItemContainer<ActorMessageBean>(ActorMessageBean.class);
	private UI ui;

	DtLogin aDtLogin;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	public ActAuthenticated(DtLogin aDtLogin) {
		this.aDtLogin = aDtLogin;
	}
	
	public DtLogin getName() {
		return this.aDtLogin;
	}

	public PtBoolean oeLogin(DtLogin aDtLogin, DtPassword aDtPassword) throws Exception {
		
		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);
		
		log.info("message ActAuthenticated.oeLogin sent to system");
		PtBoolean res = sys.oeLogin(aDtLogin, aDtPassword);
		
		if(res.getValue() == true) 
			log.info("operation oeLogin successfully executed by the system");
		
		return res;
	}
	
	public PtBoolean oeLogout() throws Exception {

		IcrashSystem sys = IcrashSystem.getInstance();
		
		//set up ActAuthenticated instance that performs the request
		sys.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAuthenticated.oeLogout sent to system");
		PtBoolean res = sys.oeLogout();

		if(res.getValue() == true) 
			log.info("operation oeLogout successfully executed by the system");
		
		return res;
	}

	public PtBoolean ieMessage(PtString aMessage) {
		
		log.info("message ActAuthenticated.ieMessage received from system");
		log.info("ieMessage is: " + aMessage.getValue());

		if (getActorUI() == null)
			log.debug("UI is NULL!!!!!!!");
		
		getActorUI().access(() ->
			getMessagesDataSource().addBean(new ActorMessageBean("ieMessage", aMessage.getValue()))
				);

		return new PtBoolean(true);
	}

	public BeanItemContainer<ActorMessageBean> getMessagesDataSource() {
		return messagesDataSource;
	}

	public void setMessagesDataSource(BeanItemContainer<ActorMessageBean> messagesDataSource) {
		this.messagesDataSource = messagesDataSource;
	}

	public UI getActorUI() {
		return ui;
	}

	public void setActorUI(UI ui) {
		this.ui = ui;
	}
}