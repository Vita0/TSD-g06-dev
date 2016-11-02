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
import com.vaadin.ui.VerticalLayout;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.AdminAuthView;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.AdminLoginView;

@Push
@Theme("valo")
@Title("iCrash Administrator")
public class AdministratorLauncher extends UI {
	private static final long serialVersionUID = 2686900851970148198L;
	public final static String adminPageName = "admin";
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();	
	
	ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
	CtAdministrator ctAdmin =  (CtAdministrator) sys.getCtAuthenticated(actAdmin);

	
	@WebServlet(value = "/"+adminPageName+"/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = AdministratorLauncher.class)
	public static class AdministratorServlet extends VaadinServlet {
		private static final long serialVersionUID = 3357527245315226146L;
	}

	@Override
	protected void init(VaadinRequest request) {
		
		actAdmin.setActorUI(getUI());
		env.setActAdministrator(actAdmin.getName(), actAdmin);
		
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		new Navigator(this, this);
		
		ViewProvider adminViewProvider = new ViewProvider() {
			
			@Override
			public String getViewName(String viewAndParameters) {
				return "";
			}
			
			@Override
			public View getView(String viewName) {

				if (!ctAdmin.vpIsLogged.getValue())
					return new AdminLoginView();

				else if (ctAdmin.vpIsLogged.getValue()) 
					return new AdminAuthView();

			 // practically unreachable code
				return null;
			}
		};
		
		getNavigator().addProvider(adminViewProvider);
	} 
}