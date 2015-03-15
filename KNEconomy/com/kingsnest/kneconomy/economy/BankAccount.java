package com.kingsnest.kneconomy.economy;

import java.util.UUID;

public class BankAccount {
	
	private UUID holder;
	private double balance;
	
	/**
	 * Retrieve existing bank account if specified uuid correlates to one,
	 * or make a new empty account.
	 * */
	public BankAccount(UUID uuid)
	{
		holder = uuid;
	}
	
	public UUID getHolder()
	{
		return holder;
	}
	
	public void setHolder(UUID uuid)
	{
		holder = uuid;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public void setBalance(double b)
	{
		balance = b;
	}
	
	public void deposit(double amount)
	{
		balance += amount;
	}
	
	public void withdrawl(double amount)
	{
		balance -= amount;
	}

}
