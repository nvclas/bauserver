package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			if(!all.getWorld().getName().equals(e.getEntity().getWorld().getName())) {
				e.setDeathMessage("");
			}
		}
		e.setKeepInventory(true);
	}
}
