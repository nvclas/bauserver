package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDworldlock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.admin")) { p.sendMessage(Vars.NO_PERM); return false; }

        String worldKey = Vars.stripWorldsPrefix(p.getWorld().getName());
        boolean locked = Configs.worldsConfig.getBoolean("Worlds." + worldKey + ".Properties.Locked");
        Configs.worldsConfig.set("Worlds." + worldKey + ".Properties.Locked", !locked);
        Configs.saveConfiguration();

        p.sendMessage(Vars.PREFIX + "§aWelt wurde §e" + (!locked ? "gesperrt" : "entsperrt"));
        return false;
    }
}
