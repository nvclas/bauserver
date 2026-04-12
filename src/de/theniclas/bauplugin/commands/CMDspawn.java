package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class CMDspawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;

        if (Configs.worldsConfig.getConfigurationSection("Spawn") == null) {
            p.sendMessage(Vars.PREFIX + "§cEs wurde kein Spawnpunkt gesetzt");
            return false;
        }

        World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
        if (w == null) { p.sendMessage(Vars.PREFIX + "§cSpawnwelt konnte nicht geladen werden"); return false; }

        double x = Configs.worldsConfig.getDouble("Spawn.X");
        double y = Configs.worldsConfig.getDouble("Spawn.Y");
        double z = Configs.worldsConfig.getDouble("Spawn.Z");
        p.teleport(new Location(w, x, y, z));
        return false;
    }
}
