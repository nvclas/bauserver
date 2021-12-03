package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDping implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("ping")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(((CraftPlayer) p).getHandle().ping <= 40) {
					p.sendMessage(Vars.pr + "§eDein Ping beträgt §a" + ((CraftPlayer) p).getHandle().ping + "ms");
				} else if(((CraftPlayer) p).getHandle().ping >= 80) {
					p.sendMessage(Vars.pr + "§eDein Ping beträgt §c" + ((CraftPlayer) p).getHandle().ping + "ms");
				} else {
					p.sendMessage(Vars.pr + "§eDein Ping beträgt §6" + ((CraftPlayer) p).getHandle().ping + "ms");
				}
			}
		}
		return false;
	}
	
}
