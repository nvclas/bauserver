package de.theniclas.bauplugin.commands;

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
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PR + "\u00a7cDas geht nur in deiner eigenen Welt"); return false;
        }
        String worldKey = p.getWorld().getName().replace("worlds/", "");
        java.util.List<String> list = Configs.worldsConfig.getStringList("Worlds." + worldKey + ".Trusted");
        if (list.isEmpty()) {
            p.sendMessage(Vars.PR + "\u00a7cAu\u00dfer dir hat in dieser Welt niemand Baurechte"); return false;
        }
        p.sendMessage(Vars.PR + "\u00a7aFolgende Spieler haben in deiner Welt Baurechte:");
        for (String uuid : list) {
            p.sendMessage("\u00a77 - \u00a7e" + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
        }
        return false;
    }
}
