package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player p) {
            if (!p.hasPermission("bs.admin")) {
                if (!Vars.isTrusted(p, p.getWorld().getName())) {
                    if (!Vars.isOwner(p, p.getWorld().getName())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
