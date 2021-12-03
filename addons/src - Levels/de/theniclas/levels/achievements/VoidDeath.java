package de.theniclas.levels.achievements;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.theniclas.levels.utils.Methods;

import org.bukkit.event.entity.PlayerDeathEvent;

public class VoidDeath implements Listener {
	@EventHandler
	public void onVoidDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if(Methods.isUnlocked(p)) {
			if(p.getLastDamageCause().getCause() == DamageCause.VOID) {
				Methods.addAchievement(p, "Wofür kannst du fliegen?", 50);
			}
		}
	}
}