package com.demo.model;

import javax.persistence.Entity;

@Entity
// @DiscriminatorValue("BIKE")
// So instead of the class name in the dtype column now the above value will
// appear
public class TwoWheeler extends Vehicle {

	/*
	 * Now when we put @Entity above the TwoWheeler and FourWheeler class and
	 * than create objects of each including Vehicle class in the hibernatetest
	 * class and save each using session, then as the Two and Four wheeler class
	 * in extending the vehicle class hibernate will create one table VEHICLE
	 * regardless of the entity annotation which includes all 3 class variables
	 * as columns. But there will be one more column "dtype" called
	 * discriminator which will show the data type of the row value.
	 */

	private String SteeringHandle;

	public String getSteeringHandle() {
		return SteeringHandle;
	}

	public void setSteeringHandle(String steeringHandle) {
		SteeringHandle = steeringHandle;
	}

}
