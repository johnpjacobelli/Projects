package com.bankapi.handler;

import java.util.List;

import com.bankapi.bankinfo.Account;
import com.bankapi.dao.AccountDAO;

import io.javalin.http.Handler;

public class AccountHandler {
	
	private static AccountDAO accountDAO = new AccountDAO();
	
	public static final Handler ACCOUNTGET = (ctx) -> {
		Account account = accountDAO.getAccountByID(Integer.parseInt(ctx.pathParam("accountID")), 
													Integer.parseInt(ctx.pathParam("clientID")));
		
		if(account != null){
			// return object to javalin and print
			ctx.json(account);
			System.out.println(account);
			System.out.println("______________________________");
		}
		
		else {
			System.out.println("Account does not exist or this client does not have access.");
			ctx.html("Account does not exist or this client does not have access.");
			ctx.status(404);
		}
	};
	
	public static final Handler ACCOUNTPOST = (ctx) -> {
		Account client = ctx.bodyAsClass(Account.class);
		if(accountDAO.insertAccount(Integer.parseInt(ctx.pathParam("clientID")), client.getBalance())) {
			System.out.println("Account successfully created.");
			ctx.html("Account successfully created.");
			ctx.status(201);
		}
		
		else  {
			System.out.println("Account not properly formatted or client does not exist; could not be created.");
			ctx.html("Account not properly formatted or client does not exist; could not be created.");
			ctx.status(404);
		}
	};
	
	public static final Handler ACCOUNTPUT = (ctx) -> {
		Account client = ctx.bodyAsClass(Account.class);
		if(accountDAO.updateAccountByID(Integer.parseInt(ctx.pathParam("accountID")), 
										Integer.parseInt(ctx.pathParam("clientID")), 
										client.getBalance())) {
			System.out.println("Account successfully updated.");
			ctx.html("Account successfully updated.");
		}
		
		else  {
			System.out.println("Account does not exist, client does not have access, or no changes were made; update failed");
			ctx.html("Account does not exist, client does not have access, or no changes were made; update failed");
			ctx.status(404);
		}
	};
	
	public static final Handler ACCOUNTDELETE = (ctx) -> {
		if(accountDAO.deleteAccountByID(Integer.parseInt(ctx.pathParam("accountID")), 
										Integer.parseInt(ctx.pathParam("clientID")))) {
			System.out.println("Account successfully removed.");
			ctx.html("Account successfully removed.");
		}
		
		else {
			System.out.println("Account could not be removed; does not exist for client.");
			ctx.html("Account could not be removed; does not exist for client.");
			ctx.status(404);
		}
	};
	
	public static final Handler ACCOUNTQUERY = (ctx) -> {
		List<Account> accounts = accountDAO.getAccountByAmount(ctx.queryParam("amountLessThan"), 
															   ctx.queryParam("amountGreaterThan"), 
															   Integer.parseInt(ctx.pathParam("clientID")));
			
		if(accounts.size() == 0) {
			ctx.status(404);
			System.out.println("Client/account does not exist.");
			ctx.html("Client/account does not exist.");
		}
		
		else {
			// return object to javalin and print
			ctx.json(accounts);
			for(Account tempAccount : accounts) {
				System.out.println(tempAccount);
			}
			
			System.out.println("______________________________");
		}
	};
}