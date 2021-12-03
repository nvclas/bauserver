package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDglobalspawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(command.getName().equalsIgnoreCase("globalspawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.admin")) {
					Configs.worldsConfig.set("Spawn.World", p.getWorld().getName());
					Configs.worldsConfig.set("Spawn.X", p.getLocation().getX());
					Configs.worldsConfig.set("Spawn.Y", p.getLocation().getY());
					Configs.worldsConfig.set("Spawn.Z", p.getLocation().getZ());
					Configs.saveConfiguration();
					p.sendMessage(Vars.pr + "§aDer globale Spawnpunkt wurde gesetzt");
				} else {
					p.sendMessage(Vars.noperm);
				}
			}
		}
		
		return false;
	}
}
