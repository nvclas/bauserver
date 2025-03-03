package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDspawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player p) {
                if (Configs.worldsConfig.getConfigurationSection("Spawn") != null) {
                    World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
                    double x = Configs.worldsConfig.getDouble("Spawn.X");
                    double y = Configs.worldsConfig.getDouble("Spawn.Y");
                    double z = Configs.worldsConfig.getDouble("Spawn.Z");
                    Location spawn = new Location(w, x, y, z);
                    p.teleport(spawn);
                } else {
                    p.sendMessage(Vars.pr + "§cEs wurde kein Spawnpunkt gesetzt");
                }
            }
        }
        return false;
    }
}