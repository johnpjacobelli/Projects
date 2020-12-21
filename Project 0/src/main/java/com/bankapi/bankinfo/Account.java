package com.bankapi.bankinfo;

public class Account {
	
	private int accountID;
	private int ownerID;
	private int balance;
	
	public Account() {

	}

	public Account(int accountID, int ownerID, int balance) {
		super();
		this.accountID = accountID;
		this.ownerID = ownerID;
		this.balance = balance;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", ownerID=" + ownerID + ", balance=" + balance + "]";
	}
	
}
