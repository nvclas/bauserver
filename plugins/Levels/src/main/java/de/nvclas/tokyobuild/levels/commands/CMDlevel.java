package de.nvclas.tokyobuild.levels.commands;

import de.nvclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.nvclas.tokyobuild.levels.utils.Data;

public class CMDlevel implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("level")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(Data.getConfig().get("Levels." + p.getUniqueId().toString() + ".Level") == null) {
					p.sendMessage(AntiBlockUpdate.PREFIX + "�cDu hast nicht die M�glichkeit XP zu sammeln");
				} else {
					p.sendMessage(AntiBlockUpdate.PREFIX + "�aDu befindest dich derzeit auf �bLevel " + Data.getConfig().get("Levels." + p.getUniqueId().toString() + ".Level"));
					p.sendMessage(AntiBlockUpdate.PREFIX + "�aDeine XP�8: �b" + Data.getConfig().get("Levels." + p.getUniqueId().toString() + ".Xp") + "�8/�b" + Data.getConfig().get("Levels." + p.getUniqueId().toString() + ".NextLevelXp"));
				}
			}
		}
		return false;
	}
}