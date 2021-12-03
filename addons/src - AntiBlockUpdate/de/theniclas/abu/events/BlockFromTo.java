package de.theniclas.abu.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import de.theniclas.abu.utils.Vars;

public class BlockFromTo implements Listener {
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent e) {
		if(Vars.config.getStringList("Worlds") != null && Vars.config.getStringList("Worlds").contains(e.getBlock().getWorld().getName())) {
			e.setCancelled(true);
		}
	}
}
