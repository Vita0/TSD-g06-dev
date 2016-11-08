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

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

@Theme("valo")
@Title("iCrash Launcher")
public class RootLauncher extends UI {

	private static final long serialVersionUID = 3359769960577523132L;
	transient Logger log = Log4JUtils.getInstance().getLogger();

	@WebServlet(value = {"/*", "/VAADIN/*"}, asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = RootLauncher.class)
	public static class RootServlet extends VaadinServlet {
		private static final long serialVersionUID = 6998747012253596448L;
	}
	
	@Override
	protected void init(VaadinRequest request) {
		
		// currently temporary redirection to Messir Creator
		// in future this root address (http://localhost:8080/iCrash)
		// might be used for a yet non-existent GUI monitor instead...

		// Hints:
		// currentURL = "http://localhost:8080/iCrash/"
		// MsrCreatorLauncher.creatorName = "creator"
		String currentURL = Page.getCurrent().getLocation().toString();
		
		int nrSlash = 0;
		int i;
		for (i=0; i<currentURL.length(); i++) {
			if (currentURL.charAt(i) == '/')
				nrSlash++;
			
			if (nrSlash == 4)
				break;
		}
		
		String iCrashURL = currentURL.substring(0, i);
		Page.getCurrent().setLocation(iCrashURL + '/' + MsrCreatorLauncher.creatorName);
	}
}