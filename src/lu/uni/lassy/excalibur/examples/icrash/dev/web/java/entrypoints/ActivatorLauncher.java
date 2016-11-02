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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.ActivatorView;

@PreserveOnRefresh
@Theme("valo")
@Title("iCrash Activator")
public class ActivatorLauncher extends UI {

	private static final long serialVersionUID = -2466646466207880956L;
	public final static String activatorPageName = "activator";
	
	@WebServlet(value = "/"+activatorPageName+"/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ActivatorLauncher.class)
	public static class ActivatorServlet extends VaadinServlet {
		private static final long serialVersionUID = 11896235414101631L;
	}
	
	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView("", new ActivatorView());
	}
}