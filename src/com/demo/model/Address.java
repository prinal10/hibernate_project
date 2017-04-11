package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
 * Inorder to tell hibernate not to create separate table for this class and to use/embedded (include) this object with field data in other table column, 
 * we will mark this class with @Embeddable 
 * and the object of this class that we will store in the address column in the USER_INFO table, 
 * that object will be marked with @Embedded but it will be marked by default
*/

@Embeddable
public class Address {

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIPCODE")
	private String pincode;

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

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Address setAddress(String street, String city, String state, String pincode) {

		this.setCity(city);
		this.setStreet(street);
		this.setState(state);
		this.setPincode(pincode);
		return this;
	}

}
