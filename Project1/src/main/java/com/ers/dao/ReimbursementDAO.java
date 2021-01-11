package com.ers.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ers.model.Reimbursement;
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
	}
	
	public void update(Reimbursement Reimbursement) {
		Session ses = hUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.update(Reimbursement);
		tx.commit();
	}
	
	public Reimbursement selectById(int id) {
		Session ses = hUtil.getSession();
		
		Reimbursement Reimbursement = ses.get(Reimbursement.class, id);
		return Reimbursement;
	}
	
	public Reimbursement selectByName(String name) {
		Session ses = hUtil.getSession();
		
		Query<Reimbursement> q = ses.createQuery("from Reimbursement where reimType = :givenname", Reimbursement.class);
		q.setParameter("givenname", name);  
		List<Reimbursement> rList = q.list();
		if(rList.size() == 0) {
			return null;
		}
		
		Reimbursement reimbursement = rList.get(0);
		return reimbursement;
	}
	
	public List<Reimbursement> selectAll() {
		Session ses = hUtil.getSession();	
		
		List<Reimbursement> rList = ses.createQuery("from Reimbursement", Reimbursement.class).list();
		return rList;
	}
	
	public List<Reimbursement> selectAllWhereXY(String column, int searchID) {
		Session ses = hUtil.getSession();	
		
		Query<Reimbursement> q = ses.createQuery("from Reimbursement where " + column + " = :givenID", Reimbursement.class);
		q.setParameter("givenID", searchID);  
		List<Reimbursement> rList = q.list();
		return rList;
	}

}
