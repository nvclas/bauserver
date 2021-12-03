package de.theniclas.levels.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.levels.utils.Data;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CMDlock implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("lock")) {
			if(sender.hasPermission("bs.admin")) {
				if(args.length >= 1) {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					if(target.hasPlayedBefore()) {
						PermissionUser user = PermissionsEx.getUser(target.getUniqueId().toString());
						if(Data.getConfig().get("Levels." + target.getUniqueId().toString() + ".Level") != null) {
							Data.getConfig().set("Levels." + target.getUniqueId().toString(), null);
							Data.saveConfiguration();
							sender.sendMessage(Vars.pr + "§aDu hast §6" + target.getName() + " §agesperrt");
							user.addGroup("gast");
							user.removeGroup("shinjin");
							user.removeGroup("deshi");
							user.removeGroup("puro");
							user.removeGroup("senpai");
							user.removeGroup("sensei");
							if(target.isOnline()) {
								((Player) target).sendMessage(Vars.pr + "§7§k1111111111111111111111111111");
								((Player) target).sendMessage(Vars.pr);
								((Player) target).sendMessage(Vars.pr + "§cDu wurdest gesperrt!");
								((Player) target).sendMessage(Vars.pr);
								((Player) target).sendMessage(Vars.pr + "§7§k1111111111111111111111111111");
								((Player) target).playSound(((Player) target).getLocation(), Sound.WITHER_SPAWN, 1.0f, 1.0f);
							}		
						} else {
							sender.sendMessage(Vars.pr + "§cDieser Spieler ist bereits gesperrt");
						}
					} else {
						sender.sendMessage(Vars.pr + "§cDieser Spieler war nie online");
					}
				} else {
					sender.sendMessage(Vars.pr + "§cWer soll wieder gesperrt werden?");
				}
			} else {
				sender.sendMessage(Vars.noperm);
			}
		}
		return false;
	}

}
