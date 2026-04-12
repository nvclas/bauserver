package de.theniclas.bauplugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import de.theniclas.bauplugin.utils.Vars;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("§9" + p.getName() + " §7hat den Server verlassen");

        // Clean up any tool-permission attachment to avoid memory leaks
        PermissionAttachment attachment = Vars.toolPermissions.remove(p.getUniqueId());
        if (attachment != null) {
            p.removeAttachment(attachment);
        }

        Vars.voidWorldName.remove(p);
        Vars.flatWorldName.remove(p);
        Vars.normalWorldName.remove(p);
    }
}
