package de.theniclas.abu.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import de.theniclas.abu.utils.Vars;

public class BlockPhysics implements Listener {
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent e) {		
		if(Vars.config.getStringList("Worlds") != null && Vars.config.getStringList("Worlds").contains(e.getBlock().getWorld().getName())) {
			e.setCancelled(true);
		}
	}
}
