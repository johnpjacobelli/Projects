package com.ers.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ers.model.ReimbursementStatus;
import com.ers.model.UserRole;
import com.ers.util.HibernateUtil;

public class UserRoleDAO implements GenericDAO<UserRole> {
	
	private HibernateUtil hUtil;
	
	public UserRoleDAO() {
		// TODO Auto-generated constructor stub
	}

	public UserRoleDAO(HibernateUtil hUtil) {
		super();
		this.hUtil = hUtil;
	}
	
	public void insert(UserRole UserRole) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.save(UserRole);
		tx.commit();
//		ses.close();
	}
	
	public void update(UserRole UserRole) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.update(UserRole);
		tx.commit();
//		ses.close();
	}
	
	public UserRole selectById(int id) {
		Session ses = hUtil.getSession();
		
		UserRole UserRole = ses.get(UserRole.class, id);
//		ses.close();
		return UserRole;
	}
	
	public List<UserRole> selectAll() {
		Session ses = hUtil.getSession();	
		
		List<UserRole> urList = ses.createQuery("from UserRole", UserRole.class).list();
//		ses.close();
		return urList;
	}

	public UserRole selectByName(String name) {
		Session ses = hUtil.getSession();
		
		Query<UserRole> q = ses.createQuery("from UserRole where userRole = :givenname");
		q.setParameter("givenname", name);  
		List<UserRole> urList = q.list();
		if(urList.size() == 0) {
			return null;
		}
		UserRole reimStatus = urList.get(0);
//		ses.close();
		return reimStatus;
	}

}