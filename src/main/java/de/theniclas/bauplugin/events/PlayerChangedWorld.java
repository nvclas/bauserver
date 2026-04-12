package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import de.theniclas.bauplugin.utils.BauserverConfig;
import de.theniclas.bauplugin.utils.Vars;

@RequiredArgsConstructor
public class PlayerChangedWorld implements Listener {

    private final Bauserver plugin;

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getWorld().equals(e.getFrom())) {
                all.sendMessage(Vars.mini("<yellow>" + p.getName() + " <gray>hat die Welt verlassen"));
            } else if (all.getWorld().equals(p.getWorld())) {
                all.sendMessage(Vars.mini("<yellow>" + p.getName() + " <gray>hat die Welt betreten"));
            }
        }

        String spawnWorld = plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World");
        if (spawnWorld != null && !e.getFrom().getName().equals(spawnWorld)
                && e.getFrom().getPlayers().isEmpty()) {
            Bukkit.unloadWorld(e.getFrom(), true);
        }
    }
}
