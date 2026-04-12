package de.theniclas.bauplugin.commands;

import java.util.UUID;

import de.theniclas.bauplugin.Bauserver;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class CMDtrusted implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.prefixed("<red>Das geht nur in deiner eigenen Welt")); return false;
        }
        String worldKey = p.getWorld().getName().replace("worlds/", "");
        java.util.List<String> list = plugin.getBauserverConfig().getWorldsConfig().getStringList("Worlds." + worldKey + ".Trusted");
        if (list.isEmpty()) {
            p.sendMessage(Vars.prefixed("<red>Außer dir hat in dieser Welt niemand Baurechte")); return false;
        }
        p.sendMessage(Vars.prefixed("<green>Folgende Spieler haben in deiner Welt Baurechte:"));
        for (String uuid : list) {
            p.sendMessage(Vars.mini("<gray> - <yellow>" + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName()));
        }
        return false;
    }
}
