package com.kingsnest.kneconomy.economy.event;

import com.kingsnest.kneconomy.economy.Bank;
import com.kingsnest.kneconomy.economy.BankAccount;

public final class BankTransactionEvent extends BankEvent{
	
	private BankAccount account;
	private double transaction;
	
	public BankTransactionEvent(Bank bank, BankAccount account, double transaction)
	{
		super(bank);
		this.account = account;
	}
	
	public BankAccount getBankAccount()
	{
		return account;
	}
	
	public void setBankAccount(BankAccount account)
	{
		this.account = account;
	}
	
	public double getTransaction()
	{
		return transaction;
	}
	
	public void setTransaction(double transaction)
	{
		this.transaction = transaction;
	}

}
