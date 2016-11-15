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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design;

import java.io.Serializable;

public class ActorMessageBean implements Serializable {
	private static final long serialVersionUID = -942432626544007040L;
	private String inputEvent;
	private String message;
	private String phoneNumber;

	public ActorMessageBean(String str, String phoneNumber, String msg) {
		setInputEvent(str);
		setPhoneNumber(phoneNumber);
		setMessage(msg);
	}

	public ActorMessageBean(String str, String msg) {
		setInputEvent(str);
		setPhoneNumber("");
		setMessage(msg);
	}

	public String getInputEvent() {
		return inputEvent;
	}
	public void setInputEvent(String inputEvent) {
		this.inputEvent = inputEvent;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
