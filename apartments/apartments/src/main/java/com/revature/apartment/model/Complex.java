package com.revature.apartment.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import com.revature.apartment.model.Unit;


@Entity
	@Table(name="COMPLEX")
	public class Complex {
	
	//	 id
	//	 name
	//	 street
	//	 city
	//	 state
	//	 zip code
	//	 phone
	//	 parking info
	//	 office id (many to one)
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private int complexId;
		private String website;
		private String email;
		private String phone;
		private String name;
		private String street;
		private String city;
		private String state;
		private String zip;
		private String parking;
		
//		@ManyToOne(fetch = FetchType.EAGER)
//		@JoinColumn(name = "OFFICE_ID")
//		@JsonIdentityInfo(scope=Complex.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "officeId")
//		@JsonIdentityReference(alwaysAsId = true)
//		private Office office;
//
//		@OneToMany(mappedBy="complex", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//		private List<Unit> units;
		
		public Complex(int complexId, String website, String email, String phone, String name, String street,
				String city, String state, String zip, String parking/*, Office office, List<Unit> units*/) {
			super();
			this.complexId = complexId;
			this.website = website;
			this.email = email;
			this.phone = phone;
			this.name = name;
			this.street = street;
			this.city = city;
			this.state = state;
			this.zip = zip;
			this.parking = parking;
			/*this.office = office;
			this.units = units;*/
		}
		
		public Complex() {}

		@Override
		public String toString() {
			return "Complex [complexId=" + complexId + ", website=" + website + ", email=" + email + ", phone=" + phone
					+ ", name=" + name + ", street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip
					+ ", parking=" + parking + "]";
		}

		public int getComplexId() {
			return complexId;
		}

		public void setComplexId(int complexId) {
			this.complexId = complexId;
		}

		public String getWebsite() {
			return website;
		}

		public void setWebsite(String website) {
			this.website = website;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getParking() {
			return parking;
		}

		public void setParking(String parking) {
			this.parking = parking;
		}

//		public Office getOffice() {
//			return office;
//		}
//
//		public void setOffice(Office office) {
//			this.office = office;
//		}
//
//		public List<Unit> getUnits() {
//			return units;
//		}
//
//		public void setUnits(List<Unit> units) {
//			this.units = units;
//		}

		

}
