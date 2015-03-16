package com.kingsnest.kneconomy.economy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;

import com.kingsnest.kneconomy.KNEconomy;
import com.kingsnest.kneconomy.Serializeable;
import com.kingsnest.kneconomy.economy.event.AccountCreationEvent;
import com.kingsnest.kneconomy.economy.event.BankEvent;
import com.kingsnest.kneconomy.economy.event.BankTransactionEvent;
import com.kingsnest.kneconomy.economy.listener.AccountCreationListener;
import com.kingsnest.kneconomy.economy.listener.BankListener;
import com.kingsnest.kneconomy.economy.listener.BankTransactionListener;

public class Bank implements Serializeable<Bank>{
	
	private static int TOTAL_BANKS = 0;
	
	/** The default bank. */
	private static Bank DEFAULT;
	
	/** All ze banks. */
	private static List<Bank> BANKS;
	
	//Bank identifiers
	/**Bank ID. After a bank has been saved to config, its ID will always be the same.*/
	private int 	id;
	private String 	name;
	
	/**The initial balance to give new bank accounts.*/
	private double initialBalance = 100;
	
	/**Listeners for bank events*/
	private List<BankListener> 	bankListeners;
	
	private static BasicTransactionListener BASIC_TRANSACTION_LISTENER;
	private static BasicCreationListener 	BASIC_CREATION_LISTENER;
	
	/**Bank accounts of users currently online*/
	protected List<BankAccount> accounts;
	
	private Bank()
	{
		bankListeners = new ArrayList<BankListener>(); 
		accounts = new ArrayList<BankAccount>();
		
		if(BANKS == null)
			BANKS = new ArrayList<Bank>();
		
		BANKS.add(this);
		
		if(BASIC_TRANSACTION_LISTENER == null)
			BASIC_TRANSACTION_LISTENER = new BasicTransactionListener();
		if(BASIC_CREATION_LISTENER == null)
			BASIC_CREATION_LISTENER = new BasicCreationListener();
	}
	
	public Bank(String bankName)
	{
		super();
		
		id = TOTAL_BANKS++;
		name = bankName;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void setID(int i)
	{
		id = i;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public double getInitialBalance()
	{
		return initialBalance;
	}
	
	public void setInitialBalance(double balance)
	{
		initialBalance = balance;
	}
	
	public static void initialize()
	{
		
	}
	
	public static Bank getDefaultBank()
	{
		return DEFAULT;
	}
	
	public static void setDefaultBank(Bank b)
	{
		DEFAULT = b;
	}
	
	public static List<Bank> getBanks()
	{
		return BANKS;
	}
	
	public List<BankAccount> getAccounts()
	{
		return accounts;
	}

	public static Bank getBankFromName(String name)
	{
		for(Bank bank : BANKS)
		{
			if(bank.getName() == name)
				return bank;
		}
		
		return null;
	}
	
	public static Bank getBankFromID(int id)
	{
		for(Bank bank : BANKS)
		{
			if(bank.getID() == id)
				return bank;
		}
		
		return null;
	}
	
	/**
	 * Retrieves the account of the specified user. Only checks online users.
	 * */
	public BankAccount getAccount(EntityPlayer player)
	{
		for(BankAccount account : accounts)
		{
			if(account.getHolder() == player)
				return account;
		}
		return null;
	}

	/**
	 * Retrieves the bank account of the specified user, if one exists.
	 * if offlineOnly is false, it will first check getAccount(uuid).
	 * */
	public BankAccount getAccount(EntityPlayer player, boolean offlineOnly)
	{
		BankAccount account = null;
		
		if(!offlineOnly)
			account = getAccount(player);
		
		if(account == null)
		{
			//TODO check config for account.
		}
		
		return account;
	}
	
	public void registerBankListener(BankListener listener)
	{
		bankListeners.add(listener);
	}
	
	public void unregisterBankListener(BankListener listener)
	{
		bankListeners.remove(listener);
	}
	
	protected void fireEvent(BankEvent e)
	{
		for(BankListener listener : bankListeners)
		{
			//TODO something
		}
	}
	
	protected void fireTransactionEvent(BankTransactionEvent e)
	{
		for(BankListener listener : bankListeners)
		{
			if(listener instanceof BankTransactionListener)
			{
				((BankTransactionListener)listener).onTransaction(e);
			}
		}
		
		//Fire basic listener separately, to ensure it is the final authority.
		BASIC_TRANSACTION_LISTENER.onTransaction(e);
	}
	
	protected void fireAccountCreationEvent(AccountCreationEvent e)
	{
		for(BankListener listener : bankListeners)
		{
			if(listener instanceof AccountCreationListener)
			{
				((AccountCreationListener)listener).onAccountCreation(e);
			}
		}
		
		//Fire basic listener separately, to ensure it is the final authority.
		BASIC_CREATION_LISTENER.onAccountCreation(e);
	}

	@Override
	public void serialize(Configuration config)
	{
		config.get(KNEconomy.CATEGORY_BANK, this.getName() + ".id", 0.0D).set(this.getID());
		config.get(KNEconomy.CATEGORY_BANK, this.getName() + ".initialbalance", 0.0D).set(this.getInitialBalance());
	}

	@Override
	public Bank deserialize(Configuration config, String key)
	{
		Bank bank = new Bank();
		
		bank.setID(config.get(KNEconomy.CATEGORY_BANK, key + ".id", 0).getInt());
		bank.setInitialBalance(config.get(KNEconomy.CATEGORY_BANK, key + ".initialbalance", 0.0D).getDouble());
		
		return bank;
	}
	
	/**
	 * The final decision maker on whether to allow a transaction, and also an example of the event system.
	 * */
	final class BasicTransactionListener implements BankTransactionListener
	{
		@Override
		public void onTransaction(BankTransactionEvent e)
		{
			if(!e.isCancelled())
			{
				if(e.getTransaction() < 0)
				{
					//Withdrawl
					e.getBankAccount().setBalance(e.getBankAccount().getBalance()-e.getTransaction());
				}
				else
				{
					//Deposit
					e.getBankAccount().setBalance(e.getBankAccount().getBalance()+e.getTransaction());
				}
			}
		}
	}
	
	/**
	 * The final decision maker on whether to allow a transaction, and also an example of the event system.
	 * */
	final class BasicCreationListener implements AccountCreationListener
	{
		@Override
		public void onAccountCreation(AccountCreationEvent e)
		{
			if(!e.isCancelled())
			{
				e.getBank().accounts.add(e.getAccount());	 // accounts, if it isn't already in there.
				KNEconomy.getLogger().info("Bank account created!");
				KNEconomy.getLogger().info("Bank: " + e.getAccount().getBank().getName());
				KNEconomy.getLogger().info("Holder: " + e.getAccount().getHolder().getDisplayName());
			}
		}
	}
	
}
