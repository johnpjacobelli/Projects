package com.bankapi.handler;

import java.util.List;

import com.bankapi.bankinfo.Client;
import com.bankapi.dao.ClientDAO;

import io.javalin.http.Handler;

public class ClientHandler {
	
	private static ClientDAO clientDAO = new ClientDAO();

	public static final Handler CLIENTGET = (ctx) -> {
		Client client = clientDAO.getClientByID(Integer.parseInt(ctx.pathParam("clientID")));
		
		if(client != null) {
			// return object to javalin and print
			ctx.json(client);
			System.out.println(client);
			System.out.println("______________________________");
		}
		
		else {
			System.out.println("Account does not exist.");
			ctx.html("Account does not exist.");
			ctx.status(404);
		}
	};
	
	public static final Handler CLIENTPOST = (ctx) -> {
		Client client = ctx.bodyAsClass(Client.class);
		if(clientDAO.insertClient(client.getFirstName(), client.getLastName())) {
			System.out.println("Client successfully created.");
			ctx.html("Client successfully created.");
			ctx.status(201);
		}
		
		else {
			System.out.println("Client info not properly formatted; could not be created.");
			ctx.html("Client info not properly formatted; could not be created.");
			ctx.status(404);
		}
	};
	 
	public static final Handler CLIENTPUT = (ctx) -> {
		Client client = ctx.bodyAsClass(Client.class);
		if(clientDAO.updateClientByID(Integer.parseInt(ctx.pathParam("clientID")), 
									  client.getFirstName(), client.getLastName())) {
			System.out.println("Account successfully updated.");
			ctx.html("Account successfully updated.");
		}
		
		else {
			System.out.println("Client does not exist or no changes were made; update failed.");
			ctx.html("Client does not exist or no changes were made; update failed.");
			ctx.status(404);
		}
	};
	
	public static final Handler CLIENTDELETE = (ctx) -> {
		if(clientDAO.deleteClientByID(Integer.parseInt(ctx.pathParam("clientID")))) {
			System.out.println("Client and client's accounts successfully removed.");
			ctx.html("Client and client's accounts successfully removed.");
		}
		
		else {
			System.out.println("Client could not be removed; does not exist.");
			ctx.html("Client could not be removed; does not exist.");
			ctx.status(404);
		}
		
	};
	
	public static final Handler CLIENTQUERY = (ctx) -> {
		List<Client> clients = clientDAO.getClientByName(ctx.queryParam("firstName"), ctx.queryParam("lastName"));
		
		// check if clients exist
		if(clients.size() == 0) {
			ctx.status(404);
			System.out.println("Client does not exist.");
			ctx.html("Client does not exist.");
		}
		
		else {
			// return object to javalin and print
			ctx.json(clients);
			for(Client tempClient : clients) {
				System.out.println(tempClient);
			}
			
			System.out.println("______________________________");
		}
	};

}