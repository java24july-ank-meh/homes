package com.revature.application.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import com.revature.apartment.model.Unit;


public class Complex {

	
	private int complexId;
	private String website;
	private String email;
	private String phone;
	private String name;

	
	private String abbreviation; // abbreviation of the name unique and under 12 char
	private String address;
	private String parking;

	
	private Office office;

	
	List<Unit> units;


	public Complex(int complexId, String website, String email, String phone, String name, String abbreviation,
			String address, String parking, Office office, List<Unit> units) {
		super();
		this.complexId = complexId;
		this.website = website;
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.abbreviation = abbreviation;
		this.address = address;
		this.parking = parking;
		this.office = office;
		this.units = units;
	}

	public Complex(String website, String email, String phone, String name, String abbreviation, String address,
			String parking, Office office, List<Unit> units) {
		super();
		this.website = website;
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.abbreviation = abbreviation;
		this.address = address;
		this.parking = parking;
		this.office = office;
		this.units = units;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	
	public Complex() {
	}

	
	public int getComplexId() {
		return complexId;
	}

	public void setComplexId(int complexId) {
		this.complexId = complexId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

}
