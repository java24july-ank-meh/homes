package com.revature.application.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSOCIATE")
public class Associate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long associateId;
	private String firstName;
	private String lastName;
	private String slackId;
	private String role;
	private Long officeId;
	private String phone;
	private String about;
	@Column(unique = true)
	private String email;
	private String gender;
	private Long unitId;
	private boolean withCar;
	private LocalDateTime moveInDate;
	private boolean housingAgreed;

	public Associate() {
		super();
	}

	public Associate(Long associateId, String firstName, String lastName, String slackId, String role, Long officeId,
			String phone, String about, String email, String gender, Long unitId, boolean withCar,
			LocalDateTime moveInDate, boolean housingAgreed) {
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
		this.withCar = withCar;
		this.moveInDate = moveInDate;
		this.housingAgreed = housingAgreed;
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

	public boolean isWithCar() {
		return withCar;
	}

	public void setWithCar(boolean withCar) {
		this.withCar = withCar;
	}

	public LocalDateTime getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(LocalDateTime moveInDate) {
		this.moveInDate = moveInDate;
	}

	public boolean isHousingAgreed() {
		return housingAgreed;
	}

	public void setHousingAgreed(boolean housingAgreed) {
		this.housingAgreed = housingAgreed;
	}

	@Override
	public String toString() {
		return "Associate [associateId=" + associateId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", slackId=" + slackId + ", role=" + role + ", officeId=" + officeId + ", phone=" + phone + ", about="
				+ about + ", email=" + email + ", gender=" + gender + ", unitId=" + unitId + ", withCar=" + withCar
				+ ", moveInDate=" + moveInDate + ", housingAgreed=" + housingAgreed + "]";
	}
	
	
}