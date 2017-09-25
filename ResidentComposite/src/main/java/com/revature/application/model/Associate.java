package com.revature.application.model;

import java.time.LocalDateTime;


public class Associate {
	private Long associateId;
	private String firstName;
	private String lastName;
	private String slackId;
	private String role;
	private Long officeId;
	private String phone;
	private String about;
	private String email;
	private String gender;
	private Long unitId;
	private int hasCar = 0; // fake boolean
	//private LocalDateTime moveInDate;
	//private LocalDateTime moveOutDate;
	//private LocalDateTime housingAgreed;// if not null then its agreed
	//private LocalDateTime hasKeys; // if null currently does not have keys

	public Associate() {
		super();
	}

	public Associate(Long associateId, String firstName, String lastName, String slackId, String role, Long officeId,
			String phone, String about, String email, String gender, Long unitId, int hasCar, LocalDateTime moveInDate,
			LocalDateTime moveOutDate, LocalDateTime housingAgreed, LocalDateTime hasKeys) {
		super();
		this.associateId = associateId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.slackId = slackId;
		this.role = role;
		this.officeId = officeId;
		this.phone = phone;
		this.about = about;
		this.email = email;
		this.gender = gender;
		this.unitId = unitId;
		this.hasCar = hasCar;
		/*this.moveInDate = moveInDate;
		this.moveOutDate = moveOutDate;
		this.housingAgreed = housingAgreed;
		this.hasKeys = hasKeys;*/
	}

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSlackId() {
		return slackId;
	}

	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public int getHasCar() {
		return hasCar;
	}

	public void setHasCar(int hasCar) {
		this.hasCar = hasCar;
	}

	/*public LocalDateTime getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(LocalDateTime moveInDate) {
		this.moveInDate = moveInDate;
	}

	public LocalDateTime getMoveOutDate() {
		return moveOutDate;
	}

	public void setMoveOutDate(LocalDateTime moveOutDate) {
		this.moveOutDate = moveOutDate;
	}

	public LocalDateTime getHousingAgreed() {
		return housingAgreed;
	}

	public void setHousingAgreed(LocalDateTime housingAgreed) {
		this.housingAgreed = housingAgreed;
	}

	public LocalDateTime getHasKeys() {
		return hasKeys;
	}

	public void setHasKeys(LocalDateTime hasKeys) {
		this.hasKeys = hasKeys;
	}*/

	@Override
	public String toString() {
		return "Associate [associateId=" + associateId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", slackId=" + slackId + ", role=" + role + ", officeId=" + officeId + ", phone=" + phone + ", about="
				+ about + ", email=" + email + ", gender=" + gender + ", unitId=" + unitId + ", hasCar=" + hasCar;
				/*+ ", moveInDate=" + moveInDate + ", moveOutDate=" + moveOutDate + ", housingAgreed=" + housingAgreed
				+ ", hasKeys=" + hasKeys + "]";*/
	}

	

}