package com.ers;

import java.util.ArrayList;
import java.util.List;

import com.ers.controller.ReimbursementController;
import com.ers.dao.ReimbursementDAO;
import com.ers.dao.ReimbursementStatusDAO;
import com.ers.dao.ReimbursementTypeDAO;
import com.ers.dao.UserDAO;
import com.ers.dao.UserRoleDAO;
import com.ers.model.Reimbursement;
import com.ers.model.ReimbursementStatus;
import com.ers.model.ReimbursementType;
import com.ers.model.User;
import com.ers.model.UserRole;
import com.ers.service.UserService;
import com.ers.util.HibernateUtil;

import io.javalin.Javalin;

public class MainDriver {
	
	public static HibernateUtil hUtil = new HibernateUtil();
	public static ReimbursementStatusDAO reimStatusDAO = new ReimbursementStatusDAO(hUtil);
	public static ReimbursementTypeDAO reimTypeDAO = new ReimbursementTypeDAO(hUtil);
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
		System.out.println(reimStatusDAO.selectAll());
		System.out.println(reimTypeDAO.selectAll());
		System.out.println(reimDAO.selectAll());
		System.out.println(userRDAO.selectAll());
		System.out.println(userDAO.selectAll());
		
		app.post("/employee/login", rCon.postLogin);
		app.get("/employee/session", rCon.getSessUser);
		app.post("/employee/reimbursement-submission", rCon.postReimForm);
		
		//hUtil.closeSes();
	}
	
	public static void insertInitialValues() {
		
		List<Reimbursement> reimList = new ArrayList<Reimbursement>();
		
		UserRole userRole1 = new UserRole("Employee");
		UserRole userRole2 = new UserRole("Finance manager");
		
		ReimbursementType reimType1 = new ReimbursementType("Lodging");
		ReimbursementType reimType2 = new ReimbursementType("Travel");
		ReimbursementType reimType3 = new ReimbursementType("Food");
		ReimbursementType reimType4 = new ReimbursementType("Other");
		
		ReimbursementStatus reimStatus1 = new ReimbursementStatus("Approved");
		ReimbursementStatus reimStatus2 = new ReimbursementStatus("Pending");
		ReimbursementStatus reimStatus3 = new ReimbursementStatus("Denied");
		
		User user1 = new User("user1", "password", "John", "Jacobelli", "jj@gmail.com", userRole1);
		User user2 = new User("user2", "password", "Mister", "Manager", "mm@gmail.com", userRole2);
		userRole1.getUserList().add(user1);
		userRole2.getUserList().add(user2);
		
		reimStatusDAO.insert(reimStatus1);
		reimStatusDAO.insert(reimStatus2);
		reimStatusDAO.insert(reimStatus3);
		reimTypeDAO.insert(reimType1);
		reimTypeDAO.insert(reimType2);
		reimTypeDAO.insert(reimType3);
		reimTypeDAO.insert(reimType4);
		userRDAO.insert(userRole1);
		userRDAO.insert(userRole2);
		userDAO.insert(user1);
		userDAO.insert(user2);
	}

}
