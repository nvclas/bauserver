package de.theniclas.bauplugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import de.theniclas.bauplugin.utils.Vars;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.quitMessage(Vars.mini("<blue>" + p.getName() + " <gray>hat den Server verlassen"));
    }
}
