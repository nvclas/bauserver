package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDdownload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("download")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.worlds")) {
					if(Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
						//File worldfile = new File(p.getWorld().getName());
						//upload file to web server
					} else p.sendMessage(Vars.pr + "§cDu kannst nur deine eigenen Welten herunterladen");
				} else p.sendMessage(Vars.noperm);
			}
		}
		return false;
	}

}
