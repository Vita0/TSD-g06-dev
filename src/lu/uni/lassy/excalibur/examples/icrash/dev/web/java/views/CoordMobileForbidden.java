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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

// currently not used at all

public class CoordMobileForbidden extends VerticalLayout implements View, Serializable {
	private static final long serialVersionUID = 5731267240795976305L;

	public CoordMobileForbidden() {
		setSizeFull();
		Label forbiddenMessage = new Label();
		// forbiddenMessage.setContentMode(ContentMode.HTML);
		// forbiddenMessage.setValue("<h1>The following Messir operation is not allowed in this context</h1>");
		forbiddenMessage.setValue("The following Messir operation is not allowed in this context");
		forbiddenMessage.setSizeUndefined();
		addComponent(forbiddenMessage);
		setComponentAlignment(forbiddenMessage, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
	}

}
