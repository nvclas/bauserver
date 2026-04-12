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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NO_PERM); return false; }

        if (args.length < 1) {
            p.sendMessage(Vars.PREFIX + "§cWem sollen seine Rechte entzogen werden?");
            return false;
        }

        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PREFIX + "§cDu musst dich in deiner eigenen Welt befinden");
            return false;
        }

        @SuppressWarnings("deprecation")
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!Vars.isTrusted(target, p.getWorld().getName())) {
            p.sendMessage(Vars.PREFIX + "§cDieser Spieler hat keine Baurechte");
            return false;
        }

        String worldKey = Vars.stripWorldsPrefix(p.getWorld().getName());
        List<String> trusted = new ArrayList<>(Configs.worldsConfig.getStringList("Worlds." + worldKey + ".Trusted"));
        trusted.remove(target.getUniqueId().toString());
        Configs.worldsConfig.set("Worlds." + worldKey + ".Trusted", trusted);
        Configs.saveConfiguration();
        p.sendMessage(Vars.PREFIX + "§aDu hast §e" + target.getName() + " §adie Rechte entzogen");
        return false;
    }
}
