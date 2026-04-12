package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDsetowner implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.admin")) { p.sendMessage(Vars.NOPERM); return false; }
        if (args.length < 1) { p.sendMessage(Vars.PR + "§cWer soll der neue Besitzer werden?"); return false; }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            p.sendMessage(Vars.PR + "§cDieser Spieler hat noch nie hier gespielt"); return false;
        }
        String worldKey = p.getWorld().getName().replace("worlds/", "");
        String currentOwner = Configs.worldsConfig.getString("Worlds." + worldKey + ".Owner");
        if (target.getUniqueId().toString().equals(currentOwner)) {
            p.sendMessage(Vars.PR + "§cDieser Spieler ist bereits der Besitzer dieser Welt"); return false;
        }
        Configs.worldsConfig.set("Worlds." + worldKey + ".Owner", target.getUniqueId().toString());
        Configs.saveConfiguration();
        p.sendMessage(Vars.PR + "§aDer neue Besitzer dieser Welt ist nun §e" + args[0]);
        return false;
    }
}
