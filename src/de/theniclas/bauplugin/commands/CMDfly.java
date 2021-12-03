package de.theniclas.bauplugin.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDfly implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("fly")) {
			if(sender instanceof Player) {
				Player p = (Player) sender; 
				if(p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE) {
					if(p.getAllowFlight() == false) {
						p.setAllowFlight(true);
						p.sendMessage(Vars.pr + "§aFlugmodus §eaktiviert");	
					} else {
						p.setAllowFlight(false);
						p.sendMessage(Vars.pr + "§aFlugmodus §edeaktiviert");
					}
				} else {
					p.sendMessage(Vars.pr + "§cDu kannst in deinem Spielmodus schon längst fliegen");
				}				
			}
		}
					return false;
	}
	
}
