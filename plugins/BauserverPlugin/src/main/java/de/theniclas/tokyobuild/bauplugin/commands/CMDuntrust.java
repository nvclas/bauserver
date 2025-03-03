package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CMDuntrust implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("untrust") && sender instanceof Player p) {
            if (p.hasPermission("bs.worlds")) {
                if (args.length >= 1) {
                    if (!Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        if (Vars.isTrusted(target, p.getWorld().getName())) {
                            ArrayList<String> trusted = new ArrayList<>(Configs.worldsConfig.getStringList("Worlds." + p.getLocation()
                                    .getWorld()
                                    .getName()
                                    .replace("worlds/", "") + ".Trusted"));
                            trusted.remove(target.getUniqueId().toString());
                            Configs.worldsConfig.set("Worlds." + p.getLocation()
                                    .getWorld()
                                    .getName()
                                    .replace("worlds/", "") + ".Trusted", trusted);
                            p.sendMessage(Vars.pr + "§aDu hast §e" + target.getName() + " §adie Rechte entzogen");
                            Configs.saveConfiguration();
                            trusted.clear();
                        } else {
                            p.sendMessage(Vars.pr + "§cDieser Spieler hat keine Baurechte");
                        }
                    } else {
                        p.sendMessage(Vars.pr + "§cDu musst dich in deiner eigenen Welt befinden");
                    }
                } else {
                    p.sendMessage(Vars.pr + "§cWem sollen seine Rechte entzogen werden?");
                }
            } else {
                p.sendMessage(Vars.noperm);
            }
        }

        return false;
    }
}