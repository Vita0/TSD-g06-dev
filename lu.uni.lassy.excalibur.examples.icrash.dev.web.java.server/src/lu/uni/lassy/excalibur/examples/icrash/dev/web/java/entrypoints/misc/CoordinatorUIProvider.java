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

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.entrypoints.CoordinatorLauncher;

public class CoordinatorUIProvider extends UIProvider {
	private static final long serialVersionUID = 8639994505241569177L;

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		
		String browserUsed = event.getRequest().getHeader("user-agent").toLowerCase();
	
	//	boolean webkit = browserUsed.toLowerCase().contains("webkit");
		boolean mobile = browserUsed.toLowerCase().contains("mobile");
	//	boolean opera = browserUsed.toLowerCase().contains("opera");
	//	boolean mini = browserUsed.toLowerCase().contains("mini");
	//	boolean mobi = browserUsed.toLowerCase().contains("mobi");
	//	boolean windowsphone = browserUsed.toLowerCase().contains("windows phone 8")
	//			|| browserUsed.toLowerCase().contains("windows phone 9");
		
		return CoordinatorLauncher.class;
		
	/*	if ((webkit && mobile)
			||
			(opera && (mini || mobi))
			||
			windowsphone)

		{
			return CoordinatorLauncher.class;

		} else { 
	    	return CoordinatorLauncher.class;
	    } */
	}
}