package com.demo.main;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

import com.demo.model.UserDetails;

public class NamedQueries {
	static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		/*
		 * Query hqlnamedquery = session.getNamedQuery("UserDetails.byId");
		 * hqlnamedquery.setInteger(0, 5);
		 * 
		 * Query sqlnamedquery = session.getNamedQuery("UserDetails.byName");
		 * sqlnamedquery.setString(0, "demo user - 10");
		 * 
		 * List<UserDetails> usernames1 = (List<UserDetails>)
		 * hqlnamedquery.list(); List<UserDetails> usernames2 =
		 * (List<UserDetails>) sqlnamedquery.list();
		 * 
		 * for (UserDetails user : usernames1) {
		 * System.out.println(user.getUserName()); }
		 */

		/*
		 * Below we have created a criteria object which is like a WHERE clause,
		 * but here instead of where clause we have RESTRICTION. we have mention
		 * the table/class for which we are imposing restriction.After that we
		 * set criteria by adding restriction EQUALS(USERNAME, 'DEMO USER -
		 * 10')to a get a particular set of records i.e we mention here the
		 * where clause (WHERE USERNAME = 'DEMO USER - 10'), if we skip this
		 * step then all records will be pulled up. We can also chain the
		 * restrictions (it adds AND clause)
		 */
		// Criteria criteria = session.createCriteria(UserDetails.class);

		// criteria.add(Restrictions.eq("userName", "demo user -
		// 10")).add(Restrictions.gt("userId", 5));

		/*
		 * In order to add a OR clause instead of AND clause we use
		 * (Restrictions.or)
		 */
		// criteria.add(Restrictions.or(Restrictions.eq("userName", "demo user -
		// 10"), Restrictions.gt("userId", 5)));

		/*
		 * Projections at a very high level are used to implement any
		 * aggregation function or grouping functions. Now suppose we only have
		 * to get the result set that contains "userId" column and so it would
		 * be efficient to get the projection of that column and then perform
		 * queries on that projection. We add the projection using the
		 * .setProjection method when we declare the criteria object and pass
		 * the property(column)/properties(columns) but you cannot pass the
		 * table/class name there. WE HAVE TO CHECK THE RETURN TYPE OF THE
		 * RESULT SET AND GET THE LIST appropriately
		 */
		// Criteria criteria1 =
		// session.createCriteria(UserDetails.class).setProjection(Projections.property("userId"));

		/*
		 * We can also performing aggregate function as below
		 */
		// Criteria criteria2 =
		// session.createCriteria(UserDetails.class).setProjection(Projections.max("userId"));

		/*
		 * We can also order the result set by using below addOrder method
		 */
		// Criteria criteria3 =
		// session.createCriteria(UserDetails.class).addOrder(Order.desc("userId"));
		/*
		 * Querying by example is used when we have too many properties and too
		 * many criteria value to satisfied for which we have to use .add()
		 * every time in the criteria object, instead we can pass a example
		 * object to tell hibernate to generate a result set that matches/ have
		 * only this much information. We will create a (UserDetails)object
		 * table/class with desire properties and restrictions and pass it to
		 * the Example.create method to obtain a Example object. Them we will
		 * pass this example object to criteria .add(criteria) method as below
		 */

		UserDetails exampleUser = new UserDetails();
		// exampleUser.setUserId(5);
		exampleUser.setUserName("demo user - 5");
		/*
		 * hibernate ignores if any property is set tpo null then hibrnate wont
		 * consider it as a example and if the null property happens to be a
		 * primary key than also hibrnate wont consider it as a example. Suppose
		 * you wont to exclude a property in the example object the we can use
		 * .excludeProperty(). We can also use .enableLike() to get result that
		 * contains the example value and may not be exactly same
		 */

		Example example = Example.create(exampleUser).excludeProperty("userName");

		Criteria criteria = session.createCriteria(UserDetails.class).add(example);

		List<UserDetails> users = (List<UserDetails>) criteria.list();

		for (UserDetails user : users) {
			System.out.println(user.getUserName());
		}

		session.getTransaction().commit();
		session.close();

	}

}
