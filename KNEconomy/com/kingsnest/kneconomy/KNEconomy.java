package com.kingsnest.kneconomy;

import org.apache.logging.log4j.Logger;

import com.kingsnest.kneconomy.commands.CommandGetBalance;
import com.kingsnest.kneconomy.commands.CommandSetBalance;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

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
    public Configuration         config       = null;
    
    public Logger logger = FMLLog.getLogger();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());

        // loading the configuration from its file
        config.load();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	logger.info("'" + NAME + "' V" + VERSION + " initializing.");
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

    @EventHandler
    public void entityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayer)
        {
        	//Check if player has an existing bank account.
            EntityPlayer player = (EntityPlayer) event.entity;
            
        }
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) 
    {
    	config.save();
    }

    public boolean isOp(EntityPlayer player) {
        // Check if multiplayer and OP or singleplayer and commands allowed
        // (cheats)
        return MinecraftServer.getServer().getConfigurationManager()
                .func_152596_g(player.getGameProfile());
    }

}
