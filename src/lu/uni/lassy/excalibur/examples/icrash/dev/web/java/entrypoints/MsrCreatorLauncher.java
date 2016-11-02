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

import org.apache.log4j.Logger;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.MsrCreatorView;

@PreserveOnRefresh
@Theme("valo")
@Title("iCrash Messir Creator")
public class MsrCreatorLauncher extends UI {

	private static final long serialVersionUID = 9188734963433264744L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	public final static String creatorName = "creator";
	
	@WebServlet(value = "/"+creatorName+"/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MsrCreatorLauncher.class)
	public static class MsrCreatorServlet extends VaadinServlet {
		private static final long serialVersionUID = 2303358012204847688L;
	}
	
	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView("", new MsrCreatorView());
	}
}