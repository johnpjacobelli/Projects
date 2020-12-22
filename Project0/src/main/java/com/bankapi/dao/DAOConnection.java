package com.bankapi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOConnection {
	
	private static String url = "jdbc:mariadb://database-1.c0xi5suntsgb.us-west-1.rds.amazonaws.com:3306/Project 0";
	private static String username = "firstuser";
	private static String password = "mypassword";
	
	public Connection getDBConnection() throws SQLException {
		
		return DriverManager.getConnection(url, username, password);
	}

}
