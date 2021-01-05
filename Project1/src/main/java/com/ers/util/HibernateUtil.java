package com.ers.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private Session ses;
	public static SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	public Session getSession() {
		
		if(ses == null) {
			ses = sf.openSession();
		}
		
		return ses;
	}
	
	public void closeSes() {
		ses.close();
		sf.close();
	}
}
