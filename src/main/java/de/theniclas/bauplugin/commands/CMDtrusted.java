package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CMDtrusted implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("trusted")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.worlds")) {
                    if (Vars.isOwner(p, p.getWorld().getName()) || p.hasPermission("bs.admin")) {
                        if (!Configs.worldsConfig.getStringList("Worlds." + p.getWorld()
                                                                .getName()
                                                                .replace("worlds/", "") + ".Trusted")
                                                        .isEmpty()) {
                            p.sendMessage(Vars.pr + "§aFolgende Spieler haben in deiner Welt Baurechte:");
                            for (String uuid : Configs.worldsConfig.getStringList("Worlds." + p.getWorld()
                                    .getName()
                                    .replace("worlds/", "") + ".Trusted")) {
                                p.sendMessage("§7 - §e" + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
                            }
                        } else p.sendMessage(Vars.pr + "§cAußer dir hat in dieser Welt niemand Baurechte");
                    } else p.sendMessage(Vars.pr + "§cDas geht nur in deiner eigenen Welt");
                } else p.sendMessage(Vars.noperm);
            }
        }
        return false;
    }

}
