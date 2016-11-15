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

import java.io.Serializable;
import java.util.Hashtable;

import elemental.json.JsonArray;
import elemental.json.impl.JreJsonFactory;
import elemental.json.impl.JsonUtil;

import java.util.ArrayList;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * The Class DtPhoneNumbers, which holds a datatype of the phone numbers.
 */
public class DtFamilyPhoneNumbers implements Serializable, JIntIs {

		private static final long serialVersionUID = 227L;
		/**  A hashtable of the name of com companies with family phone numbers associated. */
		public Hashtable<String, DtPhoneNumber> assComCompanyViewNamesDtPhoneNumber = new Hashtable<String, DtPhoneNumber>();
		public ArrayList<DtPhoneNumber> listDtPhoneNumbers = new ArrayList<DtPhoneNumber>();

		/**
		 * Instantiates a new datatype phone numbers.
		 *
		 * @param s The primitive type of string to become the datatype of the phone number
		 */
		public DtFamilyPhoneNumbers() {
		}

		/**
		 * Put new family phone numbers.
		 *
		 * @param ComCompanyViewName  The primitive type of string - the Communication Company View Name
		 * @param familyDtPhoneNumber The type DtPhoneNumber
		 */
		public void put(String ComCompanyViewName, DtPhoneNumber familyDtPhoneNumber) {
			assComCompanyViewNamesDtPhoneNumber.put(ComCompanyViewName != null ? ComCompanyViewName : "", familyDtPhoneNumber);
			listDtPhoneNumbers.add(familyDtPhoneNumber);
		}

		/**
		 * Get Json representation of family phone numbers.
		 *
		 * @return familyDtPhoneNumber The type DtPhoneNumber
		 */
		public String getJson() {
			JreJsonFactory factory = new JreJsonFactory();
			JsonArray familyNumbers = factory.createArray();
			int index = 0;
			for (DtPhoneNumber phone: listDtPhoneNumbers) {
				familyNumbers.set(index, phone.value.getValue());
				index++;
			}
			String jsonFamilyNumbers = familyNumbers.toJson();
			return jsonFamilyNumbers;
		}

		/**
		 * Parse Json to list with family phone numbers.
		 *
		 * @param jsonFamilyNumbers  The primitive type of string - the json string with family phone numbers.
		 */
		public void parseJson(String jsonFamilyNumbers) {
			JsonArray phoneNumbers = JsonUtil.parse(jsonFamilyNumbers);
			for (int i = 0; i < phoneNumbers.length(); i++){ 
				listDtPhoneNumbers.add(new DtPhoneNumber(new PtString(
						phoneNumbers.get(i).toString())));
			} 
		}

		public PtBoolean is(){
			return new PtBoolean(listDtPhoneNumbers.size() > 0);
		}

		public PtString getExpectedDataStructure(){
			return new PtString("Expected structure of the family phone numbers is to have a minimum list size > 0"); 
		}
}
