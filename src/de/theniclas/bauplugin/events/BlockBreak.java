package de.theniclas.bauplugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.theniclas.bauplugin.utils.Vars;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("bs.admin")
                && !Vars.isTrusted(p, p.getWorld().getName())
                && !Vars.isOwner(p, p.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
