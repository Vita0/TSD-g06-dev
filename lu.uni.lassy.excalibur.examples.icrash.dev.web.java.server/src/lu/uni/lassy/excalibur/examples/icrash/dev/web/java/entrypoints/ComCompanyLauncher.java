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
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.ComCompanyView;

@Push
@PreserveOnRefresh
@Theme("valo")
@Title("iCrash Communication Company")
public class ComCompanyLauncher extends UI {

	private static final long serialVersionUID = -4350213176879392663L;
	public final static String comCompaniesName = "comcompanies";
	
	@WebServlet(value = "/"+comCompaniesName+"/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = ComCompanyLauncher.class)
	public static class ComCompanyServlet extends VaadinServlet {
		private static final long serialVersionUID = 8810368015942929671L;
	}
	
	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		
		ViewProvider comCompaniesViewProvider = new ViewProvider() {
			private static final long serialVersionUID = -7990230620031142257L;

			@Override
			public String getViewName(String viewAndParameters) {
				return viewAndParameters;
			}
			
			@Override
			public View getView(String viewName) {
			
			/* now, when those 2 lines are commented, it gives NullPointerException
			 * when /comcompanies/* path is called and Creator was not launched before.
			 * The reason - IcrashSystem.comCompanyViewNames is null (not initialized) */ 
				
			//	if (viewName.isEmpty())
			//		return new ComCompanyErrorView();
				
				for (int i=0; i<(IcrashSystem.comCompanyViewNames.size()); i++) {
					if (viewName.equals(IcrashSystem.comCompanyViewNames.get(i)))
						return new ComCompanyView(viewName);
				}
				
				// return new ComCompanyErrorView();
				return null;
			}
		};
		
		// format for views: http://localhost:8080/iCrash/comcompanies#!00
		getNavigator().addProvider(comCompaniesViewProvider);
	}
}