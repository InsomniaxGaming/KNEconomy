package com.kingsnest.kneconomy.economy.event;

import net.minecraft.entity.player.EntityPlayer;

import com.kingsnest.kneconomy.economy.Bank;
import com.kingsnest.kneconomy.economy.BankAccount;

public class AccountCreationEvent extends BankEvent {
	
	private final BankAccount account;
	private final EntityPlayer holder;

	public AccountCreationEvent(Bank bank, BankAccount account) {
		super(bank);
		
		this.account = account;
		this.holder = account.getHolder();
	}
	
	public EntityPlayer getHolder()
	{
		return holder;
	}
	
	public BankAccount getAccount()
	{
		return account;
	}
}
