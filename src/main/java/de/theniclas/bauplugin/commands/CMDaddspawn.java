package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDaddspawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("addspawn") && sender instanceof Player p) {
            if (p.hasPermission("bs.worlds")) {
                if (args.length >= 1) {
                    if (Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
                        if (Configs.worldsConfig.get("Worlds." + p.getLocation()
                                .getWorld()
                                .getName()
                                .replace("worlds/",
                                        "") + ".Spawns") == null || Configs.worldsConfig.getConfigurationSection(
                                        "Worlds." + p.getLocation().getWorld().getName().replace("worlds/", "") + ".Spawns")
                                .getKeys(false)
                                .size() < 9) {
                            if (Configs.worldsConfig.get("Worlds." + p.getLocation()
                                    .getWorld()
                                    .getName()
                                    .replace("worlds/",
                                            "") + ".Spawns") == null || !Configs.worldsConfig.getConfigurationSection(
                                            "Worlds." + p.getLocation().getWorld().getName().replace("worlds/", "") + ".Spawns")
                                    .getKeys(false)
                                    .contains(args[0])) {
                                Configs.worldsConfig.set("Worlds." + p.getLocation()
                                                .getWorld()
                                                .getName()
                                                .replace("worlds/", "") + ".Spawns." + args[0] + ".Location",
                                        p.getLocation()
                                                .getWorld()
                                                .getName()
                                                .replace("worlds/", "") + ", " + p.getLocation()
                                                .getX() + ", " + p.getLocation().getY() + ", " + p.getLocation()
                                                .getZ());
                                Configs.saveConfiguration();
                                p.sendMessage(Vars.pr + "§aSpawnpunkt §e" + args[0] + " §aerstellt");
                            } else {
                                p.sendMessage(Vars.pr + "§cDiesen Spawnpunktnamen gibt es bereits für diese Welt");
                            }
                        } else {
                            p.sendMessage(Vars.pr + "§cEs existieren bereits zu viele Spawnpunkte für diese Welt, l§sche sie mithilfe von §e/worlds");
                        }
                    } else {
                        p.sendMessage(Vars.pr + "§cDu musst dich in deiner eigenen Welt befinden");
                    }
                } else {
                    p.sendMessage(Vars.pr + "§cWie soll der Spawnpunkt hei§en?");
                }
            } else {
                p.sendMessage(Vars.noperm);
            }
        }

        return false;
    }

}
