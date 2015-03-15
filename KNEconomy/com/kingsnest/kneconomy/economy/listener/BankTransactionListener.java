package com.kingsnest.kneconomy.economy.listener;

import com.kingsnest.kneconomy.economy.event.BankTransactionEvent;

/**
 * Listener for deposits/withdrawls of bank accounts.
 * */
public interface BankTransactionListener {
	
	public void onDeposit(BankTransactionEvent e);
	
	public void onWithdrawl(BankTransactionEvent e);

}
