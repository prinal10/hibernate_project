package com.demo.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity

/*
 * The below annotation will tell hibernate that this is cache able entity/class
 * and is to consider for caching
 */
@Cacheable
/*
 * The below annotation is used to specify the (type of caching) caching
 * strategy on this entity/class. It is called CacheConcurrencyStrategy
 */
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

/*
 * Now we can use NamedQuery so we don't have to type common HQL queries each
 * time. we can create many named queries with relevant name such as to get
 * UserDetails object for a specific id then we can name is UserDetails.byId.
 * Query can have a place holder or a parameter like ":userId"
 */
// @NamedQuery(name = "UserDetails.byId", query = "from UserDetails where userId
// = ?")

/*
 * We can also write named queries in pure SQL by using @namednativequery
 * annotation. But we have to use table name and column name only. we cannot use
 * class or variable name in the queries. As it is a sql query and not a HQL
 * hibernate will not the result set type and so we can specify the result set
 * return type using resultClass para
 */
// @NamedNativeQuery(name = "UserDetails.byName", query = "select * from
// USER_INFO where USER_NAME = ?", resultClass = UserDetails.class)

// @org.hibernate.annotations.Entity(selectBeforeUpdate = true)
// the above annotation will tell hibernate to perform a select query before
// session.update so that
// any updates can be checked. And if there is no change in the update user
// object and the user object
// saved in the db table then don't waste resource on updating the object and so
// skip the update. It is application for other type of transaction such as
// delte, insert ,etc Use this if there is chance that the object might not be
// changed
@Table(name = "USER_INFO")
public class UserDetails {

