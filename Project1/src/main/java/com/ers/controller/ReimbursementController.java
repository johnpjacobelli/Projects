package com.ers.controller;

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
	
	
	
	public ReimbursementController() {
		// TODO Auto-generated constructor stub
	}
	
	public ReimbursementController(UserService uServ) {
		super();
		this.uServ = uServ;
	}

}
