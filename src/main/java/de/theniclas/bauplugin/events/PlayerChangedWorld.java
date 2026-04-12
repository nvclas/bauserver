package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import de.theniclas.bauplugin.utils.Configs;

public class PlayerChangedWorld implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getWorld().equals(e.getFrom())) {
                all.sendMessage("\u00a7e" + p.getName() + " \u00a77hat die Welt verlassen");
            } else if (all.getWorld().equals(p.getWorld())) {
                all.sendMessage("\u00a7e" + p.getName() + " \u00a77hat die Welt betreten");
            }
        }

        String spawnWorld = Configs.worldsConfig.getString("Spawn.World");
        if (spawnWorld != null && !e.getFrom().getName().equals(spawnWorld)
                && e.getFrom().getPlayers().isEmpty()) {
            Bukkit.unloadWorld(e.getFrom(), true);
        }
    }
}
