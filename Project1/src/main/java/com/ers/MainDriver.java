package com.ers;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

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
	
	public final static Logger log = Logger.getLogger(MainDriver.class);
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
		
		app.post("/employee/login", rCon.postLogin);
		app.get("/employee/session", rCon.getSessUser);
		app.post("/employee/reimbursement-submission", rCon.postReimForm);
		app.get("/employee/users-submissions/:searchFilter/:id", rCon.getReimByType);
		app.get("/employee/reimbursement-column-id/:name", rCon.getStatusIDByName);
		app.put("/manager/approve/:id", rCon.approveReim);
		app.put("/manager/decline/:id", rCon.declineReim);
		
	}
	
	public static void insertInitialValues() {
		
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
		User user2 = new User("user2", "password1", "Alex", "Smith", "yh@gmail.com", userRole1);
		User user3 = new User("user3", "p", "Mister", "Manager", "mm@gmail.com", userRole2);
		User user4 = new User("user4", "mypassword", "Hello", "World", "hw@gmail.com", userRole2);
		userRole1.getUserList().add(user1);
		userRole1.getUserList().add(user2);
		userRole2.getUserList().add(user3);
		userRole2.getUserList().add(user4);
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		Timestamp previousTime = new Timestamp(System.currentTimeMillis() - 10000);
		Reimbursement reim1 = new Reimbursement(10_000, currentTime, "Test", user1, reimStatus2, reimType1);
		Reimbursement reim2 = new Reimbursement(10_000_000, currentTime, "Test again", user2, reimStatus2, reimType3);
		Reimbursement reim3 = new Reimbursement(10_000_000, previousTime, currentTime, "Test again 2", user1, user3, reimStatus1, reimType4);
		Reimbursement reim4 = new Reimbursement(10_000_000, previousTime, currentTime, "Test again 4", user2, user4, reimStatus3, reimType2);

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
		userDAO.insert(user3);
		userDAO.insert(user4);
		reimDAO.insert(reim1);
		reimDAO.insert(reim2);
		reimDAO.insert(reim3);
		reimDAO.insert(reim4);
	}

}
