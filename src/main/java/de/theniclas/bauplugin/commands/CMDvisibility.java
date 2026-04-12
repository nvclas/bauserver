package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDvisibility implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.admin")) { p.sendMessage(Vars.NOPERM); return false; }
        if (Configs.worldsConfig.getBoolean("Visibility")) {
            Configs.worldsConfig.set("Visibility", false);
            p.sendMessage(Vars.PR + "§aWelten sind nun nicht mehr für alle sichtbar");
        } else {
            Configs.worldsConfig.set("Visibility", true);
            p.sendMessage(Vars.PR + "§aWelten sind nun für alle sichtbar");
        }
        Configs.saveConfiguration();
        return false;
    }
}
