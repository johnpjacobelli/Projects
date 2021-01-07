package com.ers.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ers_reimbursement_type")
public class ReimbursementType {
	
	@Id
	@Column(name = "reimb_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int reimTypeID;
	
	@Column(name = "reimb_type")
	private String reimType;
	
	@OneToMany(mappedBy="reimTypeID", fetch=FetchType.LAZY)
	private List<Reimbursement> reimList = new ArrayList<Reimbursement>();
	
	
	public ReimbursementType() {
		// TODO Auto-generated constructor stub
	}

	public ReimbursementType(int reimTypeID, String reimType, List<Reimbursement> reimList) {
		super();
		this.reimTypeID = reimTypeID;
		this.reimType = reimType;
		this.reimList = reimList;
	}
	
	public ReimbursementType(String reimType) {
		super();
		this.reimType = reimType;
	}


	public String getReimType() {
		return reimType;
	}

	public void setReimType(String reimType) {
		this.reimType = reimType;
	}

	public List<Reimbursement> getReimList() {
		return reimList;
	}

	public void setReimList(List<Reimbursement> reimList) {
		this.reimList = reimList;
	}

	public int getReimTypeID() {
		return reimTypeID;
	}


	@Override
	public String toString() {
		return "ReimbursementType [reimTypeID=" + reimTypeID + ", reimType=" + reimType + "]";
	}
	
}
