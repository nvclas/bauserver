package de.theniclas.levels.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.theniclas.bauplugin.utils.Vars;

public class PlayerQuit implements Listener {
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(Vars.placedBlocks.containsKey(p)) {
			Vars.placedBlocks.remove(p, Vars.placedBlocks.get(p));
		}
	}
}
