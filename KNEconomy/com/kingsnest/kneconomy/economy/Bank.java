package com.kingsnest.kneconomy.economy;

import java.util.ArrayList;
import java.util.List;

import com.kingsnest.kneconomy.economy.event.BankEvent;
import com.kingsnest.kneconomy.economy.event.BankTransactionEvent;
import com.kingsnest.kneconomy.economy.listener.BankListener;
import com.kingsnest.kneconomy.economy.listener.BankTransactionListener;

public class Bank {
	
	private List<BankListener> bankListeners;
	
	public Bank()
	{
		bankListeners = new ArrayList<BankListener>();
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
	}

}
