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
public class CMDworldlock implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.admin")) { p.sendMessage(Vars.NOPERM); return false; }
        String worldKey = p.getWorld().getName().replace("worlds/", "");
        boolean locked = plugin.getBauserverConfig().getWorldsConfig().get("Worlds." + worldKey + ".Properties.Locked") != null
                && plugin.getBauserverConfig().getWorldsConfig().getBoolean("Worlds." + worldKey + ".Properties.Locked");
        plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + worldKey + ".Properties.Locked", !locked);
        plugin.getBauserverConfig().saveConfiguration();
        p.sendMessage(Vars.PR + "§aWelt wurde §e" + (!locked ? "gesperrt" : "entsperrt"));
        return false;
    }
}
