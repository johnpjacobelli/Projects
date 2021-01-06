package com.ers.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ers_users")
public class User {
	
	@Id
	@Column(name = "ers_users_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userID;
	
	@Column(name = "ers_username")
	private String username;
	
	@Column(name = "ers_password")
	private String password;
	
	@Column(name = "user_first_name")
	private String firstName;
	
	@Column(name = "user_last_name")
	private String lastName;
	
	@Column(name = "user_email")
	private String email;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="user_role_id")
	@JsonIgnore
	private UserRole roleID;
	
	@OneToMany(mappedBy="reimAuthorID", fetch=FetchType.LAZY)
	private List<Reimbursement> reimSubmittedList = new ArrayList<Reimbursement>();
	
	@OneToMany(mappedBy="reimResolverID", fetch=FetchType.LAZY)
	private List<Reimbursement> reimResolvedList = new ArrayList<Reimbursement>();


	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int userID, String username, String password, String firstName, String lastName, String email,
			UserRole roleID, List<Reimbursement> reimSubmittedList, List<Reimbursement> reimResolvedList) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleID = roleID;
		this.reimSubmittedList = reimSubmittedList;
		this.reimResolvedList = reimResolvedList;
	}

	public User(String username, String password, String firstName, String lastName, String email,
			UserRole roleID, List<Reimbursement> reimSubmittedList, List<Reimbursement> reimResolvedList) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleID = roleID;
		this.reimSubmittedList = reimSubmittedList;
		this.reimResolvedList = reimResolvedList;
	}
	
	public User(String username, String password, String firstName, String lastName, String email,
			UserRole roleID) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleID = roleID;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRoleID() {
		return roleID;
	}

	public void setRoleID(UserRole roleID) {
		this.roleID = roleID;
	}

	public List<Reimbursement> getReimSubmittedList() {
		return reimSubmittedList;
	}

	public void setReimSubmittedList(List<Reimbursement> reimSubmittedList) {
		this.reimSubmittedList = reimSubmittedList;
	}

	public List<Reimbursement> getReimResolvedList() {
		return reimResolvedList;
	}

	public void setReimResolvedList(List<Reimbursement> reimResolvedList) {
		this.reimResolvedList = reimResolvedList;
	}

	public int getUserID() {
		return userID;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", roleID=" + roleID.getUserRole()
				+ ", reimSubmittedList=" + reimSubmittedList + ", reimResolvedList=" + reimResolvedList + "]";
	}
	
}