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
@Table(name="ers_user_roles")
public class UserRole {
	
	@Id
	@Column(name = "ers_user_role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userRoleID;
	
	@Column(name = "user_role")
	private String userRole;
	
	@OneToMany(mappedBy="roleID", fetch=FetchType.LAZY)
	private List<User> userList = new ArrayList<User>();
	
	
	public UserRole() {
		// TODO Auto-generated constructor stub
	}

	public UserRole(int userRoleID, String userRole, List<User> userList) {
		super();
		this.userRoleID = userRoleID;
		this.userRole = userRole;
		this.userList = userList;
	}
	
	public UserRole(String userRole) {
		super();
		this.userRole = userRole;
	}
	

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getUserRoleID() {
		return userRoleID;
	}
	
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	

	@Override
	public String toString() {
		return "UserRole [userRoleID=" + userRoleID + ", userRole=" + userRole + ", userList=" + userList + "]";
	}
	
}
