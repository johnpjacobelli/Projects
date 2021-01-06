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
@Table(name="ers_reimbursement_status")
public class ReimbursementStatus {
	
	@Id
	@Column(name = "reimb_status_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int reimStatusID;
	
	@Column(name = "reimb_status")
	private String reimStatus;
	
	@OneToMany(mappedBy="reimStatusID", fetch=FetchType.LAZY)
	private List<Reimbursement> reimList = new ArrayList<Reimbursement>();
	
	
	public ReimbursementStatus() {
		// TODO Auto-generated constructor stub
	}
	
	public ReimbursementStatus(int reimStatusID, String reimStatus, List<Reimbursement> reimList) {
		super();
		this.reimStatusID = reimStatusID;
		this.reimStatus = reimStatus;
		this.reimList = reimList;
	}

	public ReimbursementStatus(String reimStatus) {
		super();
		this.reimStatus = reimStatus;
	}

	
	public String getReimStatus() {
		return reimStatus;
	}

	public void setReimStatus(String reimStatus) {
		this.reimStatus = reimStatus;
	}

	public List<Reimbursement> getReimList() {
		return reimList;
	}

	public void setReimList(List<Reimbursement> reimList) {
		this.reimList = reimList;
	}

	public int getReimStatusID() {
		return reimStatusID;
	}

	
	@Override
	public String toString() {
		return "ReimbursementStatus [reimStatusID=" + reimStatusID + ", reimStatus=" + reimStatus + ", reimList="
				+ reimList + "]";
	}

}
