package com.revature.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Add entry of apartment unit by date 
@Entity
@Table(name = "UNIT")
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long unitId;
	@Column
	private String unitNumber;
	private int capacity;
	private String gender;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "complexId") //private Complex complex;
	 */
	public Unit() {
	}

	public Unit(String unitNumber, int capacity, String gender) {
		super();
		this.unitNumber = unitNumber;
		this.capacity = capacity;
		this.gender = gender;
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

	/*
	 * public Complex getComplex() { return complex; }
	 * 
	 * public void setComplex(Complex complex) { this.complex = complex; }
	 */

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", unitNumber=" + unitNumber + ", capacity=" + capacity + ", gender=" + gender
				+ "]";
	}
}