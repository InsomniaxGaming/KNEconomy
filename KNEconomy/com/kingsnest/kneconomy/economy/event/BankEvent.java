package com.kingsnest.kneconomy.economy.event;

import com.kingsnest.kneconomy.economy.Bank;

public class BankEvent {
	
	private Bank bank;
	
	public BankEvent(Bank bank)
	{
		this.bank = bank;
	}
	
	public Bank getBank()
	{
		return bank;
	}
	
	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

}
