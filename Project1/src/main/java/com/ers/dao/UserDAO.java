package com.ers.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ers.model.User;
import com.ers.util.HibernateUtil;

public class UserDAO implements GenericDAO<User> {
	
	private HibernateUtil hUtil;
	
	public UserDAO() {
		// TODO Auto-generated constructor stub
	}

	public UserDAO(HibernateUtil hUtil) {
		super();
		this.hUtil = hUtil;
	}
	
	public void insert(User user) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.save(user);
		tx.commit();
//		ses.close();
	}
	
	public void update(User user) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.update(user);
		tx.commit();
//		ses.close();
	}
	
	public User selectById(int id) {
		Session ses = hUtil.getSession();
		
		User User = ses.get(User.class, id);
//		ses.close();
		return User;
	}
	
	public User selectByName(String name) {
		Session ses = hUtil.getSession();
		
		Query<User> q = ses.createQuery("from User where username = :givenname");
		q.setParameter("givenname",name);  
		List<User> uList = q.list();
		if(uList.size() == 0) {
			return null;
		}
		User user = uList.get(0);
//		ses.close();
		return user;
	}
	
	public List<User> selectAll() {
		Session ses = hUtil.getSession();	
		
		List<User> uList = ses.createQuery("from User", User.class).list();
//		ses.close();
		return uList;
	}

}
