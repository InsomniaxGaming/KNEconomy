package com.kingsnest.kneconomy.economy.event;

import com.kingsnest.kneconomy.economy.Bank;
import com.kingsnest.kneconomy.economy.BankAccount;

public final class BankTransactionEvent extends BankEvent{
	
	private BankAccount account;
	
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

}
