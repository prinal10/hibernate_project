package com.demo.main;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.demo.model.UserDetails;
import com.demo.model.Vehicle;

public class HibernateTest {

	static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	// static HashMap<Integer, UserDetails> users = new HashMap<Integer,
	// UserDetails>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Vehicle v = new Vehicle(); TwoWheeler bike = new TwoWheeler();
		 * FourWheeler car = new FourWheeler();
		 * 
		 * v.setVehicleData("suv"); // bike.setVehicleName("Motor-Bike");
		 * bike.setSteeringHandle("Bike Steering Handle");
		 * 
		 * car.setVehicleName("Motor-Car");
		 * car.setSteeringWheel("Car Steering Wheel");
		 * 
		 * for (int i = 0; i < 10; i++) { UserDetails user = new UserDetails();
		 * user.setData("demo user - " + i, new Date(), "demo desc - " + i);
		 * users.put(i, user); } System.out.println(users.size());
		 */

		// Address address = new
		/*
		 * Address(); Collection<Vehicle> vehicles = new ArrayList<Vehicle>();
		 * // Collection<UserDetails> users = new ArrayList<UserDetails>();
		 * Vehicle v1 = new Vehicle(); Vehicle v2 = new Vehicle();
		 */
		/*
		 * users.add(user); v1.setVehicleData("Car", users);
		 * v2.setVehicleData("Moto-Bike", users);
		 */
		/*
		 * v1.setVehicleData("car"); v2.setVehicleData("Moto-Bike");
		 * vehicles.add(v1); vehicles.add(v2);
		 */
		/*
		 * Session factory: one object per application. Need to save,update,get
		 * something in/from database, first we have to get a session from the
		 * session factory. and so for that we have to create a session factory.
		 * Created using the configuration file that we have already created
		 */

		/*
		 * Session : use to save the model object. in out example the
		 * UserDetails object. You can create as many session as you want for
		 * the application
		 */
		// Session session = sessionFactory.openSession();

		/*
		 * Now to save the object in the database we have to perform a
		 * transaction (unit to perform some action in database) so we will have
		 * to begin a transaction
		 */

		// session.beginTransaction();

		/*
		 * save the object (i.e data) into the database by updating or creating
		 * table
		 */
		// session.save(user);

		/*
		 * now to confirm or perform the transaction into the database we have
		 * to commit the transaction
		 */
		// session.getTransaction().commit();

		// close the session to release the resources
		// session.close();

		/*
		 * user.setHomeData("Prinal", new Date(),
		 * address.setAddress("demo street", "demo city", "demo state",
		 * "demo pincode"), "This is demo description of the user");
		 */
		/*
		 * Collection<Address> addresses = new ArrayList<Address>();
		 * addresses.add((new Address()).setAddress("demo street 1",
		 * "demo city 1", "demo state 1", "demo pincode 1")); addresses.add((new
		 * Address()).setAddress("demo street 2", "demo city 2", "demo state 2",
		 * "demo pincode 2"));
		 * 
		 * user.setData("demo name", new Date(), "demo desc", addresses);
		 * 
		 */
		/*
		 * user.setOfficeData("Marlabs Inc", new Date(),
		 * address.setAddress("demo office street", "demo office city",
		 * "demo office state", "demo office pincode"),
		 * "This is demo description of the office");
		 */

		// UserDetails user = new UserDetails();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// In HQL the queries will deal with the classes and the objects instead
		// of the tables as in sql. Now to write a query in hql we have to get a
		// query object.
		// the below statment creates a query object to which we supply our sql
		// queries. Using this query object we can manipulate and view the data
		// in the table
		Query query = session.createQuery("select userName from UserDetails");

		// Most of the function or queries SQL select has can be written in HQL
		Query query1 = session.createQuery("select max(userId) from UserDetails");

		String minUserId = "5 or 1=1";
		String minid = "5";

