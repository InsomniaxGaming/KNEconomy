package com.kingsnest.kneconomy.economy.event;

import java.util.UUID;

import com.kingsnest.kneconomy.economy.Bank;
import com.kingsnest.kneconomy.economy.BankAccount;

public class AccountCreationEvent extends BankEvent {
	
	private final BankAccount account;
	private final UUID holder;

	public AccountCreationEvent(Bank bank, BankAccount account) {
		super(bank);
		
		this.account = account;
		this.holder = account.getHolder();
	}
	
	public UUID getHolder()
	{
		return holder;
	}
	
	public BankAccount getAccount()
	{
		return account;
	}
}
