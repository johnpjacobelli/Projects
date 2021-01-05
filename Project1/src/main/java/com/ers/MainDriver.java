package com.ers;

import java.util.ArrayList;
import java.util.List;

import com.ers.controller.ReimbursementController;
import com.ers.dao.ReimbursementDAO;
import com.ers.dao.UserDAO;
import com.ers.dao.UserRoleDAO;
import com.ers.model.User;
import com.ers.model.UserRole;
import com.ers.service.UserService;
import com.ers.util.HibernateUtil;

import io.javalin.Javalin;

public class MainDriver {
	
	public static HibernateUtil hUtil = new HibernateUtil();
	public static ReimbursementDAO reimDAO = new ReimbursementDAO(hUtil);
	public static UserRoleDAO userRDAO = new UserRoleDAO(hUtil);
	public static UserDAO userDAO = new UserDAO(hUtil);
	
	public static void main(String[] args) {
		
		ReimbursementController rCon = new ReimbursementController(new UserService(new UserDAO(new HibernateUtil())));
		
		Javalin app = Javalin.create(config -> {
			config.addStaticFiles("/frontend");
		});
		app.start(9001);
		
		insertInitialValues();
		System.out.println(userRDAO.selectAll());
		System.out.println(userDAO.selectAll());
		
		app.post("/employee/login", rCon.postLogin);
		app.get("/employee/session", rCon.getSessUser);
		
		//hUtil.closeSes();
	}
	
	public static void insertInitialValues() {
		
		List<User> u1List = new ArrayList<User>();
		UserRole userRole1 = new UserRole("Employee", u1List);
		UserRole userRole2 = new UserRole("Finance manager", u1List);
		
		User user1 = new User("user1", "password", "John", "Jacobelli", "jj@gmail.com", userRole1);
		User user2 = new User("user2", "password", "Mister", "Manager", "mm@gmail.com", userRole2);
		u1List.add(user1);
		u1List.add(user2);
		
		userRDAO.insert(userRole1);
		userRDAO.insert(userRole2);
		userDAO.insert(user1);
		userDAO.insert(user2);
	}

}
