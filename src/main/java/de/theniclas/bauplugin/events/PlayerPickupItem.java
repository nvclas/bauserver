package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItem implements Listener {
    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {

        Player p = e.getPlayer();
        if (!p.hasPermission("bs.admin")) {
            if (!Vars.isTrusted(p, p.getWorld().getName())) {
                if (!Vars.isOwner(p, p.getWorld().getName())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
