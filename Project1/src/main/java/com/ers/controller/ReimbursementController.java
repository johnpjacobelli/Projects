package com.ers.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ers.MainDriver;
import com.ers.dao.ReimbursementStatusDAO;
import com.ers.model.Reimbursement;
import com.ers.model.ReimbursementStatus;
import com.ers.model.ReimbursementType;
import com.ers.model.User;
import com.ers.service.ReimbursementService;
import com.ers.service.UserService;

import io.javalin.http.Handler;

public class ReimbursementController {
	
	private UserService uServ;
	
	public Handler postLogin = (ctx) -> {
		if(uServ.verifyCredentials(ctx.formParam("username"), ctx.formParam("password"))) {
			ctx.sessionAttribute("user", uServ.getByUsername((ctx.formParam("username"))));
			if(uServ.getByUsername((ctx.formParam("username"))).getRoleID().getUserRole().equals("Employee")) {
				ctx.redirect("/html/employeehome.html");
				MainDriver.log.info(uServ.getByUsername((ctx.formParam("username"))).getFirstName() + " logged in as" + 
				" an employee at " + new Timestamp(System.currentTimeMillis()));
			}
			
			else {
				ctx.redirect("/html/financemanagerhome.html");
				MainDriver.log.info(uServ.getByUsername((ctx.formParam("username"))).getFirstName() + " logged in as" + 
						" a manager at " + new Timestamp(System.currentTimeMillis()));
			}
		}
		
		else {
			ctx.redirect("/html/badlogin.html");
		}
	};
	
	public Handler getSessUser = (ctx) -> {
		User user = (User)ctx.sessionAttribute("user");
		user = MainDriver.userDAO.selectById(user.getUserID());
		ctx.json(user);
	};
	
	public Handler getStatusIDByName = (ctx) -> {
		if(ctx.pathParam("name").equals("All")) {
			ReimbursementStatus rs = new ReimbursementStatus(-9999, "ALL");
			ctx.json(rs);
		}
		else {
			ReimbursementStatus rs = new ReimbursementStatusDAO(MainDriver.hUtil).selectByName(ctx.pathParam("name"));
			ctx.json(rs);
		}
	};
	
	public Handler approveReim = (ctx) -> {
		Reimbursement reim = MainDriver.reimDAO.selectById(Integer.parseInt(ctx.pathParam("id")));
		ReimbursementStatus rs = MainDriver.reimStatusDAO.selectByName("Approved");
		User user = (User)ctx.sessionAttribute("user");
		User dbUser = MainDriver.userDAO.selectById(user.getUserID());
		reim.setReimResolverID(dbUser);
		reim.setReimResolved(new Timestamp(System.currentTimeMillis()));
		reim.setReimStatusID(rs);
		MainDriver.reimDAO.update(reim);
		MainDriver.log.info(dbUser.getFirstName() + " approved a reimbursement" + 
				" request at " + reim.getReimResolved());
	};
	
	public Handler declineReim = (ctx) -> {
		Reimbursement reim = MainDriver.reimDAO.selectById(Integer.parseInt(ctx.pathParam("id")));
		ReimbursementStatus rs = MainDriver.reimStatusDAO.selectByName("Denied");
		User user = (User)ctx.sessionAttribute("user");
		User dbUser = MainDriver.userDAO.selectById(user.getUserID());
		reim.setReimResolverID(dbUser);
		reim.setReimResolved(new Timestamp(System.currentTimeMillis()));
		reim.setReimStatusID(rs);
		MainDriver.reimDAO.update(reim);
		MainDriver.log.info(dbUser.getFirstName() + " declined a reimbursement" + 
				" request at " + reim.getReimResolved());
	};
	
	public Handler postReimForm = (ctx) -> {
		int amount = Integer.parseInt(ctx.formParam("reimAmount"));
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		String desc = ctx.formParam("reimDesc");
		String type = ctx.formParam("reimType");

		User currentUser = (User)ctx.sessionAttribute("user");
		User submittingUser = MainDriver.userDAO.selectById(currentUser.getUserID());
		
		ReimbursementStatus reimStatus = MainDriver.reimStatusDAO.selectByName("Pending");
		ReimbursementType reimType = MainDriver.reimTypeDAO.selectByName(type);
		
		Reimbursement reim = new Reimbursement(amount, currentTime, desc, submittingUser, reimStatus, reimType);
		reimType.getReimList().add(reim);
		reimStatus.getReimList().add(reim);
		submittingUser.getReimSubmittedList().add(reim);
		MainDriver.userDAO.update(submittingUser);
		
		MainDriver.reimDAO.insert(reim);
		ctx.redirect("/html/successful-submission.html");
		MainDriver.log.info(submittingUser.getFirstName() + " created new reimbursement" + 
				" request at " + new Timestamp(System.currentTimeMillis()));
	};
	
	public Handler getReimByType = (ctx) -> {
		String filter = ctx.pathParam("searchFilter");
		int typeID = Integer.parseInt(ctx.pathParam("id"));
		List<String> reimListString = new ArrayList<String>();
		List<Reimbursement> reimList;
		
		if(typeID == -9999) {
			reimList = MainDriver.reimDAO.selectAll();
		}
		
		else {
			reimList = new ReimbursementService(MainDriver.reimDAO).getByFilter(filter, typeID);
		}
			
		for(Reimbursement item : reimList) {
			reimListString.add(item.toString());
		}
		
		ctx.json(reimListString);
	};
	
	public ReimbursementController() {
		// TODO Auto-generated constructor stub
	}
	
	public ReimbursementController(UserService uServ) {
		super();
		this.uServ = uServ;
	}

}
