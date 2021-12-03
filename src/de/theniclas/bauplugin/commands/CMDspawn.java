package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDspawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(Configs.worldsConfig.getConfigurationSection("Spawn") != null) {
					World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
					double x = Configs.worldsConfig.getDouble("Spawn.X");
					double y = Configs.worldsConfig.getDouble("Spawn.Y");
					double z = Configs.worldsConfig.getDouble("Spawn.Z");
					Location spawn = new Location(w, x, y, z);
					p.teleport(spawn);
				} else {
					p.sendMessage(Vars.pr + "§cEs wurde kein Spawnpunkt gesetzt");
				}
			}
		}
		return false;
	}
}