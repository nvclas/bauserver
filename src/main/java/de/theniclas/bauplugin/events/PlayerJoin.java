package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.Vars;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoin implements Listener {

    private final Bauserver plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPlayedBefore()) {
            e.joinMessage(Vars.mini("<blue>" + p.getName() + " <gray>ist neu beigetreten"));
            p.sendMessage(Vars.prefixed("<green>Joine auf unseren Discord, um dich freizuschalten!"));
        } else {
            e.joinMessage(Vars.mini("<blue>" + p.getName() + " <gray>hat den Server betreten"));
        }

        p.setFoodLevel(20);
        p.setHealth(20);

        if (plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Spawn") == null)
            return;
        World w = Bukkit.getWorld(plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World"));
        if (w == null)
            return;
        double x = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.X");
        double y = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Y");
        double z = plugin.getBauserverConfig().getWorldsConfig().getDouble("Spawn.Z");
        p.teleport(new Location(w, x, y, z));
    }
}
