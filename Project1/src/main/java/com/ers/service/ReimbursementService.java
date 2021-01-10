package com.ers.service;

import java.util.List;

import com.ers.dao.ReimbursementDAO;
import com.ers.model.Reimbursement;

public class ReimbursementService {
	
	private ReimbursementDAO rDAO;
	
	public ReimbursementService() {
		// TODO Auto-generated constructor stub
	}

	public ReimbursementService(ReimbursementDAO rDAO) {
		super();
		this.rDAO = rDAO;
	}
	
//	public List<Reimbursement> getByUser(int id) {
//		List<Reimbursement> Reimbursement = rDAO.selectAllForUser(id);
//		
//		if(Reimbursement == null) {
//			throw new NullPointerException();
//		}
//		
//		return Reimbursement;
//	}
	
	public List<Reimbursement> getByFilter(String column, int id) {
		List<Reimbursement> Reimbursement = rDAO.selectAllWhereXY(column, id);
		
		if(Reimbursement == null) {
			throw new NullPointerException();
		}
		
		return Reimbursement;
	}
	
}
