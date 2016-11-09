/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 *     Anton Nikonienkov - iCrash HTML5 API and implementation     
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

/**
 * The Class CtCrisis that holds the crisis details.
 */
public class CtCrisis implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	/** The id of the crisis. */
	public DtCrisisID id;
	
	/** The type of the crisis. */
	public EtCrisisType type;
	
	/** The current status of the crisis. */
	public EtCrisisStatus status;
	
	/** The location of the accident that is associated to the crisis. */
	public DtGPSLocation location;
	
	/** The date and time of the accident that is associated to the crisis. */
	public DtDateAndTime instant;
	
	/** The family numbers associated with the crisis. */
	public ArrayList<DtPhoneNumber> familyNumbers;
	
	/** The comment associated with the crisis. Often refered to as the report. */
	public DtComment comment;

	/**
	 * Initialises the crisis.
	 *
	 * @param aId the id of the crisis
	 * @param aType the type of the crisis
	 * @param aStatus the status of the crisis
	 * @param aLocation the location of the accident associated with the crisis
	 * @param aInstant the date and time of the accident associated with the crisis
	 * @param aComment the comment of the crisis
	 * @return the success of the initialisation
	 */
	public PtBoolean init(DtCrisisID aId, EtCrisisType aType,
			EtCrisisStatus aStatus, DtGPSLocation aLocation,
			DtDateAndTime aInstant, ArrayList<DtPhoneNumber> aFamilyNumbers, DtComment aComment) {

		id = aId;
		type = aType;
		status = aStatus;
		location = aLocation;
		instant = aInstant;
		familyNumbers = aFamilyNumbers;
		comment = aComment;
		
		return new PtBoolean(true);

	}
	
	/**
	 * If this crisis is still in the pending status and the system time minus the last reminder time
	 * is greater than the standard crisis reminder period then we return true.
	 *
	 * @return If the time since last handled is greater than the standard reminder period, return true
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server is not bound
	 */
	public PtBoolean handlingDelayPassed() {
		if(status.toString().equals(EtCrisisStatus.pending.toString())){
		
			IcrashSystem sys = IcrashSystem.getInstance();	  
			// TODO: change CtState from static to instances for spec conformance!!!
	        
			double currentClockSecondsQty = CtState.clock.toSecondsQty().getValue();
			double vpLastReminderSecondsQty = CtState.vpLastReminder.toSecondsQty().getValue();
			if((currentClockSecondsQty - vpLastReminderSecondsQty)>CtState.crisisReminderPeriod.value.getValue()) {
				return new PtBoolean(true);
			}	
		}

		return new PtBoolean(false);
	}
	
	/**
	 * If this crisis is still in the pending status and the system time minus the last reminder time
	 * is greater than the max standard crisis reminder period then we return true.
	 *
	 * @return If the time since last handled is greater than the max standard reminder period, return true
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server is not bound
	 */
	public PtBoolean maxHandlingDelayPassed() throws RemoteException, NotBoundException {
	
		if(status.toString().equals(EtCrisisStatus.pending.toString())){
			
			IcrashSystem sys = IcrashSystem.getInstance();	  
			// TODO: change CtState from static to instances for spec conformance!!!
			
			double currentClockSecondsQty = CtState.clock.toSecondsQty().getValue();
			double crisisInstantSecondsQty = instant.toSecondsQty().getValue();
			
			if((currentClockSecondsQty - crisisInstantSecondsQty)>CtState.maxCrisisReminderPeriod.value.getValue()) {
				return new PtBoolean(true);
			}	
		}

		return new PtBoolean(false);
	}
	
	/**
	 * Used to provide a given coordinator with current alert information.
	 *
	 * @param aActCoordinator the actor coordinator to send the information to
	 * @return the success of the method
	 * @throws RemoteException Thrown if the remote destination is unreachable
	 */
	public PtBoolean isSentToCoordinator(ActCoordinator aActCoordinator) {
		aActCoordinator.ieSendACrisis(this);
		return new PtBoolean(true);
	}
	
	/**
	 * Used to allocate a crisis to a coordinator if any or to alert the administrator of crisis waiting to be handled.
	 *
	 * @return the success of the method
	 * @throws RemoteException  Thrown if the remote destination is unreachable
	 * @throws NotBoundException Thrown if the remote destination hasn't been bound
	 */
	public PtBoolean isAllocatedIfPossible() {
		
		IcrashSystem sys = IcrashSystem.getInstance();	  
		IcrashEnvironment env = IcrashEnvironment.getInstance();
		// TODO: change CtState from static to instances for spec conformance!!!		
		
		double currentClockSecondsQty = CtState.clock.toSecondsQty().getValue();
		double crisisInstantSecondsQty = instant.toSecondsQty().getValue();
		
		if((currentClockSecondsQty - crisisInstantSecondsQty)>CtState.maxCrisisReminderPeriod.value.getValue()){
			
			CtCoordinator theCoordinator = sys.getRandomCtCoordinator();
			
			if(theCoordinator != null){			
				sys.bindCtCrisisCtCoordinator(this,theCoordinator);
				status = EtCrisisStatus.handled;
				String crisisId = this.id.value.getValue().toString();
				PtString aMessage = new PtString("You are now considered as handling the crisis having ID: '"+crisisId+"'");
				ActCoordinator theCoordinatorActor = env.getActCoordinator(theCoordinator.login);
				log.info("Message being sent to " + theCoordinator.login.value.getValue());
				theCoordinatorActor.ieMessage(aMessage);
			
			} else {
				PtString aMessage = new PtString("Please add new coordinators to handle pending crisis !");
				for(ActAdministrator admin:sys.getAllActAdministrators()) {
					admin.ieMessage(aMessage);
				}	
			}
			
			return new PtBoolean(true);
		}
		return new PtBoolean(false);

	}
	
	@Override
	public String toString(){
		return this.id.value.getValue();
	}
	
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof CtCrisis))
			return false;
		
		CtCrisis aCtCrisis = (CtCrisis)obj;

		if (!aCtCrisis.id.value.getValue().equals(this.id.value.getValue()))
			return false;
		
		if (!aCtCrisis.comment.value.getValue().equals(this.comment.value.getValue()))
			return false;
		
		if (!aCtCrisis.instant.toString().equals(this.instant.toString()))
			return false;
		
		if (aCtCrisis.location.latitude.value.getValue() != this.location.latitude.value.getValue())
			return false;
		
		if (aCtCrisis.location.longitude.value.getValue() != this.location.longitude.value.getValue())
			return false;
		
		if (!aCtCrisis.status.equals(this.status))
			return false;
		
		if (!aCtCrisis.type.equals(this.type))
			return false;

		if (!aCtCrisis.familyNumbers.equals(this.familyNumbers))
			return false;

		return true;
	}
	
	@Override
	public int hashCode(){
		return this.id.value.getValue().length();
	}
}
