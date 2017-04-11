package com.demo.main;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UseCache {
	static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		/*
		 * Even though we have called the hibernate to fetch data from database
		 * twice. Hibernate will not contact the database twice instead it check
		 * whether the same data is present in the cache and if it is present
		 * then assign the data directly from the cache. This cache is 1st LEVEL
		 * CACHE and its scope is session
		 */

		// UserDetails user = (UserDetails) session.get(UserDetails.class, 1);
		// UserDetails user2s = (UserDetails) session.get(UserDetails.class, 1);

		Query query1 = session.createQuery("from UserDetails user where user.userId = 1");

		query1.setCacheable(true);
		List users1 = query1.list();

		session.getTransaction().commit();
		session.close();

		/*
		 * when you run the main hibernate will call the database twice even
		 * though there is no change/update in the database. This can be avoided
		 * by using 2nd level of caching
		 */
		/*
		 * CONFIGURING 2nd LEVEL CACHE There is a property in the
		 * hibernate-cfg-xml file named cache.provider_class, where we provide
		 * the cache provider class name. By default it is
		 * org.hibernate.cache.NoCacheProvider (where NoCacheProvider is the
		 * class name). If we look in the hibernate jar file in the project
		 * directory we can find many cache provider. We can use any provider by
		 * mentioing the class name in the cache property tag. For example
		 * //<property
		 * name="cache.provider_class">org.hibernate.cache.EhCacheProvider</
		 * property> By default the 2nd level cache is switched off. we can
		 * enable using the following property tag
		 * 
		 * @<property name="cache.use_second_level_cache">true</property> After
		 * this we will add EHCACHE dependency in pom.xml to download the
		 * EHCACHE jar file
		 * 
		 * After that we have to configure the Entity/Class that we want to be
		 * cached at 2nd level
		 * 
		 * Even after you have configure the class and hibernate-cfg.xml file so
		 * we can use 2nd level cache, still when we run the same query in
		 * different sessions the hibernate does'nt know at the query level that
		 * the data is available in the cache. So to enable this query caching
		 * we have to add property called <property
		 * name="cache.use_query_cache">true</property> and set it true.This is
		 * because the query is not stored in the 2nd level cache. After this we
		 * will write query.setCacheable(true) as shown in the above and below
		 * session
		 */
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();

		// UserDetails user3 = (UserDetails) session.get(UserDetails.class, 1);

		Query query2 = session2.createQuery("from UserDetails user where user.userId = 1");
		/*
		 * We have to set query.setCacheable(true) not only to put the query
		 * into the 2nd level cache but to also check before running whether
		 * there is any same query result in the query cache
		 */
		query2.setCacheable(true);
		List users2 = query2.list();

		session2.getTransaction().commit();
		session2.close();

	}

}
