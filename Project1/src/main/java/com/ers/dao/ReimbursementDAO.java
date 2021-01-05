package com.ers.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ers.model.Reimbursement;
import com.ers.model.ReimbursementType;
import com.ers.util.HibernateUtil;

public class ReimbursementDAO implements GenericDAO<Reimbursement>{
	
	private HibernateUtil hUtil;
	
	public ReimbursementDAO() {
		// TODO Auto-generated constructor stub
	}

	public ReimbursementDAO(HibernateUtil hUtil) {
		super();
		this.hUtil = hUtil;
	}
	
	public void insert(Reimbursement Reimbursement) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.save(Reimbursement);
		tx.commit();
//		ses.close();
	}
	
	public void update(Reimbursement Reimbursement) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.update(Reimbursement);
		tx.commit();
//		ses.close();
	}
	
	public Reimbursement selectById(int id) {
		Session ses = hUtil.getSession();
		
		Reimbursement Reimbursement = ses.get(Reimbursement.class, id);
//		ses.close();
		return Reimbursement;
	}
	
	public Reimbursement selectByName(String name) {
		Session ses = hUtil.getSession();
		
		Query<Reimbursement> q = ses.createQuery("from Reimbursement where reimType = :givenname");
		q.setParameter("givenname", name);  
		List<Reimbursement> rList = q.list();
		if(rList.size() == 0) {
			return null;
		}
		Reimbursement reimStatus = rList.get(0);
//		ses.close();
		return reimStatus;
	}
	
	public List<Reimbursement> selectAll() {
		Session ses = hUtil.getSession();	
		
		List<Reimbursement> rList = ses.createQuery("from Reimbursement", Reimbursement.class).list();
//		ses.close();
		return rList;
	}

}
