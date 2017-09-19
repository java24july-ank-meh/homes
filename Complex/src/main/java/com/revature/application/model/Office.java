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
	private String address;
	private String phone;
	private String website;
	private String timezone;

	@OneToMany(mappedBy = "office", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	Set<Complex> complexes;
	
	public Office() {
	}

	public Office(int officeId, String address, String phone, String website, String timezone) {
		super();
		this.officeId = officeId;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		return "Office [officeId=" + officeId + ", address=" + address + ", phone=" + phone + ", website=" + website + ", timezone" + timezone;
	}
}
