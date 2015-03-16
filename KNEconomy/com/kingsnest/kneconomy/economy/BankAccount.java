package com.kingsnest.kneconomy.economy;

import java.util.HashMap;
import java.util.UUID;

import net.minecraftforge.common.config.Configuration;

import com.kingsnest.kneconomy.KNEconomy;
import com.kingsnest.kneconomy.Serializeable;
import com.kingsnest.kneconomy.economy.event.BankTransactionEvent;

public class BankAccount implements Serializeable{
	
	private Bank bank;
	private UUID holder;
	private double balance;
	
	/**
	 * Retrieve existing bank account if specified uuid correlates to one,
	 * or make a new empty account.
	 * 
	 * @param 	b		the bank this account belongs to
	 * @param	uuid	the UUID of the account holder
	 * */
	public BankAccount(Bank b, UUID uuid)
	{
		bank	= b;
		holder 	= uuid;
		
		if(bank.accounts.contains(this)) // Add this account to the bank's list of 
			bank.accounts.add(this);	 // accounts, if it isn't already in there.
	}
	
	public Bank getBank()
	{
		return bank;
	}
	
	public void setBank(Bank b)
	{
		bank = b; //TODO mayhaps some extra logic for when the set bank already contains this user.
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
		this.bank.fireTransactionEvent(new BankTransactionEvent(bank, this, amount));
	}
	
	public void withdrawl(double amount)
	{
		this.bank.fireTransactionEvent(new BankTransactionEvent(bank, this, -amount));
	}

	@Override
	public void serialize(Configuration config)
	{
		config.get(KNEconomy.CATEGORY_ACCOUNT, this.getHolder().toString() + "." + this.getBank().getName() + ".holder", false).set( this.getHolder().toString());
	}

	@Override
	public void deserialize(HashMap<String, Object> data)
	{
		
	}

}
