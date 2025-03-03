package de.nvclas.tokyobuild.levels.commands;

import de.nvclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.nvclas.tokyobuild.levels.config.Data;
import org.jetbrains.annotations.NotNull;

public class CMDlevel implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
		if(command.getName().equalsIgnoreCase("level")) {
			if(sender instanceof Player p) {
                if(Data.getConfig().get("Levels." + p.getUniqueId() + ".Level") == null) {
					p.sendMessage(Vars.pr + "�cDu hast nicht die M�glichkeit XP zu sammeln");
				} else {
					p.sendMessage(Vars.pr + "�aDu befindest dich derzeit auf �bLevel " + Data.getConfig().get("Levels." + p.getUniqueId() + ".Level"));
					p.sendMessage(Vars.pr + "�aDeine XP�8: �b" + Data.getConfig().get("Levels." + p.getUniqueId() + ".Xp") + "�8/�b" + Data.getConfig().get("Levels." + p.getUniqueId() + ".NextLevelXp"));
				}
			}
		}
		return false;
	}
}