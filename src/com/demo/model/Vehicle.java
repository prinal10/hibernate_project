package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*
 * InheritanceType.SINGLE_TABLE, as the Two and Four wheeler class
 * in extending the vehicle class hibernate will create one table VEHICLE
 * regardless of the entity annotation which includes all 3 class variables
 * as columns. But there will be one more column "dtype" called
 * discriminator which will show the data type of the row value. Using name para we can change the dtype column name to VEHICLE_TYPE
 */
@Entity
/*
 * @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 * 
 * @DiscriminatorColumn(name = "VEHICLE_TYPE", discriminatorType =
 * DiscriminatorType.STRING)
 */

/*
 * InheritanceType.TABLE_PER_CLASS this para will create separate table for each
 * class i.e parent and child classes instead of one table with dtype column.
 * Child table will also have the inherited column from parent
 */
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

/*
 * Now in the table per class strategy, all children will have the redundant
 * columns of the parent. A efficient solution would be not to have such
 * redundant table. This can be achieved by using InheritanceType.JOINED
 * strategy, this will join the children table with parent table where the
 * parent table will have new value of parent properties implemented by child
 * and child will only have the foreign key id of parent and its own properties
 * column. So data retrieval becomes easy using join queries.
 */
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "VEHICLE_ID")
	private int vehicleId;

	@Column(name = "VEHICLE_NAME")
	private String vehicleName;

	/*
	 * suppose a car does'nt have a user. Now when you tell hibernate to
	 * retrieve the user for such vehicle object the hibernate will throw an
	 * exception. So to suppress such exceptions we will use @NotFound and in
	 * that we have to specify action which in this is ignore annotation .
	 */
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "USER_ID")
	 * 
	 * @NotFound(action = NotFoundAction.IGNORE) private UserDetails user;
	 */
	/*
	 * @ManyToMany(mappedBy = "vehicle") private Collection<UserDetails> users =
	 * new ArrayList<UserDetails>();
	 */
	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	/*
	 * public UserDetails getUser() { return user; }
	 * 
	 * public void setUser(UserDetails user) { this.user = user; }
	 */

	/*
	 * public void setVehicleData(String name) { this.setVehicleName(name);
	 * 
	 * }
	 */

	/*
	 * public Collection<UserDetails> getUsers() { return users; }
	 * 
	 * public void setUsers(Collection<UserDetails> users) { this.users = users;
	 * }
	 */
	/*
	 * public void setVehicleData(String name, UserDetails u) {
	 * this.setVehicleName(name); this.setUser(u);
	 * 
	 * }
	 */
	/*
	 * public void setVehicleData(String name, Collection<UserDetails> u) {
	 * this.setVehicleName(name); this.setUsers(u);
	 * 
	 * }
	 */

	public void setVehicleData(String name) {
		this.setVehicleName(name);
	}
}
