package de.theniclas.bauplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        // Only show the death message in the world where the player died
        if (e.getEntity().getWorld().getPlayers().size() <= 1) {
            e.setDeathMessage(null);
        }
        e.setKeepInventory(true);
    }
}
