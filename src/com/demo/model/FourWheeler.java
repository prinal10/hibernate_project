package com.demo.model;

import javax.persistence.Entity;

@Entity
// @DiscriminatorValue("CAR")
public class FourWheeler extends Vehicle {
	private String SteeringWheel;

	public String getSteeringWheel() {
		return SteeringWheel;
	}

	public void setSteeringWheel(String steeringWheel) {
		SteeringWheel = steeringWheel;
	}

}
