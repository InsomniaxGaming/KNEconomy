package com.kingsnest.kneconomy;

import org.apache.logging.log4j.Logger;

import com.kingsnest.kneconomy.commands.CommandGetBalance;
import com.kingsnest.kneconomy.commands.CommandSetBalance;
import com.kingsnest.kneconomy.economy.Bank;
import com.kingsnest.kneconomy.economy.BankAccount;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = KNEconomy.MODID, version = KNEconomy.VERSION, name = KNEconomy.NAME)
public class KNEconomy {

	//Configuration categories
	public static final String CATEGORY_MAIN 	= "KNEconomy";
	public static final String CATEGORY_BANK 	= "KNEconomy.Banks";
	public static final String CATEGORY_ACCOUNT = "KNEconomy.Accounts";

    public static final String MODID        	= "kneconomy";
    public static final String VERSION      	= "0.0.1 Alpha";
    public static final String NAME         	= "King's Nest Economy API";

    @Instance(MODID)
    public KNEconomy instance;

    @SidedProxy(clientSide = "com.kingsnest.kneconomy.ClientProxy", serverSide = "com.kingsnest.kneconomy.CommonProxy")
    public static ClientProxy proxy;

    // Config object
    public static Configuration CONFIGURATION	= null;
    
    // Logger, for logging
    public static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	LOGGER = event.getModLog();
    	
        CONFIGURATION = new Configuration(event.getSuggestedConfigurationFile());

        // loading the configuration from its file
        CONFIGURATION.load();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	Bank.setDefaultBank(new Bank("Default"));
    	
    	for(String name : CONFIGURATION.getStringList(CATEGORY_MAIN, "BankNames", new String[0], ""))
    		Bank.getBanks().add(Bank.getDefaultBank().deserialize(CONFIGURATION, name));
    	
    	LOGGER.info("'" + NAME + "' V" + VERSION + " initializing.");
    	
    	MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	event.registerServerCommand(new CommandSetBalance(this));
    	event.registerServerCommand(new CommandGetBalance(this));
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayer)
        {
        	//Check if player has an existing bank account.
            EntityPlayer player = (EntityPlayer) event.entity;
            BankAccount account = null;
            
            for(Bank bank : Bank.getBanks())
            	account = bank.getAccount(player, true);
            
            if(account == null)
            	account = new BankAccount(Bank.getDefaultBank(), player);
        }
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) 
    {
    	String[] bankNames = new String[Bank.getBanks().size()];
    	int nameIndex = 0;
    	
    	for(Bank bank : Bank.getBanks())
    	{
    		bank.serialize(CONFIGURATION); //Save the bank
    		
    		bankNames[nameIndex] = bank.getName();
    		nameIndex++;
    		
    		for(BankAccount account : bank.getAccounts()) //Save the accounts of online peeps
    			account.serialize(CONFIGURATION);
    	}
    	
    	CONFIGURATION.get(KNEconomy.CATEGORY_MAIN, "BankNames", bankNames).set(bankNames);
    	
    	CONFIGURATION.save();
    }
    
    public static Logger getLogger()
    {
    	return LOGGER;
    }

    public boolean isOp(EntityPlayer player) {
        // Check if multiplayer and OP or singleplayer and commands allowed
        // (cheats)
        return MinecraftServer.getServer().getConfigurationManager()
                .func_152596_g(player.getGameProfile());
    }

}
