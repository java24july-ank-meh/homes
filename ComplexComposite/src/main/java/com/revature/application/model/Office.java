package com.revature.application.model;

import java.util.*;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Office {
	private int officeId;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String website;
	private String timezone;
	Set<Complex> complexes;
	
	public Office() {
	}

	public Office(int officeId, String street, String city, String state, String zip, String phone, String website, String timezone) {
		super();
		this.officeId = officeId;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.website = website;
		this.timezone = timezone;
	}

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Set<Complex> getComplexes() {
		return complexes;
	}

	public void setComplexes(Set<Complex> complexes) {
		this.complexes = complexes;
	}
	
	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@Override
	public String toString() {
		return "Office [officeId=" + officeId + ", street=" + street + ", city=" + city + ", state=" + state + ", zip="
				+ zip + ", phone=" + phone + ", website=" + website + ", timezone" + timezone;
	}
}
