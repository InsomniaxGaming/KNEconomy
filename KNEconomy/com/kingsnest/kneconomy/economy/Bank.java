package com.kingsnest.kneconomy.economy;

import java.util.ArrayList;
import java.util.List;

import com.kingsnest.kneconomy.economy.listener.BankListener;

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

}
