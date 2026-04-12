package de.theniclas.bauplugin.commands;

import java.util.ArrayList;
import java.util.List;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class CMDtrust implements CommandExecutor {

    private final Bauserver plugin;

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.prefixed("<red>Du musst dich in deiner eigenen Welt befinden")); return false;
        }
        if (args.length < 1) { p.sendMessage(Vars.prefixed("<red>Wer soll denn Baurechte bekommen?")); return false; }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            p.sendMessage(Vars.prefixed("<red>Dieser Spieler hat noch nie hier gespielt")); return false;
        }
        if (target.getName() != null && target.getName().equals(p.getName())) {
            p.sendMessage(Vars.prefixed("<red>Du bist doch aber der Schöpfer dieser Welt")); return false;
        }
        String worldKey = p.getLocation().getWorld().getName().replace("worlds/", "");
        if (Vars.isTrusted(target, worldKey)) {
            p.sendMessage(Vars.prefixed("<red>Der Spieler hat bereits Baurechte, nutze <yellow>/untrust (Spieler)")); return false;
        }
        List<String> trusted = new ArrayList<>(plugin.getBauserverConfig().getWorldsConfig().getStringList("Worlds." + worldKey + ".Trusted"));
        trusted.add(target.getUniqueId().toString());
        plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + worldKey + ".Trusted", trusted);
        plugin.getBauserverConfig().saveConfiguration();
        p.sendMessage(Vars.prefixed("<green>Du hast <yellow>" + target.getName() + " <green>Baurechte gegeben"));
        return false;
    }
}
