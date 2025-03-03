package de.nvclas.tokyobuild.levels.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;

public class PlayerQuitListener implements Listener {
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(Vars.placedBlocks.containsKey(p)) {
			Vars.placedBlocks.remove(p, Vars.placedBlocks.get(p));
		}
	}
}
