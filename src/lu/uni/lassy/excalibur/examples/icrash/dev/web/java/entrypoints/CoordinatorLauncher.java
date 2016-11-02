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

import org.apache.log4j.Logger;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.CoordMobileAuthView;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views.CoordMobileLoginView;

@Push
@Theme("icrash")
@Title("iCrash Coordinator")
public class CoordinatorLauncher extends UI {
	private static final long serialVersionUID = -4176039228390755825L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	IcrashSystem sys = IcrashSystem.getInstance();
	
	@Override
	protected void init(VaadinRequest request) {
		
		new Navigator(this, this);
		
		ViewProvider coordinatorsViewProvider = new ViewProvider() {
			private static final long serialVersionUID = -2918314754706507334L;

			@Override
			public String getViewName(String viewAndParameters) {
				return viewAndParameters;
			}
			
			@Override
			public View getView(String viewName) {
				
				CtCoordinator thisCtCoord = (CtCoordinator) sys.getCtCoordinator(new DtCoordinatorID(new PtString(viewName)));
				
				if (!thisCtCoord.vpIsLogged.getValue()) {
					log.debug("coordinatorsViewProvider: actor is logged out, so let's return CoordMobileLoginView");
					return new CoordMobileLoginView(viewName);
				}
				
				else {	
					log.debug("coordinatorsViewProvider: actor is logged in, so let's return CoordMobileAuthView");
					return new CoordMobileAuthView(viewName);
				} 
			}
		};
		
		getNavigator().addProvider(coordinatorsViewProvider);
	}
}