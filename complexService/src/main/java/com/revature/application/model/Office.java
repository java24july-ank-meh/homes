package com.revature.application.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int officeId;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String website;
	
	@OneToMany(mappedBy = "office", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	Set<Complex> complexes;
	
	public Office() {
	}

	public Office(int officeId, String street, String city, String state, String zip, String phone, String website) {
		super();
		this.officeId = officeId;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.website = website;
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

	@Override
	public String toString() {
		return "Office [officeId=" + officeId + ", street=" + street + ", city=" + city + ", state=" + state + ", zip="
				+ zip + ", phone=" + phone + ", website=" + website;
	}
}
