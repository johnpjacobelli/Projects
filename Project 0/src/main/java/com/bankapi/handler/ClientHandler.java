package com.bankapi.handler;

import io.javalin.http.Handler;

public class ClientHandler {

	public static final Handler CLIENTGET = (ctx) -> {

	};
	
	public static final Handler CLIENTPOST = (ctx) -> {
  
	};
	 
	public static final Handler CLIENTPUT = (ctx) -> {

	};
	public static final Handler CLIENTDELETE = (ctx) -> {

	};
	
	public static final Handler CLIENTQUERY = (ctx) -> {

		// TODO SQL search for account info
		// if no info, error
		String amountLT = ctx.queryParam("amountLessThan");
		String amountGT = ctx.queryParam("amountGreaterThan");

		if (amountLT == null && amountGT == null) {
			// query DB for all clients and return
			
		}

		if (amountLT != null && amountGT == null) {
			// query DB for clients with ID > X
				// return 200 if found
				// return 404 if none found
			System.out.println("amountLT not null");
		}

		if (amountLT == null && amountGT != null) {
			// query DB for amountGT with ID < X
				// return 200 if found
				// return 404 if none found
			System.out.println("amountGT not null");
		}

		if (amountLT != null && amountGT != null) {
			// query DB for amountGT with X < ID < Y
				// return 200 if found
				// return 404 if none found
			System.out.println("neither are null");
		}

	};

}