/*******************************************************************************
 * Copyright (c) 2014 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib;


public class DtYear extends DtInteger{

	private static final long serialVersionUID = 227L;

	public DtYear(PtInteger v) {
		super(v);
	}
	@Override
	public String toString(){
		return Integer.toString(value.getValue());
	}
}
