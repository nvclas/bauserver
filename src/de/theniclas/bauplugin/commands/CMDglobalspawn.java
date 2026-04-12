package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDglobalspawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.admin")) { p.sendMessage(Vars.NO_PERM); return false; }

        Configs.worldsConfig.set("Spawn.World", p.getWorld().getName());
        Configs.worldsConfig.set("Spawn.X", p.getLocation().getX());
        Configs.worldsConfig.set("Spawn.Y", p.getLocation().getY());
        Configs.worldsConfig.set("Spawn.Z", p.getLocation().getZ());
        Configs.saveConfiguration();
        p.sendMessage(Vars.PREFIX + "§aDer globale Spawnpunkt wurde gesetzt");
        return false;
    }
}
