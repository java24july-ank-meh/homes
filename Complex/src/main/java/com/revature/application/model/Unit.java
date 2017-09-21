package com.revature.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UNIT")
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long unitId;
	private String unitNumber;
	private String buildingNumber;
	private int capacity;
	private String gender;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "complexId")
	private Complex complex;

	public Unit() {
	}

	public Unit(String unitNumber, String buildingNumber, int capacity, String gender, Complex complex) {
		super();
		this.unitNumber = unitNumber;
		this.buildingNumber = buildingNumber;
		this.capacity = capacity;
		this.gender = gender;
		this.complex = complex;
	}

	public long getUnitId() {
		return unitId;
	}

	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", unitNumber=" + unitNumber + ", capacity=" + capacity + ", gender=" + gender
				+ "]";
	}
}