package org.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

public class HibernateUtil
{
   
	public static final SessionFactory sessionFactory=buildSessionFactory();
	private static SessionFactory buildSessionFactory()
	{
		
		try
		{
			return new Configuration().configure("myconfiguration.xml").buildSessionFactory();
		}catch(Throwable throwable)
		{
			throwable.printStackTrace();
			new ExceptionInInitializerError(throwable);
			return null;
		}
	}
	public static SessionFactory getSessionFactory()
	{
		Statistics statistics=sessionFactory.getStatistics();
		
		statistics.logSummary();
		//System.out.println(statistics.getConnectCount());
		return sessionFactory;
	}
	
}