	// id states that it will be the primary key
	// if we want to use embedded object as an primary key i.e id than we can
	// use @EmbeddedId annotation
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	/*
	 * We want a 1to1 relation between UserDetails and Vehicle table and so we
	 * will use @OneToOne. It is used to map one column from UserDetails table
	 * to point(map,refer) one column in Vehicle table
	 */
	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name = "VEHICLE_ID") private Vehicle vehicle;
	 */

	/*
	 * This relation is between one user object that has many vehicles. So we
	 * have to represent the vehicle object as a set that contains all the
	 * vehicles for a user. So hibernate will create a new table of vehicles
	 * that will map user_id to vehicle_id for all vehicles that are present in
	 * the collection array for a user
	 * 
	 * @JoinTabel annotation is used to rename the mapping columns of the new
	 * table. name para is used to rename the new table. joincolumns para is
	 * used to rename the column of this table ie. user and inversejoincolumn
	 * para is used for vehicle table
	 * 
	 * Now suppose you don't want a separate table instead we want the user_id
	 * column in the vehicle table itself to identify the vehicle user. So we
	 * will use mappedBy para in OneToMany annotation and will pass the user
	 * variable that we have created in the vehicle table. Similarly in the
	 * ManyToOne annotation in the vehicle class we will specify what is the
	 * join column in USER_INFO table for the user column in the vehicle table,
	 * by using JoinColumn and passing value "USER_ID" in the name parameter.
	 * And so this will join or map the user column of vehicle table to the
	 * USER_ID column of the USER_INFO table
	 */
	// @OneToMany(mappedBy = "user")
	/*
	 * @JoinTable(name = "USER_VEHICLES", joinColumns = @JoinColumn(name =
	 * "USER_ID"), inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID"))
	 */
	// @ManyToMany

	/*
	 * Now we have always saved the vehicles objects in the HibernateTest class
	 * file using session.save. But since the vehicle collection is already
	 * added to the vehicle ArrayList in the userdetails class the saving of the
	 * vehicle should happen automatically and so thats why we use cascade para
	 * in the OnToMany annotation. In persist any changes made to the variables
	 * in the userdetails class will be tracked down and saved when
	 * session.persist(user) is performed on userdetails object
	 */
	/*
	 * @OneToMany(cascade = CascadeType.PERSIST) private Collection<Vehicle>
	 * vehicle = new ArrayList<Vehicle>();
	 */

	// @Basic annotation treats the below field i.e below column as it should be
	// as per the hibernate default rules and so the data type and length will
	// be decided by the hibernate itself. By defualt all the column are basic
	// so no need to mention. we will only need basic when we need to use the
	// parameter that we can pass in the basic such as fetch="" and optional=""

	// @Transient to mark the field as transient
	// to tell hibernate not to persist this field

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "JOINED_DATE")
	@Temporal(TemporalType.DATE)
	private Date joinedDate;

	// @Lob means that Large Object. It tells hibernate not to limit the size
	// with default 255 Similarly hibernate also has @CLob character large
	// object or @BLob Bytestream Large Object
	@Column(name = "DESCRIPTION")
	@Lob
	private String description;

	// @Column(name = "ADDRESS")
	// private String Address;

	/*
	 * @Embedded private Address homeAddress;
	 * 
	 * 
	 * suppose you want to use the address object to create a new field office
	 * address in the table but you also want to change the column/fields names
	 * in the address class/object i.e from street name to building number, etc.
	 * 
	 * @Embedded
	 * 
	 * @AttributeOverrides({ @AttributeOverride(name = "street", column
	 * = @Column(name = "OFFICE_STREET")),
	 * 
	 * @AttributeOverride(name = "city", column = @Column(name =
	 * "OFFICE_CITY")),
	 * 
	 * @AttributeOverride(name = "state", column = @Column(name =
	 * "OFFICE_STATE")),
	 * 
	 * @AttributeOverride(name = "pincode", column = @Column(name =
	 * "OFFICE_ZIPCODE")) }) private Address officeAddress;
	 */

	/*
	 * @ElementCollection is the annotation that is used to tell the hibernate
	 * to treat this as a list and also that we want to store this list in a
	 * table. Now here address class is already embedded and so it means that
	 * not only the object of address class are to be stored as column fields
	 * but in separate table
	 */

	/*
	 * @JoinTable is used to configure the the listOfAddresses table. It
	 * includes naming, change the foreign key name in this table from
	 * "UserDetails_USER_ID" to USER_ID
	 */

	/*
	 * @CollectionId is a hibernate specific annotation that is used to define a
	 * primary key for the given collection holding table here it is
	 * listOfAddresses table.The listOfAddresses has to be of data collection
	 * that holds index and so thats why we have used ArrayListThere are
	 * following parameter in this annotation generator states how to the
	 * primary key to be generated for that we use @GenericGenerator annotation
	 * which have the strategy="hilo" in hibernate. Type which states what is
	 * the type of the primary key long,int,etc
	 * 
	 * @Column is the column that you want to define as a primary key
	 */
	/*
	 * @ElementCollection(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "USER_ADDRESSES", joinColumns = @JoinColumn(name =
	 * "USER_ID"))
	 * 
	 * @GenericGenerator(name = "hilo-gen", strategy = "hilo")
	 * 
	 * @CollectionId(columns = { @Column(name = "ADDRESS_ID") }, generator =
	 * "hilo-gen", type = @Type(type = "long"))
	 * 
	 * private Collection<Address> listOfAddresses = new ArrayList<Address>();
	 * 
	 * 
	 * public Collection<Address> getListOfAddresses() { return listOfAddresses;
	 * }
	 * 
	 * public void setListOfAddresses(Collection<Address> listOfAddresses) {
	 * this.listOfAddresses = listOfAddresses; }
	 */

	/*
	 * public Address getHomeAddress() { return homeAddress; }
	 * 
	 * public void setHomeAddress(Address homeAddress) { this.homeAddress =
	 * homeAddress; }
	 * 
	 * public Address getOfficeAddress() { return officeAddress; }
	 * 
	 * public void setOfficeAddress(Address officeAddress) { this.officeAddress
	 * = officeAddress; }
	 */
	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*
	 * public Collection<Vehicle> getVehicle() { return vehicle; }
	 * 
	 * public void setVehicle(Collection<Vehicle> vehicle) { this.vehicle =
	 * vehicle; }
	 */
	/*
	 * public Vehicle getVehicle() { return vehicle; }
	 * 
	 * public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
	 */

	/*
	 * public void setData(String name, Date date, String desc, Vehicle v) {
	 * this.setUserName(name); this.setJoinedDate(date);
	 * this.setDescription(desc); this.setVehicle(v);
	 * 
	 * }
	 */
	/*
	 * public void setHomeData(String name, Date date, Address homeaddress,
	 * String desc) {
	 * 
	 * this.setUserName(name); this.setJoinedDate(date);
	 * this.setHomeAddress(homeaddress); this.setDescription(desc); }
	 * 
	 * public void setOfficeData(String name, Date date, Address officeaddress,
	 * String desc) {
	 * 
	 * this.setUserName(name); this.setJoinedDate(date);
	 * this.setOfficeAddress(officeaddress); this.setDescription(desc); }
	 * 
	 * public void setAllData(String name, Date date, Address officeaddress,
	 * String desc, Address homeaddress) {
	 * 
	 * this.setUserName(name); this.setJoinedDate(date);
	 * this.setOfficeAddress(officeaddress); this.setDescription(desc);
	 * this.setHomeAddress(homeaddress); }
	 */

	/*
	 * public void setData(String name, Date date, String desc,
	 * Collection<Address> addresses) {
	 * 
	 * this.setUserName(name); this.setJoinedDate(date);
	 * this.setDescription(desc); this.listOfAddresses = addresses;
	 * 
	 * }
	 */

	/*
	 * public void setData(String name, Date date, String desc,
	 * Collection<Vehicle> v) { this.setUserName(name);
	 * this.setJoinedDate(date); this.setDescription(desc); this.setVehicle(v);
	 * }
	 */

	public void setData(String name, Date date, String desc) {

		this.setUserName(name);
		this.setJoinedDate(date);
		this.setDescription(desc);

	}

}
