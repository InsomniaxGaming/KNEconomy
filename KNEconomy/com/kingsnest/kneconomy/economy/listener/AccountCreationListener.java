package com.kingsnest.kneconomy.economy.listener;

import com.kingsnest.kneconomy.economy.event.AccountCreationEvent;

public interface AccountCreationListener extends BankListener {
	
	public void onAccountCreation(AccountCreationEvent e);

}
