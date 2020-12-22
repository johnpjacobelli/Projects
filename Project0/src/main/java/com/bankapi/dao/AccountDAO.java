package com.bankapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bankapi.bankinfo.Account;

public class AccountDAO {
	
	public AccountDAO() {
		// TODO Auto-generated constructor stub
	}
	
	private static String url = "jdbc:mariadb://database-1.c0xi5suntsgb.us-west-1.rds.amazonaws.com:3306/Project 0";
	private static String username = "firstuser";
	private static String password = "mypassword";
	
	public Account getAccountByID(int accountID, int clientID) {
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
			
			return acct;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	};
	
	public boolean insertAccount(int clientID, int balance) {
		boolean isSuccessfulInsert = false;
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			// create and prepare call
			String callString = "{ CALL insert_account(?, ?) }";
			CallableStatement databaseCall = conn.prepareCall(callString);

			// read in account into db call
			databaseCall.setInt(1, clientID);
			databaseCall.setInt(2, balance);
			databaseCall.executeUpdate();
			isSuccessfulInsert = true;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccessfulInsert;
	};
	
	public boolean updateAccountByID(int accountID, int clientID, int balance) {
		boolean isSuccessfulUpdate = false;
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			// create and prepare call
			String callString = "{ CALL update_balance(?, ?, ?) }";
			String query = "SELECT * FROM accounts WHERE accountID = ?;";
			CallableStatement databaseCall = conn.prepareCall(callString);
			PreparedStatement accountQuery = conn.prepareStatement(query);
			
			// query account before change for updates
			accountQuery.setInt(1, accountID);
			ResultSet beforeQuery = accountQuery.executeQuery();
			
			// read in account into db call
			databaseCall.setInt(1, accountID);
			databaseCall.setInt(2, clientID);
			databaseCall.setInt(3, balance);
			
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
			
			isSuccessfulUpdate = true;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccessfulUpdate;
	};
	
	public boolean deleteAccountByID(int accountID, int clientID) {
		boolean isSuccessfulDelete = false;
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			// check account count before delete
			String query = "SELECT COUNT(*) FROM accounts;";
			PreparedStatement accountQuery = conn.prepareStatement(query);
			ResultSet accountInfo = accountQuery.executeQuery();

			// create and prepare call
			String callString = "{ CALL delete_account(?, ?) }";
			CallableStatement databaseCall = conn.prepareCall(callString);

			// read in account into db call
			databaseCall.setInt(1, accountID);
			databaseCall.setInt(2, clientID);
			
			// execute delete and query all accounts again
			databaseCall.executeUpdate();
			ResultSet afterDeleteInfo = accountQuery.executeQuery();
			afterDeleteInfo.next();
			accountInfo.next();
			
			// check to see if difference in account count
			if(afterDeleteInfo.getInt(1) != accountInfo.getInt(1)) {
				isSuccessfulDelete = true;
			}
			
			else {
				throw new SQLException();
			}
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccessfulDelete;
	};
	
	public List<Account> getAccountByAmount(String amountLT, String amountGT, int clientID) {
		List<Account> accounts = new ArrayList<Account>();
		PreparedStatement accountQuery;
		String query;
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			
			if(amountLT == null && amountGT == null) {
				// prepare string
				query = "SELECT * FROM accounts WHERE ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, clientID);
			}
			
			else if(amountLT != null && amountGT == null) {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance < ? AND ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountLT));
				accountQuery.setInt(2, clientID);
			}
			
			else if(amountLT == null && amountGT != null) {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance > ? AND ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountGT));
				accountQuery.setInt(2, clientID);
			}
			
			else {
				// prepare string
				query = "SELECT * FROM accounts WHERE balance > ? AND balance < ? AND ? = accountOwnerID;";
				accountQuery = conn.prepareStatement(query);
				accountQuery.setInt(1, Integer.parseInt(amountGT));
				accountQuery.setInt(2, Integer.parseInt(amountLT));
				accountQuery.setInt(3, clientID);
			}
			
			// prepre string and save results
			ResultSet accountInfo = accountQuery.executeQuery();
			
			// return results after saving into account objects
			while(accountInfo.next()) {
				Account acct = new Account(accountInfo.getInt(1), accountInfo.getInt(2), accountInfo.getInt(3));
				accounts.add(acct);
			}
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return accounts;
	};

}
