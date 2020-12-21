package com.bankapi.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bankapi.bankinfo.Account;

import io.javalin.http.Handler;

public class AccountHandler {
	
	private static String url = "jdbc:mariadb://database-1.c0xi5suntsgb.us-west-1.rds.amazonaws.com:3306/Project 0";
	private static String username = "firstuser";
	private static String password = "mypassword";
	
	
	// Retrieve specific account info
	public static final Handler ACCOUNTGET = (ctx) -> {
		
		int accountID = Integer.parseInt(ctx.pathParam("accountID"));
		int clientID = Integer.parseInt(ctx.pathParam("clientID"));
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			// prepare string
			String query = "SELECT * FROM accounts WHERE accountID = ? AND accountOwnerID = ?;";
			
			PreparedStatement accountQuery = conn.prepareStatement(query);
			accountQuery.setInt(1, accountID);
			accountQuery.setInt(2, clientID);
			
			// execute query and save info in account object
			ResultSet accountInfo = accountQuery.executeQuery();
			accountInfo.next();
			Account acct = new Account(accountInfo.getInt(1), accountInfo.getInt(2), accountInfo.getInt(3));
			
			// return object to javalin and print
			ctx.json(acct);
			System.out.println(acct);
			System.out.println("______________________________");
		}
		
		catch(SQLException e) {
			System.out.println("Account does not exist");
			ctx.html("Account does not exist");
			ctx.status(404);
		}
		
//		System.out.println("Account info retrieved");
//		System.out.println("ClientID: " + ctx.pathParam("clientID") + ", AccountID: " + ctx.pathParam("accountID"));
//		
//		ctx.json(new Account(Integer.parseInt(ctx.pathParam("clientID")), Integer.parseInt(ctx.pathParam("accountID")), 1000));
//		// should actually look like 
//		// ctx.json(new Account(SQL.GET(OWNER_ID), SQL.GET(ACCOUNT_ID), SQL.GET(BALANCE)));
	};
	
	public static final Handler ACCOUNTPOST = (ctx) -> {
		// TODO SQL check if account exists
			// if not, SQL input for account info
		
		System.out.println("Account info input successfully");
		
		ctx.json("Account added: " + new Account(1, Integer.parseInt(ctx.pathParam("accountID")), 1000));
		System.out.println("ClientID: " + ctx.pathParam("clientID") + ", AccountID: " + ctx.pathParam("accountID"));
		// should actually look like 
		// ctx.json(new Account(SQL.GET(OWNER_ID), SQL.GET(ACCOUNT_ID), SQL.GET(BALANCE)));
		
			// if so... error?
	};
	
	public static final Handler ACCOUNTPUT = (ctx) -> {
		// TODO SQL check if account exists
			// if not, SQL input for account info
		
		System.out.println("Account info updated successfully");
		
		ctx.json("Account added: " + new Account(1, Integer.parseInt(ctx.pathParam("accountID")), 1000));
		System.out.println("ClientID: " + ctx.pathParam("clientID") + ", AccountID: " + ctx.pathParam("accountID"));
		// should actually look like 
		// ctx.json(new Account(SQL.GET(OWNER_ID), SQL.GET(ACCOUNT_ID), SQL.GET(BALANCE)));
		
			// if so... error?
	};
	
	public static final Handler ACCOUNTDELETE = (ctx) -> {
		// TODO check if account exists
			// if so, delete
		
		System.out.println("Account successfully deleted");
		
		ctx.json("Account removed: " + new Account(1, Integer.parseInt(ctx.pathParam("accountID")), 1000));
		System.out.println("ClientID: " + ctx.pathParam("clientID") + ", AccountID: " + ctx.pathParam("accountID"));
		// should actually look like 
		// ctx.json(new Account(SQL.GET(OWNER_ID), SQL.GET(ACCOUNT_ID), SQL.GET(BALANCE)));
		
			// if not, error
	};
	
	public static final Handler ACCOUNTQUERY = (ctx) -> {
		// TODO SQL search for account info
				// if no info, error
		String amountLT = ctx.queryParam("amountLessThan");
		String amountGT = ctx.queryParam("amountGreaterThan");
		List<Account> accounts = new ArrayList<Account>();
		PreparedStatement accountQuery;
		String query;
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			
			if(amountLT == null && amountGT == null) {
				// prepare string
				query = "SELECT * FROM accounts;";
				accountQuery = conn.prepareStatement(query);
			}
			
			else if(amountLT != null && amountGT == null) {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance < ?;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountLT));
			}
			
			else if(amountLT == null && amountGT != null) {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance > ?;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountGT));
			}
			
			else {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance > ? AND balance < ?;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountGT));
				accountQuery.setInt(2, Integer.parseInt(amountLT));
			}
			
			// prepre string and save results
			ResultSet accountInfo = accountQuery.executeQuery();
			
			// return results after saving into account objects
			while(accountInfo.next()) {
				Account acct = new Account(accountInfo.getInt(1), accountInfo.getInt(2), accountInfo.getInt(3));
				accounts.add(acct);
			}
			
			// return object to javalin and print
			ctx.json(accounts);
			for(Account account : accounts) {
				System.out.println(account);
			}
			System.out.println("______________________________");
		}
		
		catch(SQLException e) {
			System.out.println("No accounts match/exist");
			ctx.html("No accounts match/exist");
			ctx.status(404);
		}
	};
}
