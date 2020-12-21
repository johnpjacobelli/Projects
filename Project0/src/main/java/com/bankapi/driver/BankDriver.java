package com.bankapi.driver;

import com.bankapi.handler.AccountHandler;
import com.bankapi.handler.ClientHandler;

import io.javalin.Javalin;

public class BankDriver {
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create();
		app.start(9001);
		
		
		
		// Client request handling ---------------------------
//		GET /clients => gets all clients return 200
		app.get("/clients", ClientHandler.CLIENTQUERY);
		
//		POST /clients => Creates a new client return a 201-status code
		app.post("/clients", ClientHandler.CLIENTPOST);
		
//		GET /clients/10 => get client with id of 10 return 404 if no such client exists
		app.get("/clients/:clientID", ClientHandler.CLIENTGET);
		
//		PUT /clients/12 => updates client with id of 12 return 404 if no such client exists
		app.put("/clients/:clientID", ClientHandler.CLIENTPUT);
		
//		DELETE /clients/15 => deletes client with the id of15 return 404 if no such client exists
		app.delete("/clients/:clientID", ClientHandler.CLIENTDELETE);
		
		
		
		// Account request handling ---------------------------
//		GET /clients/7/accounts => get all accounts for client 7 return 404 if no client exists
//		GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and 2000 return 404 if no client exists
		app.get("/clients/:clientID/accounts", AccountHandler.ACCOUNTQUERY);
		
//		POST /clients/5/accounts =>creates a new account for client with the id of 5 return a 201-status code
		app.post("/clients/:clientID/accounts", AccountHandler.ACCOUNTPOST);
		
//		GET /clients/9/accounts/4 => get account 4 for client 9 return 404 if no account or client exists
		app.get("/clients/:clientID/accounts/:accountID", AccountHandler.ACCOUNTGET);
		
//		PUT /clients/10/accounts/3 => update account with the id 3 for client 10 return 404 if no account or client exists
		app.put("/clients/:clientID/accounts/:accountID", AccountHandler.ACCOUNTPUT);
		
//		DELETE /clients/15/accounts/6 => delete account 6 for client 15 return 404 if no account or client exists
		app.delete("/clients/:clientID/accounts/:accountID", AccountHandler.ACCOUNTDELETE);
		
		
		

	}

}