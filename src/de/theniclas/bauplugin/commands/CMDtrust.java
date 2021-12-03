package de.theniclas.bauplugin.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDtrust implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("trust")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.worlds")) {
					if(args.length >= 1) {
						if(Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
							OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
							if(target.hasPlayedBefore() || target.isOnline()) {
								if(!target.getName().equals(p.getName())) {
									ArrayList<String> trusted = new ArrayList<>();
									if(Configs.worldsConfig.getStringList("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Trusted") == null || Configs.worldsConfig.getStringList("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Trusted").isEmpty()) {
										trusted.add(target.getUniqueId().toString());
										Configs.worldsConfig.set("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Trusted", trusted);
										p.sendMessage(Vars.pr + "§aDu hast §e" + target.getName() + " §aBaurechte gegeben");
										Configs.saveConfiguration();
									} else if(!Vars.isTrusted(target, p.getWorld().getName())) {
										for(String uuid : Configs.worldsConfig.getStringList("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Trusted")) {
											trusted.add(uuid);
										}
										trusted.add(target.getUniqueId().toString());
										Configs.worldsConfig.set("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Trusted", trusted);
										Configs.saveConfiguration();
										p.sendMessage(Vars.pr + "§aDu hast §e" + target.getName() + " §aBaurechte gegeben");
									} else {
										p.sendMessage(Vars.pr + "§cDer Spieler hat bereits Baurechte, wenn du sie ihm entziehen willst, nutze §e/untrust <Spieler>");
									}
									trusted.clear();
								} else {
									p.sendMessage(Vars.pr + "§cDu bist doch aber der Schöpfer dieser Welt");
								}
							} else {
								p.sendMessage(Vars.pr + "§cDieser Spieler hat noch nie hier gespielt");
							}
						} else {
							p.sendMessage(Vars.pr + "§cDu musst dich in deiner eigenen Welt befinden");
						}
					} else {
						p.sendMessage(Vars.pr + "§cWer soll denn Baurechte bekommen?");
					}
				} else {
					p.sendMessage(Vars.noperm);
				}
			}
		}
		return false;
	}
}