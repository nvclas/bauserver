package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDworldlock implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("worldlock")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.admin")) {
					if(Configs.worldsConfig.get("Worlds." + p.getWorld().getName().replaceAll("worlds/", "") + ".Properties.Locked") == null || Configs.worldsConfig.getBoolean("Worlds." + p.getWorld().getName().replaceAll("worlds/", "") + ".Properties.Locked") == false) {
						Configs.worldsConfig.set("Worlds." + p.getWorld().getName().replaceAll("worlds/", "") + ".Properties.Locked", true);
						p.sendMessage(Vars.pr + "§aWelt wurde §egesperrt");
					} else {
						Configs.worldsConfig.set("Worlds." + p.getWorld().getName().replaceAll("worlds/", "") + ".Properties.Locked", false);
						p.sendMessage(Vars.pr + "§aWelt wurde §eentsperrt");
					}
					Configs.saveConfiguration();
				}
			}
		}
		return false;
	}

}
