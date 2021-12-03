package de.theniclas.levels.achievements;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.levels.utils.Methods;

public class CreateWorld implements Listener {
	@EventHandler
	public void onCreatingWorld (PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if(Methods.isUnlocked(p)) {
			if(Vars.isOwner(p, e.getTo().getWorld().getName())) {
				Methods.addAchievement(p, "Neues Terrain", 10);
			}
		}
	}
}
