package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDaddspawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("addspawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.worlds")) {
					if(args.length >= 1) {
						if(Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
							if(Configs.worldsConfig.get("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Spawns") == null || Configs.worldsConfig.getConfigurationSection("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Spawns").getKeys(false).size() < 9) {
								if(Configs.worldsConfig.get("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Spawns") == null || !Configs.worldsConfig.getConfigurationSection("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Spawns").getKeys(false).contains(args[0])) {
									Configs.worldsConfig.set("Worlds." + p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ".Spawns." + args[0] + ".Location", p.getLocation().getWorld().getName().replaceAll("worlds/", "") + ", " + String.valueOf(p.getLocation().getX()) + ", " + String.valueOf(p.getLocation().getY()) + ", " + String.valueOf(p.getLocation().getZ()));
									Configs.saveConfiguration();
									p.sendMessage(Vars.pr + "§aSpawnpunkt §e" + args[0] + " §aerstellt");
								} else {
									p.sendMessage(Vars.pr + "§cDiesen Spawnpunktnamen gibt es bereits für diese Welt");
								}
							} else {
								p.sendMessage(Vars.pr + "§cEs existieren bereits zu viele Spawnpunkte für diese Welt, lösche sie mithilfe von §e/worlds");
							}
						} else {
							p.sendMessage(Vars.pr + "§cDu musst dich in deiner eigenen Welt befinden");
						}
					} else {
						p.sendMessage(Vars.pr + "§cWie soll der Spawnpunkt heißen?");
					}
				} else {
					p.sendMessage(Vars.noperm);
				}
			}
		}
		return false;
	}

}
