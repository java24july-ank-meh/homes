package com.revature.application.model;

public class ResidentUnitComplexOffice {
	
	private Associate associate;
	private Unit unit;
	private Complex complex;
	private Office office;
	
	public ResidentUnitComplexOffice() {
		super();
	}
	public ResidentUnitComplexOffice(Associate associate, Unit unit, Complex complex, Office office) {
		super();
		this.associate = associate;
		this.unit = unit;
		this.complex = complex;
		this.office = office;
	}
	
	public Associate getAssociate() {
		return associate;
	}
	public void setAssociate(Associate associate) {
		this.associate = associate;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Complex getComplex() {
		return complex;
	}
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	
	

}
