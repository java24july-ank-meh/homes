package com.revature.application.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int officeId;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String website;
	
	//@OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
	//Set<Complex> complexes;
	
	public Office() {
	}
}
