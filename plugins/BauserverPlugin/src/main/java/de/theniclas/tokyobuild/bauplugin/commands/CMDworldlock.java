package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDworldlock implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("worldlock")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.admin")) {
                    if (Configs.worldsConfig.get("Worlds." + p.getWorld()
                            .getName()
                            .replace("worlds/",
                                    "") + ".Properties.Locked") == null || !Configs.worldsConfig.getBoolean("Worlds." + p.getWorld()
                            .getName()
                            .replace("worlds/", "") + ".Properties.Locked")) {
                        Configs.worldsConfig.set("Worlds." + p.getWorld()
                                .getName()
                                .replace("worlds/", "") + ".Properties.Locked", true);
                        p.sendMessage(Vars.pr + "§aWelt wurde §egesperrt");
                    } else {
                        Configs.worldsConfig.set("Worlds." + p.getWorld()
                                .getName()
                                .replace("worlds/", "") + ".Properties.Locked", false);
                        p.sendMessage(Vars.pr + "§aWelt wurde §eentsperrt");
                    }
                    Configs.saveConfiguration();
                }
            }
        }
        return false;
    }

}
