package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        Player p = e.getPlayer();
        if (!p.hasPermission("bs.admin") && !Vars.isTrusted(p, p.getWorld().getName()) && !Vars.isOwner(p,
                p.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
