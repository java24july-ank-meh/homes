package com.revature.application.model;

import java.util.List;

public class Unit {

	private long unitId;
	private String unitNumber;
	private String buildingNumber;
	private int capacity;
	private String gender;
	private Complex complex;
	private List<Associate> associates;

	public Unit() {
	}

	public Unit(long unitId, String unitNumber, String buildingNumber, int capacity, String gender, Complex complex) {
		super();
		this.unitId = unitId;
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
	
	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public List<Associate> getAssociates() {
		return associates;
	}

	public void setAssociates(List<Associate> associates) {
		this.associates = associates;
	}

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", unitNumber=" + unitNumber + ", capacity=" + capacity + ", gender=" + gender
				+ "]";
	}
}