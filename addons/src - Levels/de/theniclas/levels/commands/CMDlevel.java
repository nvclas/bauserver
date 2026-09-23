package de.theniclas.levels.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.levels.utils.Data;

public class CMDlevel implements CommandExecutor {
    private static final String PREFIX = "§8[§9§lTokyo-Build§8] §7";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("level")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (Data.getConfig().get("Levels." + p.getUniqueId().toString() + ".Level") == null) {
                    p.sendMessage(PREFIX + "�cDu hast nicht die M�glichkeit XP zu sammeln");
                } else {
                    p.sendMessage(PREFIX + "�aDu befindest dich derzeit auf �bLevel " + Data.getConfig()
                            .get("Levels." + p.getUniqueId().toString() + ".Level"));
                    p.sendMessage(PREFIX + "�aDeine XP�8: �b" + Data.getConfig()
                            .get("Levels." + p.getUniqueId().toString() + ".Xp") + "�8/�b" + Data.getConfig()
                            .get("Levels." + p.getUniqueId().toString() + ".NextLevelXp"));
                }
            }
        }
        return false;
    }
}
