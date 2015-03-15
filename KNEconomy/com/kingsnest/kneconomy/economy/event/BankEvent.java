package com.kingsnest.kneconomy.economy.event;

import com.kingsnest.kneconomy.economy.Bank;

public class BankEvent {
	
	private Bank bank;
	private boolean cancel = false;
	
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
	
	public boolean isCancelled()
	{
		return  cancel;
	}
	
	public void setCancelled(boolean cancel)
	{
		this.cancel = cancel;
	}

}
