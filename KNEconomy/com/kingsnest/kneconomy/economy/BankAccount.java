package com.kingsnest.kneconomy.economy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;

import com.kingsnest.kneconomy.KNEconomy;
import com.kingsnest.kneconomy.Serializeable;
import com.kingsnest.kneconomy.economy.event.AccountCreationEvent;
import com.kingsnest.kneconomy.economy.event.BankTransactionEvent;

public class BankAccount implements Serializeable<BankAccount>{
	
	private Bank bank;
	private EntityPlayer holder;
	private double balance;
	
	/**Empty constructor for deserialization.*/
	public BankAccount(){}
	
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
		
		balance = bank.getInitialBalance();
		
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
		config.get(KNEconomy.CATEGORY_ACCOUNT, this.getHolder().getPersistentID().toString() + ".balance", 0.0D).set(this.getBalance());
		config.get(KNEconomy.CATEGORY_ACCOUNT, this.getHolder().getPersistentID().toString() + ".bank", "").set(this.getBank().getName());
	}

	@Override
	public BankAccount deserialize(Configuration config, String key)
	{
		BankAccount account = new BankAccount();
		
		config.get(KNEconomy.CATEGORY_ACCOUNT, key + ".balance", 0.0D).getDouble();
		config.get(KNEconomy.CATEGORY_ACCOUNT, key + ".bank", "").getString();
		
		return account;
	}

}
