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
@Table(name="SUPPLY")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "suppliesId")
public class Supply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int supplyId;
	
	private String name;
	private Date submitDate;
	private boolean resolved;
	private Date resolveDate;
	private int unitId;
	private int submittedBy;
	
	
	public Supply() {}


	public Supply(int suppliesId, String name, Date submitDate, boolean resolved, Date resolveDate, int unitId, int submittedBy) {
		super();
		this.supplyId = suppliesId;
		this.name = name;
		this.submitDate = submitDate;
		this.resolved = resolved;
		this.resolveDate = resolveDate;
		this.unitId = unitId;
		this.submittedBy = submittedBy;
	}


	public int getSuppliesId() {
		return supplyId;
	}


	public void setSuppliesId(int suppliesId) {
		this.supplyId = suppliesId;
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


	public int getUnitId() {
		return unitId;
	}


	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}
	
	


	public int getSubmittedBy() {
		return submittedBy;
	}


	public void setSubmittedBy(int submittedBy) {
		this.submittedBy = submittedBy;
	}


	@Override
	public String toString() {
		return "Supplies [suppliesId=" + supplyId + ", name=" + name + ", submitDate=" + submitDate + ", resolved="
				+ resolved + ", resolveDate=" + resolveDate + ", unitId=" + unitId + ", submittedBy= " + submittedBy +"]";
	};
	
	
}	
