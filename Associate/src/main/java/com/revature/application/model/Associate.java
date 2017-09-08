package com.revature.application.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ASSOCIATE")
public class Associate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int associateId;
	private String firstName;
	private String lastName;
	private String slackId;
	private String role;
	private int officeId;
	private String phone;
	private String about;
	private String email;
	private String gender;
	private int unitId;
	
	

	public Associate() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Associate(int associateId, String firstName, String lastName, String slackId, String role, int officeId,
			String phone, String about, String email, String gender, int unitId) {
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
	}

	

	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}



	public int getAssociateId() {
		return associateId;
	}

	public void setAssociateId(int associateId) {
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

	public int getOfficeId() {
		return officeId;
	}

	public void setOfficeId(int officeId) {
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

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}



	@Override
	public String toString() {
		return "Associate [associateId=" + associateId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", slackId=" + slackId + ", role=" + role + ", officeId=" + officeId + ", phone=" + phone + ", about="
				+ about + ", email=" + email + ", gender=" + gender + ", unitId=" + unitId + "]";
	}

	

}