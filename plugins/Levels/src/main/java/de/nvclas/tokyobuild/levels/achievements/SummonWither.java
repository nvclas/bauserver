package de.nvclas.tokyobuild.levels.achievements;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import de.nvclas.tokyobuild.levels.utils.Methods;

public class SummonWither implements Listener {
	@EventHandler
	public void onSummoningWither(CreatureSpawnEvent e) {
		
		if(e.getSpawnReason() == SpawnReason.BUILD_WITHER) {		
			if(!e.isCancelled()) {
				for(Entity entity : e.getLocation().getWorld().getNearbyEntities(e.getLocation(), 5, 5, 5)) {
					if(entity instanceof Player) {
						Player p = (Player) entity;
						if(Methods.isUnlocked(p)) {
							Methods.addAchievement(p, "Bitte nicht griefen!", 30);
						}				
					}
				}
			}
		}
	}
}
