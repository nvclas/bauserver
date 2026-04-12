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

public class CMDtrust implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.PR + "§cDu musst dich in deiner eigenen Welt befinden"); return false;
        }
        if (args.length < 1) { p.sendMessage(Vars.PR + "§cWer soll denn Baurechte bekommen?"); return false; }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            p.sendMessage(Vars.PR + "§cDieser Spieler hat noch nie hier gespielt"); return false;
        }
        if (target.getName() != null && target.getName().equals(p.getName())) {
            p.sendMessage(Vars.PR + "§cDu bist doch aber der Schöpfer dieser Welt"); return false;
        }
        String worldKey = p.getLocation().getWorld().getName().replace("worlds/", "");
        if (Vars.isTrusted(target, worldKey)) {
            p.sendMessage(Vars.PR + "§cDer Spieler hat bereits Baurechte, nutze §e/untrust <Spieler>"); return false;
        }
        List<String> trusted = new ArrayList<>(Configs.worldsConfig.getStringList("Worlds." + worldKey + ".Trusted"));
        trusted.add(target.getUniqueId().toString());
        Configs.worldsConfig.set("Worlds." + worldKey + ".Trusted", trusted);
        Configs.saveConfiguration();
        p.sendMessage(Vars.PR + "§aDu hast §e" + target.getName() + " §aBaurechte gegeben");
        return false;
    }
}
