package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDglobalspawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {

        if (command.getName().equalsIgnoreCase("globalspawn") && sender instanceof Player p) {
                if (p.hasPermission("bs.admin")) {
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


        return false;
    }
}
