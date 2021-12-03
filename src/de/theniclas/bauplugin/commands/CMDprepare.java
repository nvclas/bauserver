package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDprepare implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("prepare")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.worlds")) {
					if(Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
						p.getWorld().setTime(6000);
						p.getWorld().setGameRuleValue("doMobSpawning", "false");
						p.sendMessage(Vars.pr + "§aMobspawning §edeaktiviert");
						p.getWorld().setGameRuleValue("mobGriefing", "false");
						p.sendMessage(Vars.pr + "§aMobgriefing §edeaktiviert");
						p.getWorld().setGameRuleValue("doFireTick", "false");
						p.sendMessage(Vars.pr + "§aFeuerausbreitung §edeaktiviert");
						p.getWorld().setGameRuleValue("randomTickSpeed", "0");
						p.sendMessage(Vars.pr + "§aZufällige Blockupdates §edeaktiviert");
						p.chat("/weather off");
						p.getWorld().setGameRuleValue("doDaylightCycle", "false");
						p.sendMessage(Vars.pr + "§aTag-/Nachtzyklus §edeaktiviert");
						p.chat("/abu");
					} else {
						p.sendMessage(Vars.pr + "§cDu bist nicht der Ersteller dieser Welt");
					}
				} else {
					p.sendMessage(Vars.noperm);
				}
			}
		}
		return false;
	}

}
