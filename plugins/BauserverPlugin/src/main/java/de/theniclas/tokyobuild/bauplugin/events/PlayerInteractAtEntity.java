package de.theniclas.tokyobuild.bauplugin.events;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntity implements Listener {
    @EventHandler
    public void onEntityInteractaction(PlayerInteractAtEntityEvent e) {

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
