package de.theniclas.bauplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDuntrust implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PR + "\u00a7cDu musst dich in deiner eigenen Welt befinden"); return false;
        }
        if (args.length < 1) { p.sendMessage(Vars.PR + "\u00a7cWem sollen seine Rechte entzogen werden?"); return false; }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String worldKey = p.getLocation().getWorld().getName().replace("worlds/", "");
        if (!Vars.isTrusted(target, worldKey)) {
            p.sendMessage(Vars.PR + "\u00a7cDieser Spieler hat keine Baurechte"); return false;
        }
        List<String> trusted = new ArrayList<>(Configs.worldsConfig.getStringList("Worlds." + worldKey + ".Trusted"));
        trusted.remove(target.getUniqueId().toString());
        Configs.worldsConfig.set("Worlds." + worldKey + ".Trusted", trusted);
        Configs.saveConfiguration();
        p.sendMessage(Vars.PR + "\u00a7aDu hast \u00a7e" + target.getName() + " \u00a7adie Rechte entzogen");
        return false;
    }
}
