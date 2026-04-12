package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class CMDvisibility implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.admin")) { p.sendMessage(Vars.NOPERM); return false; }
        if (plugin.getBauserverConfig().getWorldsConfig().getBoolean("Visibility")) {
            plugin.getBauserverConfig().getWorldsConfig().set("Visibility", false);
            p.sendMessage(Vars.PR + "§aWelten sind nun nicht mehr für alle sichtbar");
        } else {
            plugin.getBauserverConfig().getWorldsConfig().set("Visibility", true);
            p.sendMessage(Vars.PR + "§aWelten sind nun für alle sichtbar");
        }
        plugin.getBauserverConfig().saveConfiguration();
        return false;
    }
}
