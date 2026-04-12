package de.theniclas.bauplugin.events;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.quitMessage(LegacyComponentSerializer.legacySection().deserialize(
                "\u00a79" + p.getName() + " \u00a77hat den Server verlassen"));
    }
}
