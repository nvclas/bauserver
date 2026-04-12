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
public class CMDglobalspawn implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.admin")) { p.sendMessage(Vars.NOPERM); return false; }
        plugin.getBauserverConfig().getWorldsConfig().set("Spawn.World", p.getWorld().getName());
        plugin.getBauserverConfig().getWorldsConfig().set("Spawn.X", p.getLocation().getX());
        plugin.getBauserverConfig().getWorldsConfig().set("Spawn.Y", p.getLocation().getY());
        plugin.getBauserverConfig().getWorldsConfig().set("Spawn.Z", p.getLocation().getZ());
        plugin.getBauserverConfig().saveConfiguration();
        p.sendMessage(Vars.prefixed("<green>Der globale Spawnpunkt wurde gesetzt"));
        return false;
    }
}
