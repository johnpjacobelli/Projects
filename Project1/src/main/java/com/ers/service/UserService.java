package com.ers.service;

import com.ers.dao.UserDAO;
import com.ers.model.User;

public class UserService {
	
	private UserDAO uDAO;
	
	public UserService() {
		// TODO Auto-generated constructor stub
	}

	public UserService(UserDAO uDAO) {
		super();
		this.uDAO = uDAO;
	}
	
	public User getByUsername(String name) {
		User user = uDAO.selectByName(name);
		
		if(user == null) {
			throw new NullPointerException();
		}
		
		return user;
	}
	
	public User getById(int id) {
		User user = uDAO.selectById(id);
		
		if(user == null) {
			throw new NullPointerException();
		}
		
		return user;
	}
	
	public boolean verifyCredentials(String name, String password) {
		boolean isVerified = false;
		User user = uDAO.selectByName(name);
		if(user != null && user.getPassword().equals(password)) {
			isVerified = true;
		}
		
		return isVerified;
	}

}
