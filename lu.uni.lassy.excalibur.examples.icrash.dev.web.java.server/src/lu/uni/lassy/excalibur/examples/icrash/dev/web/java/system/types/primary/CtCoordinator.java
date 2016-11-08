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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;

public class CtCoordinator extends CtAuthenticated {

	private static final long serialVersionUID = -9187114038511423143L;

	public DtCoordinatorID id;
	
	public PtBoolean init(DtCoordinatorID aId,DtLogin aLogin,DtPassword aPwd){
		super.init(aLogin, aPwd);
		id = aId;
		return new PtBoolean(true); 
	}
	
	public PtBoolean update(DtLogin aLogin,DtPassword aPwd){
		login = aLogin;
		pwd = aPwd;
		return new PtBoolean(true);
	}
}
