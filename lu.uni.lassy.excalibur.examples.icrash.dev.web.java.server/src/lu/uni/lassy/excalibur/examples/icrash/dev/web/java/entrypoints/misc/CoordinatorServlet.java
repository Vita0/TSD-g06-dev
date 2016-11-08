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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.misc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.annotations.VaadinServletConfiguration;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.CoordinatorLauncher;

@WebServlet(value = "/"+CoordinatorServlet.coordinatorsName+"/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = CoordinatorLauncher.class,
widgetset = "lu.uni.lassy.excalibur.examples.icrash.dev.web.java.widgetset.IcrashWidgetset")
public class CoordinatorServlet extends TouchKitServlet {
	private static final long serialVersionUID = -1668773872762253472L;
	public final static String coordinatorsName = "coordinators";
	
	// private CoordinatorUIProvider coordUIProvider = new CoordinatorUIProvider();
	
	@Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
    }
}
