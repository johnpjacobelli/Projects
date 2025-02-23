package com.ers.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ers.model.ReimbursementType;
import com.ers.util.HibernateUtil;

public class ReimbursementTypeDAO implements GenericDAO<ReimbursementType>{
	
	private HibernateUtil hUtil;
	
	public ReimbursementTypeDAO() {
		// TODO Auto-generated constructor stub
	}

	public ReimbursementTypeDAO(HibernateUtil hUtil) {
		super();
		this.hUtil = hUtil;
	}
	
	public void insert(ReimbursementType ReimbursementType) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.save(ReimbursementType);
		tx.commit();
	}
	
	public void update(ReimbursementType ReimbursementType) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.update(ReimbursementType);
		tx.commit();
	}
	
	public ReimbursementType selectById(int id) {
		Session ses = hUtil.getSession();
		
		ReimbursementType ReimbursementType = ses.get(ReimbursementType.class, id);
		return ReimbursementType;
	}
	
	public ReimbursementType selectByName(String name) {
		Session ses = hUtil.getSession();
		
		Query<ReimbursementType> q = 
				ses.createQuery("from ReimbursementType where reimType = :givenname", ReimbursementType.class);
		q.setParameter("givenname", name);  
		List<ReimbursementType> rtList = q.list();
		if(rtList.size() == 0) {
			return null;
		}
		
		ReimbursementType reimStatus = rtList.get(0);
		return reimStatus;
	}
	
	public List<ReimbursementType> selectAll() {
		Session ses = hUtil.getSession();	
		
		List<ReimbursementType> rtList = ses.createQuery("from ReimbursementType", ReimbursementType.class).list();
		return rtList;
	}

}
