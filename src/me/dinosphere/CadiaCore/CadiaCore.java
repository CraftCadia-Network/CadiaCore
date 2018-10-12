package me.dinosphere.CadiaCore;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyost.ghosts.GhostPlayer;
import fr.skyost.ghosts.tasks.TurnHuman;
import me.dinosphere.CadiaCore.utils.GhostFactory;

public class CadiaCore extends JavaPlugin{

	public GhostFactory ghostFactory;
	public static Logger logger = Logger.getLogger("Minecraft");
	public static CadiaCore plugin;
	
	// Console Message (When Turned Off)	
	@Override
	public void onDisable() {
		
		this.ghostFactory = new GhostFactory(this);
		PluginDescriptionFile pdfFile = this.getDescription();
		CadiaCore.logger.info(pdfFile.getName() + " has been disabled.");
		
	}
	// Console Message (When Turned On)
	@Override
	public void onEnable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		CadiaCore.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() +  "has been enabled. Starting CadiaCore..");
		
	}
		
		
	// Command Code
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		// Discord Link
		if(label.equalsIgnoreCase("discord")) {
			player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY + "http://discord.io/cadia/");
			
			
			
		}
		// Website Link
		if(label.equalsIgnoreCase("website")) {
			
			player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY + "http://cadianetwork.com/");
		
		}
		
		// Sets Player as Ghost
		 if (label.equalsIgnoreCase("ghost")) {
			
			 GhostPlayer.ghostManager.setGhost(player, true);
	            GhostPlayer.ghostManager.addPlayer(player);
	            player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY + "Set " + player.getDisplayName() + " to a ghost.");
	            GhostPlayer.totalGhosts += 1;
	            new TurnHuman(player.getName(), Boolean.valueOf(false)).runTaskLaterAsynchronously(Bukkit.getPluginManager().getPlugin("Ghost Player"), GhostPlayer.config.ghostTime.intValue());
			 
		    }

		// Fly Command (Toggle)
		if(label.equalsIgnoreCase("fly")) {
			if (player.hasPermission("cadiacore.fly")){
				if (player.isFlying() == true) {
					player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY + "Fly for user " + player.getDisplayName() + " has been disabled.");
					player.setAllowFlight(false);
					player.setFlying(false);
				
				}else {
					
					player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY + "Fly for user " + player.getDisplayName() + " has been enabled.");
					player.setAllowFlight(true);
					player.setFlying(true);
					
				}	
			}
			
			
			
		}
		return false;
			
	}
	
}
