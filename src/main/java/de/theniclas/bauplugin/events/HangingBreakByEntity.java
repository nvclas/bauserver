package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class HangingBreakByEntity implements Listener {

    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent e) {
        if (!(e.getRemover() instanceof Player p))
            return;
        if (!p.hasPermission("bs.admin") && !Vars.isTrusted(p, p.getWorld().getName())
                && !Vars.isOwner(p, p.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
