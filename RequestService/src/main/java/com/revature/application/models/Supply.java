package com.revature.application.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="SUPPLIES")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "suppliesId")
public class Supply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int suppliesId;
	private String name;
	private Date submitDate;
	private boolean resolved;
	private Date resolveDate;
	private int apartmentId;
	
	
	public Supply() {}


	public Supply(int suppliesId, String name, Date submitDate, boolean resolved, Date resolveDate, int apartmentId) {
		super();
		this.suppliesId = suppliesId;
		this.name = name;
		this.submitDate = submitDate;
		this.resolved = resolved;
		this.resolveDate = resolveDate;
		this.apartmentId = apartmentId;
	}


	public int getSuppliesId() {
		return suppliesId;
	}


	public void setSuppliesId(int suppliesId) {
		this.suppliesId = suppliesId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getSubmitDate() {
		return submitDate;
	}


	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}


	public boolean isResolved() {
		return resolved;
	}


	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}


	public Date getResolveDate() {
		return resolveDate;
	}


	public void setResolveDate(Date resolveDate) {
		this.resolveDate = resolveDate;
	}


	public int getApartmentId() {
		return apartmentId;
	}


	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}


	@Override
	public String toString() {
		return "Supplies [suppliesId=" + suppliesId + ", name=" + name + ", submitDate=" + submitDate + ", resolved="
				+ resolved + ", resolveDate=" + resolveDate + ", apartmentId=" + apartmentId + "]";
	};
	
	
}	
