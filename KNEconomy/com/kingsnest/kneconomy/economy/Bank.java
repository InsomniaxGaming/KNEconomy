package com.kingsnest.kneconomy.economy;

import java.util.ArrayList;
import java.util.List;

import com.kingsnest.kneconomy.economy.event.BankEvent;
import com.kingsnest.kneconomy.economy.event.BankTransactionEvent;
import com.kingsnest.kneconomy.economy.listener.BankListener;
import com.kingsnest.kneconomy.economy.listener.BankTransactionListener;

public class Bank {
	
	/**Listeners for bank events*/
	private List<BankListener> 	bankListeners;
	
	private static BasicTransactionListener BASIC_LISTENER;
	
	/**Bank accounts of users currently online*/
	protected List<BankAccount> accounts;
	
	public Bank()
	{
		bankListeners = new ArrayList<BankListener>(); 
		
		if(BASIC_LISTENER == null)
			BASIC_LISTENER = new BasicTransactionListener();
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
		BASIC_LISTENER.onTransaction(e);
	}
	
	/**
	 * The final decision maker on whether to allow a
	 * transaction, and also an example of the event system.
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
}
