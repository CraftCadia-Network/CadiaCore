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

			// Kick Command
			if(label.equalsIgnoreCase("stats")) {
				if (player.hasPermission("cadiacore.stats")) {
					
					player.sendMessage(ChatColor.BLUE + "MyCadia> " + ChatColor.GRAY + "EXP Till Next Level: " + player.getExpToLevel() + ".");
					player.sendMessage(ChatColor.BLUE + "MyCadia> " + ChatColor.GRAY + "Current EXP: " + player.getExp() + ".");
					player.sendMessage(ChatColor.BLUE + "MyCadia> " + ChatColor.GRAY + "Flying Status: " + player.isFlying() + ".");
					player.sendMessage(ChatColor.BLUE + "MyCadia> " + ChatColor.GRAY + "Gamemode: " + player.getGameMode() + ".");
					player.sendMessage(ChatColor.BLUE + "MyCadia> " + ChatColor.GRAY + "Are You Op?: " + player.isOp() + ".");
					player.sendMessage(ChatColor.BLUE + "MyCadia> " + ChatColor.GRAY + "Display Name: " + player.getDisplayName() + ".");
					player.sendMessage(ChatColor.BLUE + "MyCadia> " + ChatColor.GRAY + "Normal Name: " + player.getName() + ".");
					
					
					
				}

			
			}
	
			
			
	}
		
		// Respawn Location Command
					if(label.equalsIgnoreCase("respawnloc")) {
						
						player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY  + player.getDisplayName() + ", you will respawn at these coordinates: " + player.getBedSpawnLocation() + ".");
						
					
					}
					// Time Played Command
		            if(label.equalsIgnoreCase("timeplayed")) {
						
						player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY  + player.getDisplayName() + "'s first time playing was  " + player.getFirstPlayed() + " ago.");
						
					
					}
		            
		            // On Player Death Command (Requires to be run on death by another API!)
		            if(label.equalsIgnoreCase("ondeath")) {
						
		            	
						player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY  + player.getDisplayName() + "was " + ChatColor.BLUE + ChatColor.BOLD + "done goofed by " + player.getKiller() + "!");
						
					
					}
		          
		         // Set Player Time to day
		            if(label.equalsIgnoreCase("pday")) {
						
		            	player.setPlayerTime(1000, true);
						player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.GRAY  + player.getDisplayName() + "'s personal player time was set to day. (" + ChatColor.BLUE + "Time was set to 1000" + ChatColor.GRAY + ")");
						
					
					}
		            
			         // Current Potions
		            if(label.equalsIgnoreCase("potioneffect")) {
		            	
						player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.DARK_GREEN  + "your status effect is " + player.getActivePotionEffects());
		
		            }
		            
			         // Current Potions
		            if(label.equalsIgnoreCase("potioneffect")) {
		            	
						player.sendMessage(ChatColor.BLUE + "CadiaCore> " + ChatColor.DARK_GREEN  + "your status effect is " + player.getActivePotionEffects());
		
		            }
		return false;
	
}}
