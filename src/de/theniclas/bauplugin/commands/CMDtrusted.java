package de.theniclas.bauplugin.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDtrusted implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NO_PERM); return false; }

        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PREFIX + "§cDas geht nur in deiner eigenen Welt");
            return false;
        }

        String worldKey = Vars.stripWorldsPrefix(p.getWorld().getName());
        List<String> trustedUuids = Configs.worldsConfig.getStringList("Worlds." + worldKey + ".Trusted");

        if (trustedUuids.isEmpty()) {
            p.sendMessage(Vars.PREFIX + "§cAußer dir hat in dieser Welt niemand Baurechte");
            return false;
        }

        p.sendMessage(Vars.PREFIX + "§aFolgende Spieler haben in deiner Welt Baurechte:");
        for (String uuid : trustedUuids) {
            p.sendMessage("§7 - §e" + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
        }
        return false;
    }
}
