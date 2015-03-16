package com.kingsnest.kneconomy.economy;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;

import com.kingsnest.kneconomy.KNEconomy;
import com.kingsnest.kneconomy.Serializeable;
import com.kingsnest.kneconomy.economy.event.AccountCreationEvent;
import com.kingsnest.kneconomy.economy.event.BankTransactionEvent;

public class BankAccount implements Serializeable{
	
	private Bank bank;
	private EntityPlayer holder;
	private double balance;
	
	/**
	 * Retrieve existing bank account if specified uuid correlates to one,
	 * or make a new empty account.
	 * 
	 * @param 	b		the bank this account belongs to
	 * @param	uuid	the UUID of the account holder
	 * */
	public BankAccount(Bank b, EntityPlayer uuid)
	{
		bank	= b;
		holder 	= uuid;
		
		bank.fireAccountCreationEvent(new AccountCreationEvent(bank, this));
	}
	
	public Bank getBank()
	{
		return bank;
	}
	
	public void setBank(Bank b)
	{
		bank = b; //TODO mayhaps some extra logic for when the set bank already contains this user.
	}
	
	public EntityPlayer getHolder()
	{
		return holder;
	}
	
	public void setHolder(EntityPlayer player)
	{
		holder = player;
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
		config.get(KNEconomy.CATEGORY_ACCOUNT+"."+this.getBank().getName(), this.getHolder().getUniqueID().toString() + ".holder", false).set(this.getHolder().getUniqueID().toString());
		config.get(KNEconomy.CATEGORY_ACCOUNT+"."+this.getBank().getName(), this.getHolder().getUniqueID().toString() + ".balance", false).set(this.getBalance());
	}

	@Override
	public void deserialize(HashMap<String, Object> data)
	{
		//this.setHolder(UUID.fromString((String)data.get("holder"))); TODO figure out how to get player from UUID
		//this.setBalance((double)data.get("balance"));
	}

}
