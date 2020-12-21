package com.bankapi.handler;

import java.sql.CallableStatement;
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
		
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
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
	};
	
	public static final Handler ACCOUNTPOST = (ctx) -> {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			// create and prepare call
			String callString = "{ CALL insert_account(?, ?) }";
			CallableStatement databaseCall = conn.prepareCall(callString);

			// read in account into db call
			Account account = ctx.bodyAsClass(Account.class);
			databaseCall.setInt(1, account.getOwnerID());
			databaseCall.setInt(2, account.getBalance());
			
			databaseCall.executeUpdate();
			System.out.println("Account successfully added");
			ctx.html("Account successfully created");
			ctx.status(201);
		}
		
		catch(SQLException e) {
			System.out.println("Account not properly formatted or account with given ID already exists; could not be created");
			ctx.html("Account not properly formatted or account with given ID already exists; could not be created");
			ctx.status(404);
		}
	};
	
	public static final Handler ACCOUNTPUT = (ctx) -> {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			// create and prepare call
			String callString = "{ CALL update_balance(?, ?, ?) }";
			String query = "SELECT * FROM accounts WHERE accountID = ?;";
			CallableStatement databaseCall = conn.prepareCall(callString);
			PreparedStatement accountQuery = conn.prepareStatement(query);
			// query account before change for updates
			accountQuery.setInt(1, Integer.parseInt(ctx.pathParam("accountID")));
			ResultSet beforeQuery = accountQuery.executeQuery();
			// read in account into db call
			Account account = ctx.bodyAsClass(Account.class);
			databaseCall.setInt(1, Integer.parseInt(ctx.pathParam("accountID")));
			databaseCall.setInt(2, Integer.parseInt(ctx.pathParam("clientID")));
			databaseCall.setInt(3, account.getBalance());
			
			databaseCall.executeUpdate();
			ResultSet afterQuery = accountQuery.executeQuery();
			beforeQuery.next();
			afterQuery.next();
			
			// if no changes, error
			if(beforeQuery.getInt(1) == afterQuery.getInt(1) && 
			   beforeQuery.getInt(2) == afterQuery.getInt(2) &&
			   beforeQuery.getInt(3) == afterQuery.getInt(3)) {
				throw new SQLException();
			}
			
			Account oldAccount = new Account(beforeQuery.getInt(1), beforeQuery.getInt(2), beforeQuery.getInt(3));
			Account updatedAccount = new Account(afterQuery.getInt(1), afterQuery.getInt(2), afterQuery.getInt(3));
			System.out.println("Account updated from: " + oldAccount + " to : " + updatedAccount);
			ctx.html("Account updated from: " + oldAccount + " to : " + updatedAccount);
			ctx.status(201);
		}
		
		catch(SQLException e) {
			System.out.println("Account does not exist, client does not have access, or no changes were made; update failed");
			ctx.html("Account does not exist, client does not have access, or no changes were made; update failed");
			ctx.status(404);
		}
	};
	
	public static final Handler ACCOUNTDELETE = (ctx) -> {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			// check account count before delete
			String query = "SELECT COUNT(*) FROM accounts;";
			PreparedStatement accountQuery = conn.prepareStatement(query);
			ResultSet accountInfo = accountQuery.executeQuery();
			

			// create and prepare call
			String callString = "{ CALL delete_account(?, ?) }";
			CallableStatement databaseCall = conn.prepareCall(callString);

			// read in account into db call
			databaseCall.setInt(1, Integer.parseInt(ctx.pathParam("accountID")));
			databaseCall.setInt(2, Integer.parseInt(ctx.pathParam("clientID")));
			
			// execute delete and query all accounts again
			databaseCall.executeUpdate();
			ResultSet afterDeleteInfo = accountQuery.executeQuery();
			
			
			afterDeleteInfo.next();
			accountInfo.next();
			// check to see if difference in account count
			if(afterDeleteInfo.getInt(1) != accountInfo.getInt(1)) {
				System.out.println("Account successfully removed");
				ctx.html("Account successfully removed");
				ctx.status(201);
			}
			
			else {
				throw new SQLException();
			}
		}
		
		catch(SQLException e) {
			System.out.println("Account could not be removed; does not exist for client.");
			ctx.html("Account could not be removed; does not exist for client.");
			ctx.status(404);
		}
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
				query = "SELECT * FROM accounts WHERE ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(ctx.pathParam("clientID")));
			}
			
			else if(amountLT != null && amountGT == null) {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance < ? AND ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountLT));
				accountQuery.setInt(2, Integer.parseInt(ctx.pathParam("clientID")));
			}
			
			else if(amountLT == null && amountGT != null) {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance > ? AND ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountGT));
				accountQuery.setInt(2, Integer.parseInt(ctx.pathParam("clientID")));
			}
			
			else {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance > ? AND balance < ? AND ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountGT));
				accountQuery.setInt(2, Integer.parseInt(amountLT));
				accountQuery.setInt(3, Integer.parseInt(ctx.pathParam("clientID")));
			}
			
			// prepre string and save results
			ResultSet accountInfo = accountQuery.executeQuery();
			
			// return results after saving into account objects
			while(accountInfo.next()) {
				Account acct = new Account(accountInfo.getInt(1), accountInfo.getInt(2), accountInfo.getInt(3));
				accounts.add(acct);
			}
			
			if(accounts.size() == 0) {
				ctx.status(404);
				System.out.println("Client/account does not exist");
				ctx.html("Client/account does not exist");
			}
			
			else {
				// return object to javalin and print
				ctx.json(accounts);
				for(Account account : accounts) {
					System.out.println(account);
				}
				
				System.out.println("______________________________");
			}
		}
		
		catch(SQLException e) {
			System.out.println("Client/account does not exist");
			ctx.html("Client/account does not exist");
			ctx.status(404);
		}
	};
}