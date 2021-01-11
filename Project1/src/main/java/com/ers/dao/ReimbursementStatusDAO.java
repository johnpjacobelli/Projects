package com.ers.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ers.model.ReimbursementStatus;
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
	}
	
	public void update(ReimbursementStatus reimStatus) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.update(reimStatus);
		tx.commit();
	}
	
	public ReimbursementStatus selectById(int id) {
		Session ses = hUtil.getSession();
		
		ReimbursementStatus reimStatus = ses.get(ReimbursementStatus.class, id);
		return reimStatus;
	}
	
	public ReimbursementStatus selectByName(String name) {
		Session ses = hUtil.getSession();
		Query<ReimbursementStatus> q = ses.createQuery("from ReimbursementStatus where reimb_status = :givenname", ReimbursementStatus.class);
		q.setParameter("givenname", name);  
		List<ReimbursementStatus> rsList = q.list();
		if(rsList.size() == 0) {
			return null;
		}
		
		ReimbursementStatus reimStatus = rsList.get(0);
		return reimStatus;
	}
	
	public List<ReimbursementStatus> selectAll() {
		Session ses = hUtil.getSession();	
		
		List<ReimbursementStatus> rsList = ses.createQuery("from ReimbursementStatus", ReimbursementStatus.class).list();
		return rsList;
	}

}
