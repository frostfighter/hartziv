/*
* This is the important source code of the plugin.
* I tried to include lots of comments to let you know what I did.
* If you have any question, send me a PM!
* Thanks for using my plugin!
*/


package tm.info.felix;

import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.*;
import org.bukkit.block.Sign;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.milkbowl.vault.economy.*;

import java.io.File;
import java.util.*;


public final class HartzIV extends JavaPlugin implements Listener {

	Date now;
	static Economy economy = null;
	
	public boolean cfgExists() {
		// Check if config file exists
		File f = new File("./" + this.getName() + "/config.yml");
		return f.exists();
	}
	
	@Override
	public void onEnable() {
		// get config file or create config file
		getServer().getPluginManager().registerEvents(this, this);
		if(!cfgExists()) {
			// Creates a config file
			saveDefaultConfig();
			System.out.println("Config created");
			getConfig().set("wait", 1000);
		}
		getConfig();
		
		System.out.println(setupEconomy());
	}
	
	@Override
	public void onDisable() {
	}

	// Active when command entered
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("hartz")){
    		/* if(args.length == 1) {
    			if(args[0] == "reload") {
                    getConfig();
                    saveConfig();
                    getServer().getPluginManager().disablePlugin(this);
                    getServer().getPluginManager().enablePlugin(this);
                    sender.sendMessage("Plugin reloaded!");
                    System.out.print("RELOADED HARTZIV!");
                    return true;
    			}
    		}
    		*/
    		// Send message on command use
    		sender.sendMessage(ChatColor.RED + "This command is not implemented yet.");

    	}
		return false;
    }
    
    
    
    
    // "The real plugin part"
    // *********************************************************************+
    
    
    // Will return a message on sign creation
    @EventHandler(priority=EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
    	Player setter = event.getPlayer();
    	
    	// Check if the created sign is a HartzIV sign
    	if(event.getLine(0).equalsIgnoreCase("[moneyget]")) {
    		// Check permission to set sign
    		if(setter.hasPermission("hartziv.sign.create") || setter.hasPermission("hartziv.all")) {
    			setter.sendMessage("Sign created");
    		}
        	else {
        		setter.sendMessage("No permission!");
        		event.getBlock().breakNaturally();
        	}
    	}
    	
    	// If player has no permission to create a moneyget sign
    }
    
    
    
    
    @EventHandler(priority=EventPriority.NORMAL)
    // Active when player interacts in some way
    public void onPlayerInteract(PlayerInteractEvent event) {
    	
    	// Get player who caused the event
    	Player player = event.getPlayer();
    	
    	// Check if a sign is right clicked
    	if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
    		
    		// Get sign from event blocksss
    		Sign sign = (Sign)event.getClickedBlock().getState();
    		
    		if(hartzsign(sign)) {
    			
    			// Get the time
    			now = new Date();
    			
    			// Check if player has permission to click on sign
    			if(player.hasPermission("hartziv.sign.use") || player.hasPermission("hartziv.all")) {
    				
    				// Check if player waited long enough
    				if(now.getTime() - getConfig().getDouble("players." + player.getName()) >= getConfig().getInt("wait")) {
    					
    					// Updates the time when player recieved money last in the config.yml
    					getConfig().set("players." + player.getName(), now.getTime());
    					saveConfig();
    					
    					// Send message to player and to console about recieving money
    					player.sendMessage("You recieved " + sign.getLine(1) + " Dollars.");
    					System.out.println(player.getName() + " got " + sign.getLine(1) + " Dollars.");
    					
    					// Give player money
    					economy.depositPlayer(player.getName(), Integer.parseInt(sign.getLine(1)));
    				}
    				
    				// Player didn't wait long enough to recieve money again
    				else {
    					player.sendMessage("You need to wait to recieve money from this sign.");
    				}
    				
    			}
    			
    			else {
    				player.sendMessage("No permission!");
    			}
    		}
    	}
    }
    
    
    
    
    
    // Defining some methods for the real program
    
    public boolean hartzsign(Sign sign) {
		if(sign.getLine(0).equalsIgnoreCase("[moneyget]")) {
			return true;
		}
		return false;
    }
    
    
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
