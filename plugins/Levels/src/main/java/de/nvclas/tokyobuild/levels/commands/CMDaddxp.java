package de.nvclas.tokyobuild.levels.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.nvclas.tokyobuild.levels.utils.Methods;
import org.jetbrains.annotations.NotNull;

public class CMDaddxp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
		if(sender.hasPermission("bs.admin")) {
			if(args.length >= 1) {
				Methods.addXp(Bukkit.getPlayer(args[1]), Integer.parseInt(args[0]));
			} else {
				Methods.addXp((Player) sender, Integer.parseInt(args[0]));
			}
		}
		return false;
	}

}
