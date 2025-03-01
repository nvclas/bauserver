package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDvisibility implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("visibility")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.admin")) {
                    if (Configs.worldsConfig.getBoolean("Visibility")) {
                        Configs.worldsConfig.set("Visibility", false);
                        p.sendMessage(Vars.pr + "§aWelten sind nun nicht mehr für alle sichtbar");
                    } else {
                        Configs.worldsConfig.set("Visibility", true);
                        p.sendMessage(Vars.pr + "§aWelten sind nun für alle sichtbar");
                    }
                    Configs.saveConfiguration();
                } else {
                    p.sendMessage(Vars.noperm);
                }
            }
        }
        return false;
    }

}
