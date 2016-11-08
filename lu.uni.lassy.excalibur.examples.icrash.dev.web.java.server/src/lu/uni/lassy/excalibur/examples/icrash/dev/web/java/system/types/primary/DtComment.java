/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 *     Anton Nikonienkov - iCrash HTML5 API and implementation     
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * The Class DtComment, which holds the data type of the comment.
 */
public class DtComment extends DtString implements JIntIs {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;
	
	/**
	 * Instantiates a new datatype of the comment.
	 *
	 * @param s The primitive type of string to put into the datatype
	 */
	public DtComment(PtString s) {
		super(s);
	}
	
	/** The maximum length of a comment. */
	private int _maxLength = 160;
	
	public PtBoolean is(){
		return new PtBoolean((this.value.getValue().length() <= _maxLength));
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected strucutre of the comment is to have a maximum length of " + _maxLength);
	}
}
