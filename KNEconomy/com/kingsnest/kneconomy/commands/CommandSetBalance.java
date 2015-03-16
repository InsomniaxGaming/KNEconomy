package com.kingsnest.kneconomy.commands;

import java.util.ArrayList;
import java.util.List;

import com.kingsnest.kneconomy.KNEconomy;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class CommandSetBalance implements ICommand{
	
    private KNEconomy 	 myMod       = null;
    private String       commandName = "setbalance";
    private String       commandUse  = "/setbalance <player> [bank] <amount> | Set the account balance of the specified player and specified bank.";

    private List<String> aliases;
    
    public CommandSetBalance(KNEconomy economy)
    {
    	myMod = economy;
    	
    	aliases = new ArrayList<String>();
    	aliases.add("setbalance");
    }

	@Override
	public int compareTo(Object arg0)
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return commandName;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return commandUse;
	}

	@Override
	public List<String> getCommandAliases()
	{
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender commandSender, String[] args)
	{
		String message = "";
		if(args.length > 2)
		{
			String player 	= args[0];
			String bank		= args[1];
			double balance;
			
			try{
				balance = Double.parseDouble(args[2]);
			}catch(Exception e)
			{
				message = "Invalid balance specified.";
			}
		}
		else if(args.length > 1)
		{
			String player 	= args[0];
			double balance;
			
			try{
				balance = Double.parseDouble(args[1]);
			}catch(Exception e)
			{
				message = "Invalid balance specified.";
			}
			
		}
		else
			message = getCommandUsage(commandSender);
		
		if(commandSender instanceof EntityPlayer)
		{
			ChatComponentText cmc = new ChatComponentText("");
			cmc.appendText(message);
			((EntityPlayer)commandSender).addChatMessage(cmc);
		}
		else
		{
			myMod.logger.info(message);
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		 if (icommandsender instanceof EntityPlayer)
		 	return (myMod.isOp((EntityPlayer) icommandsender));
		
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender commandSender, String[] args)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}

}
