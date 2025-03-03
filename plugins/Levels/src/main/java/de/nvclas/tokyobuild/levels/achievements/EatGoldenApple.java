package de.nvclas.tokyobuild.levels.achievements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import de.nvclas.tokyobuild.levels.utils.Methods;

public class EatGoldenApple implements Listener {
	@EventHandler
	public void onEatingGoldenApple(PlayerItemConsumeEvent e) {
		
		Player p = e.getPlayer();
		if(!e.isCancelled()) {
			if(Methods.isUnlocked(p)) {
				if(e.getItem() != null) {
					if(e.getItem().getDurability() == 1 && e.getItem().getType() == Material.GOLDEN_APPLE) {
						Methods.addAchievement(p, "UNLIMITED POWER", 10);
					}
				}
			}				
		}
	}
}