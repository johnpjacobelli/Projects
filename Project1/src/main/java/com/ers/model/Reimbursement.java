package com.ers.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ers_reimbursement")
public class Reimbursement {

	@Id
	@Column(name = "reimb_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int reimID;

	@Column(name = "reimb_amount")
	private int reimAmount;

	@Column(name = "reimb_submitted")
	private Timestamp reimSubmitted;

	@Column(name = "reimb_resolved")
	private Timestamp reimResolved;

	@Column(name = "reimb_description")
	private String reimDesc;

//	@Column(name = "reimb_receipt")
//	private Object reimReceipt;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "reimb_author")
	@JsonIgnore
	private User reimAuthorID;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "reimb_resolver")
	@JsonIgnore
	private User reimResolverID;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "reimb_status_id")
	@JsonIgnore
	private ReimbursementStatus reimStatusID;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "reimb_type_id")
	@JsonIgnore
	private ReimbursementType reimTypeID;

	public Reimbursement() {
		// TODO Auto-generated constructor stub
	}

	public Reimbursement(int reimID, int reimAmount, Timestamp reimSubmitted, Timestamp reimResolved, String reimDesc,
			User reimAuthorID, User reimResolverID, ReimbursementStatus reimStatusID, ReimbursementType reimTypeID) {
		super();
		this.reimID = reimID;
		this.reimAmount = reimAmount;
		this.reimSubmitted = reimSubmitted;
		this.reimResolved = reimResolved;
		this.reimDesc = reimDesc;
		this.reimAuthorID = reimAuthorID;
		this.reimResolverID = reimResolverID;
		this.reimStatusID = reimStatusID;
		this.reimTypeID = reimTypeID;
	}

	public Reimbursement(int reimAmount, Timestamp reimSubmitted, Timestamp reimResolved, String reimDesc, User reimAuthorID,
			User reimResolverID, ReimbursementStatus reimStatusID, ReimbursementType reimTypeID) {
		super();
		this.reimAmount = reimAmount;
		this.reimSubmitted = reimSubmitted;
		this.reimResolved = reimResolved;
		this.reimDesc = reimDesc;
		this.reimAuthorID = reimAuthorID;
		this.reimResolverID = reimResolverID;
		this.reimStatusID = reimStatusID;
		this.reimTypeID = reimTypeID;
	}

	public Reimbursement(int reimAmount, Timestamp reimSubmitted, String reimDesc, User reimAuthorID,
			ReimbursementStatus reimStatusID, ReimbursementType reimTypeID) {
		super();
		this.reimAmount = reimAmount;
		this.reimSubmitted = reimSubmitted;
		this.reimDesc = reimDesc;
		this.reimAuthorID = reimAuthorID;
		this.reimStatusID = reimStatusID;
		this.reimTypeID = reimTypeID;
	}

	public int getReimAmount() {
		return reimAmount;
	}

	public void setReimAmount(int reimAmount) {
		this.reimAmount = reimAmount;
	}

	public Timestamp getReimSubmitted() {
		return reimSubmitted;
	}

	public void setReimSubmitted(Timestamp reimSubmitted) {
		this.reimSubmitted = reimSubmitted;
	}

	public Timestamp getReimResolved() {
		return reimResolved;
	}

	public void setReimResolved(Timestamp reimResolved) {
		this.reimResolved = reimResolved;
	}

	public String getReimDesc() {
		return reimDesc;
	}

	public void setReimDesc(String reimDesc) {
		this.reimDesc = reimDesc;
	}

	public User getReimAuthorID() {
		return reimAuthorID;
	}

	public void setReimAuthorID(User reimAuthorID) {
		this.reimAuthorID = reimAuthorID;
	}

	public User getReimResolverID() {
		return reimResolverID;
	}

	public void setReimResolverID(User reimResolverID) {
		this.reimResolverID = reimResolverID;
	}

	public ReimbursementStatus getReimStatusID() {
		return reimStatusID;
	}

	public void setReimStatusID(ReimbursementStatus reimStatusID) {
		this.reimStatusID = reimStatusID;
	}

	public ReimbursementType getReimTypeID() {
		return reimTypeID;
	}

	public void setReimTypeID(ReimbursementType reimTypeID) {
		this.reimTypeID = reimTypeID;
	}

	public int getReimID() {
		return reimID;
	}

	@Override
	public String toString() {
		return "reimID=" + reimID + ", reimAmount=" + reimAmount + ", reimSubmitted=" + reimSubmitted
				+ ", reimResolved=" + reimResolved + ", reimDesc=" + reimDesc + ", reimAuthorID="
				+ reimAuthorID.getFirstName() + " " + reimAuthorID.getLastName() + ", reimResolverID="
				+ ((reimResolverID == null) ? "null"
						: (reimResolverID.getFirstName() + " " + reimResolverID.getLastName()))
				+ ", reimStatusID=" + reimStatusID.getReimStatus() + ", reimTypeID=" + reimTypeID.getReimType();
	}

}
