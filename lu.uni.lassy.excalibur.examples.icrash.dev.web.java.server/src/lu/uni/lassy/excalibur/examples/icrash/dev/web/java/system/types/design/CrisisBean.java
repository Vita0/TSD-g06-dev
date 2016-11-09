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
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design;

import java.util.ArrayList;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPhoneNumber;

public class CrisisBean {
	public CrisisBean(Integer iD, String date, String time, String type, double longitude, double latitude,
			ArrayList<String> familyNumbers, String comment, String status) {
		ID = iD;
		this.date = date;
		this.time = time;
		this.type = type;
		this.longitude = longitude;
		this.latitude = latitude;
		this.familyNumbers = familyNumbers;
		this.comment = comment;
		this.status = status;
	}
	private Integer ID;
	private String date;
	private String time;
	private String type;
	private double longitude;
	private double latitude;
	private ArrayList<String> familyNumbers;
	private String comment;
	private String status;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public ArrayList<String> getFamilyNumbers() {
		return familyNumbers;
	}
	public void setFamilyNumbers(ArrayList<String> familyNumbers) {
		this.familyNumbers = familyNumbers;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
