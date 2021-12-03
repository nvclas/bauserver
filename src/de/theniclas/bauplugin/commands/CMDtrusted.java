package de.theniclas.bauplugin.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDtrusted implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("trusted")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.worlds")) {
					if(Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
						if(!Configs.worldsConfig.getStringList("Worlds." + p.getWorld().getName().replaceAll("worlds/", "") + ".Trusted").isEmpty() && Configs.worldsConfig.getStringList("Worlds." + p.getWorld().getName().replaceAll("worlds/", "") + ".Trusted") != null) {
							p.sendMessage(Vars.pr + "ßaFolgende Spieler haben in deiner Welt Baurechte:");
							for(String uuid : Configs.worldsConfig.getStringList("Worlds." + p.getWorld().getName().replaceAll("worlds/", "") + ".Trusted")) {
								p.sendMessage("ß7 - ße" + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
							}
						} else p.sendMessage(Vars.pr + "ßcAuﬂer dir hat in dieser Welt niemand Baurechte");
					} else p.sendMessage(Vars.pr + "ßcDas geht nur in deiner eigenen Welt");
				} else p.sendMessage(Vars.noperm);
			}
		}
		return false;
	}

}
