package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.Vars;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CMDspawn implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p))
            return false;
        if (plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Spawn") == null) {
            p.sendMessage(Vars.prefixed("<red>Es wurde kein Spawnpunkt gesetzt"));
            return false;
        }
        World w = Bukkit.getWorld(plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World"));
        if (w == null) {
            p.sendMessage(Vars.prefixed("<red>Spawnwelt nicht gefunden"));
            return false;
        }
        double x = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.X");
        double y = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Y");
        double z = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Z");
        p.teleport(new Location(w, x, y, z));
        return false;
    }
}
