package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.Vars;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CMDuntrust implements CommandExecutor {

    private final Bauserver plugin;

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p))
            return false;
        if (!p.hasPermission("bs.worlds")) {
            p.sendMessage(Vars.NOPERM);
            return false;
        }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.prefixed("<red>Du musst dich in deiner eigenen Welt befinden"));
            return false;
        }
        if (args.length < 1) {
            p.sendMessage(Vars.prefixed("<red>Wem sollen seine Rechte entzogen werden?"));
            return false;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String worldKey = p.getLocation().getWorld().getName().replace("worlds/", "");
        if (!Vars.isTrusted(target, worldKey)) {
            p.sendMessage(Vars.prefixed("<red>Dieser Spieler hat keine Baurechte"));
            return false;
        }
        List<String> trusted = new ArrayList<>(
                plugin.getBauserverConfig().getWorldsConfig().getStringList("Worlds." + worldKey + ".Trusted"));
        trusted.remove(target.getUniqueId().toString());
        plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + worldKey + ".Trusted", trusted);
        plugin.getBauserverConfig().saveConfiguration();
        p.sendMessage(Vars.prefixed("<green>Du hast <yellow>" + target.getName() + " <green>die Rechte entzogen"));
        return false;
    }
}
