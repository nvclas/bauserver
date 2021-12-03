package de.theniclas.levels.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.levels.utils.Methods;

public class CMDaddxp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("bs.admin")) {
			if(args.length >= 1) {
				Methods.addXp(Bukkit.getPlayer(args[1]), Integer.valueOf(args[0]));
			} else {
				Methods.addXp((Player) sender, Integer.valueOf(args[0]));
			}
		}
		return false;
	}

}
