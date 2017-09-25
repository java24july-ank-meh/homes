package com.revature.application.model;

public class ResidentUnitComplexOffice {
	
	private Associate associate;
	private Unit unit;
	
	public ResidentUnitComplexOffice() {
		super();
	}
	public ResidentUnitComplexOffice(Associate associate, Unit unit) {
		super();
		this.associate = associate;
		this.unit = unit;
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
	
	

}