		// This is an classic example of SQL injection. The user can manipulate
		// query by using such adding strings to the input parameter and get the
		// data which is not intended for the user. To prevent this we have to
		// avoid parameter binding. Like we just binded the parameter minUserId
		// to the query.
		Query sqlinjection = session.createQuery("from UserDetails where userId > " + minUserId);

		// This can also be avoided by using parameter substitution i.e using
		// placeholder ? in query

		/*
		 * Query query2 =
		 * session.createQuery("from UserDetails where userId > ?");
		 * query2.setInteger(0, Integer.parseInt(minid));
		 */
		// Another way is by using the meaningful variable name using : i.e
		// ":userId"
		Query query2 = session.createQuery("from UserDetails where userId > :userId");
		query2.setInteger("userId", Integer.parseInt(minid));

		// setFirstResult(n) will skip rows starting from the first row to n
		// rows below and pull ups the rest and puts it in a list
		query.setFirstResult(5);

		// setMaxResults(n) will pull up only n rows and puts it in list from
		// the result set
		query.setMaxResults(5);
		// List<UserDetails> users = (List<UserDetails>) query.list();

		// the above query returns username column which is of string type and
		// so string list is return instead of user object list
		List<String> usernames = (List<String>) query.list();

		System.out.println(usernames.size());
		session.getTransaction().commit();
		session.close();
		for (String username : usernames) {
			System.out.println(username);
		}

		// user.setData("demo user", new Date(), "demo desc - ");

		// Before this SAVE statement the user object is a transient object and
		// so the session will not track the changes made to the user object
		// properties. But after the SAVE user object becomes persistent and is
		// handed to the hibernate
		// and after that hibernate keeps tracks of the last update on user
		// object properties before the transaction is committed

		// user.setUserName("new user here");

		// session.save(user);

		// user.setUserName("This is the new Name!!!"); // Even though the user
		// object is saved
		// before this update,
		// hibernate will always
		// updates the table of
		// whatever "LAST"
		// changes that
		// are made to user
		// object "PROPERTIES"
		// before the
		// commit statement is
		// executed as update
		// after the commit
		// statement the object
		// becomes detached
		// object

		/*
		 * for (int i = 0; i < 10; i++) { UserDetails user1 = new UserDetails();
		 * user1.setData("demo user - " + (i + 1), new Date(), "demo desc - " +
		 * (i + 1)); // users.put(i, user1);
		 * 
		 * session.save(user1); }
		 */
		// session.delete(getObejctById(6));
		// session.persist(user);
		/*
		 * session.save(user); session.save(v1); session.save(v2);
		 */

		/*
		 * session.save(v); session.save(bike); session.save(car);
		 */
		// session.getTransaction().commit();

		/// user.setUserName("User after the commit");
		// session.close();

		// saveData(user, vehicle);
		// saveData(vehicle);

		/*
		 * In Lazy initialization the retrievedUser object will have all
		 * variables but it wont have listOfAddresses collection object Instead
		 * when we used the getter method to get the listOfAddresses using
		 * retrievedUser object at that time hibernate will automatically
		 * generate new query and will fetch this listOfAddresses data, but the
		 * session should not be closed
		 */

		/*
		 * To avoid the lazy initialization we use fetch=FetchType.EAGER in
		 * the @ElementCollection annotation of the listOfAddresses collection
		 * object And so now retrieved user will have the listOfAddresses from
		 * the session.get method itself
		 */

		// UserDetails retrievedUser = getDataById(1);

		// System.out.println("The retrieved user name is : " +
		// retrievedUser.getUserName());

	}

	public static void saveData(UserDetails user, Vehicle v) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.save(v);
		session.getTransaction().commit();
		session.close();

	}

	public static UserDetails getDataById(int id) {

		Session session = sessionFactory.openSession();
		// session.beginTransaction();
		UserDetails user = (UserDetails) session.get(UserDetails.class, id);
		session.close();
		return user;

	}

	/*
	 * public static UserDetails getObejctById(int id) {
	 * 
	 * System.out.println(users.size());
	 * 
	 * return users.get(id);
	 * 
	 * }
	 */

}
