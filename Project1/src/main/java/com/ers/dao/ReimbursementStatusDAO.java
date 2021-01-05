package com.ers.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ers.model.ReimbursementStatus;
import com.ers.model.User;
import com.ers.util.HibernateUtil;

public class ReimbursementStatusDAO implements GenericDAO<ReimbursementStatus>{
	
	private HibernateUtil hUtil;
	
	public ReimbursementStatusDAO() {
		// TODO Auto-generated constructor stub
	}

	public ReimbursementStatusDAO(HibernateUtil hUtil) {
		super();
		this.hUtil = hUtil;
	}
	
	public void insert(ReimbursementStatus reimStatus) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.save(reimStatus);
		tx.commit();
//		ses.close();
	}
	
	public void update(ReimbursementStatus reimStatus) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.update(reimStatus);
		tx.commit();
//		ses.close();
	}
	
	public ReimbursementStatus selectById(int id) {
		Session ses = hUtil.getSession();
		
		ReimbursementStatus reimStatus = ses.get(ReimbursementStatus.class, id);
//		ses.close();
		return reimStatus;
	}
	
	public ReimbursementStatus selectByName(String name) {
		Session ses = hUtil.getSession();
		
		Query<ReimbursementStatus> q = ses.createQuery("from ReimbursementStatus where reimStatus = :givenname");
		q.setParameter("givenname", name);  
		List<ReimbursementStatus> rsList = q.list();
		if(rsList.size() == 0) {
			return null;
		}
		ReimbursementStatus reimStatus = rsList.get(0);
//		ses.close();
		return reimStatus;
	}
	
	public List<ReimbursementStatus> selectAll() {
		Session ses = hUtil.getSession();	
		
		List<ReimbursementStatus> rsList = ses.createQuery("from ReimbursementStatus", ReimbursementStatus.class).list();
//		ses.close();
		return rsList;
	}

}
