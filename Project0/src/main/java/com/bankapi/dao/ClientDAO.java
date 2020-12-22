package com.bankapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bankapi.bankinfo.Client;

public class ClientDAO {

	private DAOConnection dbc;
	
	public ClientDAO() {
		dbc = new DAOConnection();
	}
	
	public ClientDAO(DAOConnection dbc) {
		this.dbc = dbc;
	}

	public Client getClientByID(int clientID) {
		try(Connection conn = dbc.getDBConnection()) {
			// prepare string
			String query = "SELECT * FROM clients WHERE clientID = ?;";
			PreparedStatement accountQuery = conn.prepareStatement(query);
			accountQuery.setInt(1, clientID);
			
			// execute query and save info in account object
			ResultSet clientInfo = accountQuery.executeQuery();
			clientInfo.next();
			Client client = new Client(clientInfo.getInt(1), clientInfo.getString(2), clientInfo.getString(3));
			return client;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	};
	
	public boolean insertClient(String firstName, String lastName) {
		boolean isSuccessfulInsert = false;
		try(Connection conn = dbc.getDBConnection()) {
			// create and prepare call
			String callString = "{ CALL insert_client(?, ?) }";
			CallableStatement databaseCall = conn.prepareCall(callString);

			// read in account into db call
			databaseCall.setString(1, firstName);
			databaseCall.setString(2, lastName);
			databaseCall.executeUpdate();
			isSuccessfulInsert = true;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccessfulInsert;
	};
	 
	public boolean updateClientByID(int clientID, String firstName, String lastName) {
		boolean isSuccessfulUpdate = false;
		try(Connection conn = dbc.getDBConnection()) {
			// create and prepare call
			String callString = "{ CALL update_client(?, ?, ?) }";
			String query = "SELECT * FROM clients WHERE clientID = ?;";
			CallableStatement databaseCall = conn.prepareCall(callString);
			PreparedStatement accountQuery = conn.prepareStatement(query);
			
			// query account before change for updates
			accountQuery.setInt(1, clientID);
			ResultSet beforeQuery = accountQuery.executeQuery();
			
			// read in account into db call
			databaseCall.setInt(1, clientID);
			databaseCall.setString(2, firstName);
			databaseCall.setString(3, lastName);
			
			databaseCall.executeUpdate();
			ResultSet afterQuery = accountQuery.executeQuery();
			beforeQuery.next();
			afterQuery.next();
			
			// if no changes, error
			if(beforeQuery.getInt(1) == afterQuery.getInt(1) && 
			   beforeQuery.getString(2).equals(afterQuery.getString(2)) &&
			   beforeQuery.getString(3).equals(afterQuery.getString(3))) {
				throw new SQLException();
			}
			
			isSuccessfulUpdate = true;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccessfulUpdate;
	};
	
	public boolean deleteClientByID(int clientID) {
		boolean isSuccessfulDelete = false;
		try(Connection conn = dbc.getDBConnection()) {
			// check account count before delete
			String query = "SELECT COUNT(*) FROM clients;";
			PreparedStatement accountQuery = conn.prepareStatement(query);
			ResultSet accountInfo = accountQuery.executeQuery();
			
			// create and prepare call
			String callString = "{ CALL delete_client(?) }";
			CallableStatement databaseCall = conn.prepareCall(callString);

			// read in account into db call
			databaseCall.setInt(1, clientID);
			
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
	
	public List<Client> getClientByName(String firstName, String lastName) {
		List<Client> clients = new ArrayList<Client>();
		PreparedStatement clientQuery;
		String query;
		
		try(Connection conn = dbc.getDBConnection()){
			
			if(firstName == null && lastName == null) {
				// prepare string
				query = "SELECT * FROM clients";
				clientQuery = conn.prepareStatement(query);
			}
			
			else if(firstName != null && lastName == null) {
				// prepare string
				query = "SELECT * FROM clients WHERE clientFirstName = ?;";
				clientQuery = conn.prepareStatement(query);
				clientQuery.setString(1, firstName);
			}
			
			else if(firstName == null && lastName != null) {
				// prepare string
				query = "SELECT * FROM clients WHERE clientLastName = ?;";
				clientQuery = conn.prepareStatement(query);
				clientQuery.setString(1,lastName);
			}
			
			else {
				// prepare string
				query = "SELECT * FROM clients WHERE clientFirstName = ? AND clientLastName = ?;";
				clientQuery = conn.prepareStatement(query);
				clientQuery.setString(1, firstName);
				clientQuery.setString(2, lastName);
			}
			
			// prepare string and save results
			ResultSet accountInfo = clientQuery.executeQuery();
			
			// return results after saving into account objects
			while(accountInfo.next()) {
				Client client = new Client(accountInfo.getInt(1), accountInfo.getString(2), accountInfo.getString(3));
				clients.add(client);
			}
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return clients;
	};

}
