package de.theniclas.abu.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.abu.utils.Vars;

public class CMDabu implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("abu")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.abu")) {
					ArrayList<String> worlds = new ArrayList<>();
					if(Vars.config.getStringList("Worlds") != null) {
						for(String worldName : Vars.config.getStringList("Worlds")) {
							worlds.add(worldName);
						}
						if(!Vars.config.getStringList("Worlds").contains(p.getWorld().getName())) {
							worlds.add(p.getWorld().getName());
							for(Player all : Bukkit.getOnlinePlayers()) {
								if(all.getWorld().getName().equals(p.getWorld().getName())) {
									all.sendMessage(Vars.pr + "§aBlockphysiken wurden für diese Welt §edeaktiviert");
								}
							}
						} else {
							worlds.remove(p.getWorld().getName());
							for(Player all : Bukkit.getOnlinePlayers()) {
								if(all.getWorld().getName().equals(p.getWorld().getName())) {
									all.sendMessage(Vars.pr + "§aBlockphysiken wurden für diese Welt §eaktiviert");
								}
							}
						}
					} else {
						worlds.add(p.getWorld().getName());
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(all.getWorld().getName().equals(p.getWorld().getName())) {
								all.sendMessage(Vars.pr + "§aBlockphysiken wurden für diese Welt §edeaktiviert");
							}
						}
					}
					Vars.config.set("Worlds", worlds);
					Vars.saveConfiguration();
					worlds.clear();
				} else {
					p.sendMessage(Vars.pr + "§cDafür hast du keine Rechte");
				}
			}
		}
		return false;
	}
	
}
