package de.theniclas.bauplugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.theniclas.bauplugin.utils.Vars;

public class EntityDamageByEntity implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;
        if (!p.hasPermission("bs.admin")
                && !Vars.isTrusted(p, p.getWorld().getName())
                && !Vars.isOwner(p, p.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
