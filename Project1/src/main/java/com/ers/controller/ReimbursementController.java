package com.ers.controller;

import java.sql.Date;
import java.util.List;

import com.ers.MainDriver;
import com.ers.model.Reimbursement;
import com.ers.model.ReimbursementStatus;
import com.ers.model.ReimbursementType;
import com.ers.model.User;
import com.ers.service.UserService;

import io.javalin.http.Handler;

public class ReimbursementController {
	
	private UserService uServ;
	
	public Handler postLogin = (ctx) -> {
		if(uServ.verifyCredentials(ctx.formParam("username"), ctx.formParam("password"))) {
			ctx.sessionAttribute("user", uServ.getByUsername((ctx.formParam("username"))));
			if(uServ.getByUsername((ctx.formParam("username"))).getRoleID().getUserRole().equals("Employee")) {
				ctx.redirect("/html/employeehome.html");
			}
			
			else {
				ctx.redirect("/html/financemanagerhome.html");
			}
		}
		
		else {
			ctx.redirect("/html/badlogin.html");
		}
	};
	
	public Handler getSessUser = (ctx) -> {
		System.out.println((User)ctx.sessionAttribute("user"));
		User user = (User)ctx.sessionAttribute("user");
		ctx.json(user);
	};
	
	public Handler postReimForm = (ctx) -> {
		//Reimbursement reim = new Reimbursement(ctx.formParam("reimType"));
		int amount = Integer.parseInt(ctx.formParam("reimAmount"));
		Date currentTime = new Date(System.currentTimeMillis());
		String desc = ctx.formParam("reimDesc");
		String type = ctx.formParam("reimType");

		User currentUser = (User)ctx.sessionAttribute("user");
		User submittingUser = MainDriver.userDAO.selectById(currentUser.getUserID());
		
		ReimbursementStatus reimStatus = MainDriver.reimStatusDAO.selectByName("Pending");
		ReimbursementType reimType = MainDriver.reimTypeDAO.selectByName(type);
		
		Reimbursement reim = new Reimbursement(amount, currentTime, desc, submittingUser, reimStatus, reimType);
		reimType.getReimList().add(reim);
		reimStatus.getReimList().add(reim);
		System.out.println(reimStatus.getReimList());
		submittingUser.getReimSubmittedList().add(reim);
		MainDriver.userDAO.update(submittingUser);
		
		MainDriver.reimDAO.insert(reim);
		System.out.println(MainDriver.reimDAO.selectAll());
		ctx.redirect("/html/successful-submission.html");
	};
	
	public Handler getUsersSubs = (ctx) -> {
		System.out.println("hello");
		List<Reimbursement> reim = MainDriver.reimDAO.selectAll();
		System.out.println(reim);
		ctx.json(reim);
		ctx.json(reim.get(0).getReimAuthorID().getFirstName());
	};
	
	
	
	public ReimbursementController() {
		// TODO Auto-generated constructor stub
	}
	
	public ReimbursementController(UserService uServ) {
		super();
		this.uServ = uServ;
	}

}
